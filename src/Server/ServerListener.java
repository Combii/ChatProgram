package Server;
/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashSet;


public class ServerListener implements Runnable{

    DatagramSocket socket;
    DatagramPacket request;
    private final HashSet<Client> users = new HashSet<>();

    @Override
    public void run() {
        try{
        socket = new DatagramSocket(1234,InetAddress.getByName("localhost"));
            
        while (true) {
            request = new DatagramPacket(new byte[1024], 1024);

            System.out.println("Server is listening on port: " + socket.getLocalPort()+  " and ip: " + socket.getLocalAddress());

            socket.receive(request);


            System.out.println("Message received!");

            String text = new String(request.getData(), 0, request.getLength());

            System.out.println(text);

            if(!isKeyWord(text,request)) {
                new Thread(() -> {
                    try {
                        sendTextToClients(text, request.getAddress());
                    } catch (UnknownHostException | SocketException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            for (Client c : users) {
                System.out.println(c);
            }

            RunServer.getController().console.appendText(text + "\n");

        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendTextToClients(String text, InetAddress senderAddress) throws UnknownHostException, SocketException {

        for (Client c : users) {
            DatagramPacket p = new DatagramPacket(text.getBytes(),text.length(), c.getIp(),c.getPort());
            try {
                System.out.println("PORT SENDING: " + c.getPort() + " " + c.getIp() + " " + c.getUsername());
                socket.send(p);
            } catch (IOException e) {
                System.out.println("Message could not be sent");
            }
        }

    }

    private synchronized Client identifyClient(InetAddress ip) {

        for (Client c : users) {
            if (c.getIp().equals(ip)) return c;
        }
        
        return null;
    }

    private boolean isInChatRoom(InetAddress address) {
        for (Client c : users) {
            if(c.getIp().equals(address)) return true;
        }
        return false;
    }

    private synchronized Client identifyClientByUsername(String username) {
        for (Client c : users) {
            if (c.getUsername().equals(username)) return c;
        }
        return null;
    }



    public void sendUsers() throws IOException {

        String usernames = "--USERNAMES--" + getUsernames();

        for (Client c : users) {

            DatagramPacket p = new DatagramPacket(usernames.getBytes(),usernames.length(),c.getIp(),c.getPort());
            socket.send(p);
        }

    }
    
    private String getUsernames() {

        String usernames = "";

        for (Client c : users) {
            usernames += c.getUsername();

        }

        return usernames;

    }

    private void respondToClient(InetAddress senderAddress, int port, String message) {
        try {
            DatagramPacket p = new DatagramPacket(message.getBytes(),message.length(), senderAddress, port);
            socket.send(p);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void removeUser(InetAddress senderAddress){
        for (Client c : users) {
            if(c.getIp().equals(senderAddress)){
                users.remove(c);
            }
        }
    }

    private boolean isUniqueUsername(String username) {
        for (Client c : users) {
            if(c.getUsername().equals(username)) return false;
        }
        return true;
    }

    private boolean isKeyWord(String text, DatagramPacket request) throws IOException {

        if(text.contains("--PING-CHECK--")) {
            new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "ALVE")).start();
            return true;

        } else if(text.contains("--USERNAME--")) {
            String username = text.replaceAll("--USERNAME--","");
            if(isUniqueUsername(username)) {
                synchronized (users) {
                    System.out.println("adding");
                    users.add(new Client(request.getAddress(), request.getPort(), username));
                    new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "J_OK")).start();
                    //sendUsers();
                }
            } else {
                new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "J_ERR")).start();
            }
            return true;
        } else if (text.contains("--QUIT--")) {
            removeUser(request.getAddress());
            sendUsers();
            return true;
        }

        return false;
    }

    private String getUsername(String receivedMessage){
        return receivedMessage.substring(12,receivedMessage.length()-1);
    }

}