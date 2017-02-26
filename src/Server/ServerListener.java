package Server;
/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */

import java.io.IOException;
import java.net.*;
import java.util.HashSet;


public class ServerListener implements Runnable{

    DatagramSocket socket;
    private final HashSet<Client> users = new HashSet<>();

    @Override
    public void run() {
        try{
        socket = new DatagramSocket(1234);
            
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
            socket.receive(request);


            System.out.println("Message received!");

            String text = new String(request.getData(), 0, request.getLength());

            if(!isKeyWord(text,request)) {
                new Thread(() -> {
                    try {
                        sendTextToClients(text, request.getAddress());
                    } catch (UnknownHostException | SocketException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            RunServer.getController().console.appendText(text + "\n");

        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendTextToClients(String text, InetAddress senderAddress) throws UnknownHostException, SocketException {
        DatagramSocket socket2 = new DatagramSocket();

        Client sender = identifyClient(senderAddress);
        text = sender.getUsername() + ": " + text;

        for (Client c : users) {
            DatagramPacket p = new DatagramPacket(text.getBytes(),text.length(), c.getIp(),c.getPort());
            try {
                System.out.println("PORT SENDING: " + c.getPort() + " " + c.getIp() + " " + c.getUsername());
                socket2.send(p);
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

    private void respondToClient(InetAddress senderAddress, int port, String message) {
        try {
            DatagramSocket socket2 = new DatagramSocket();


            DatagramPacket p = new DatagramPacket(message.getBytes(),message.length(), senderAddress, port);
            socket2.send(p);

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

    private boolean isKeyWord(String text, DatagramPacket request) {

        switch (text) {
            case  "PING-CHECK":
                new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "PING-BACK")).start();
                return true;
            case  "--USERNAME--":
                if (isUniqueUsername(getUsername(text))) {
                    synchronized (users) {
                        users.add(new Client(request.getAddress(), request.getPort(), text));
                    }
                    return true;
                }
                else
                    new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "USERNAME-NOT-AVAILABLE")).start();
                return true;
            case "--QUIT--":
                removeUser(request.getAddress());
                return true;
        }
        return false;
    }

    private String getUsername(String receivedMessage){
        return receivedMessage.substring(12,receivedMessage.length()-1);
    }

}