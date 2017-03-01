package Server;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;

/**
 * Created by David Stovlbaek
 * 01 March 2017.
 */
public class ClientList {
    static final HashSet<Client> users = new HashSet<>();
    private DatagramSocket socket;

    public ClientList() throws SocketException {
        socket = new DatagramSocket();
    }


    synchronized void sendTextToClients(String text) throws UnknownHostException, SocketException {

        for (Client c : users) {
           respondToClient(c.getIp(), c.getPort(), text);
        }

    }

    private synchronized void sendUsers() {

        String usernames = "--USERNAMES--" + getUsernames();

        for (Client c : users) {
            DatagramPacket p = new DatagramPacket(usernames.getBytes(),usernames.length(),c.getIp(),c.getPort());
            try {
                socket.send(p);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized String getUsernames() {

        String userNames = "";

        for (Client c : users) {
            userNames += c.getUsername() + "\n";
        }

        return userNames;

    }

    private void respondToClient(InetAddress senderAddress, int port, String message) {
        try {
            DatagramPacket p = new DatagramPacket(message.getBytes(),message.length(), senderAddress, port);
            socket.send(p);
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

    private synchronized boolean isUniqueUsername(String username) {
        for (Client c : users) {
            if(c.getUsername().equals(username)) return false;
        }
        return true;
    }

    void checkKeyword(String text, DatagramPacket request) throws InterruptedException {

        if(text.contains("--PING-CHECK--")) {
            new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "ALVE")).start();

        } else if(text.contains("--USERNAME--")) {
            String username = text.replaceAll("--USERNAME--","");
            if(isUniqueUsername(username)) {
                    System.out.println("adding");
                    users.add(new Client(request.getAddress(), request.getPort(), username));
                    Thread t = new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "J_OK"));
                    t.join();
                    new Thread(this::sendUsers);
            } else {
                new Thread(() -> respondToClient(request.getAddress(), request.getPort(), "J_ERR")).start();
            }
        } else if (text.contains("--QUIT--")) {
            removeUser(request.getAddress());
            new Thread(this::sendUsers).start();
        }
    }
}
