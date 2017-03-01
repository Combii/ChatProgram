package Server;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by David Stovlbaek
 * 01 March 2017.
 */
public class ClientList {
    static HashSet<Client> users = new HashSet<>();
    private DatagramSocket socket;

    public ClientList() throws SocketException {
        socket = new DatagramSocket();
    }


    synchronized void sendTextToClients(String text) {

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

    private synchronized void removeUser(String username){
        System.out.println("REMOVING USERNAME: " + username);
        Iterator<Client> iterator = users.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getUsername().equals(username)){
                iterator.remove();
                return;
            }
        }
    }

    private synchronized boolean isUniqueUsername(String username) {
        for (Client c : users) {
            if(c.getUsername().equals(username)) return false;
        }
        return true;
    }

    void checkKeyword(String text, DatagramPacket request) {

        if(text.contains("--PING-CHECK--")) {
            respondToClient(request.getAddress(), request.getPort(), "ALVE");

        } else if(text.contains("--USERNAME--")) {
            String username = text.replaceAll("--USERNAME--","");
            if(isUniqueUsername(username)) {
                    System.out.println("adding");
                    users.add(new Client(request.getAddress(), request.getPort(), username));
                    respondToClient(request.getAddress(), request.getPort(), "J_OK");
                    sendUsers();
            } else {
                respondToClient(request.getAddress(), request.getPort(), "J_ERR");
            }
        } else if (text.contains("--QUIT--:")) {
            removeUser(text.substring(9, text.length()));
            sendUsers();
        }
    }
}
