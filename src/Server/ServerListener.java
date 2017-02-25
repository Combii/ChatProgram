package Server;
/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;


public class ServerListener implements Runnable{

    DatagramSocket socket;
    private HashSet<Client> users = new HashSet<>();

    @Override
    public void run() {
        try{
        /** Destination ports and ip */
        int port = 1234;
        //InetAddress ip = InetAddress.getByName("localhost");

        socket = new DatagramSocket(1234);


            System.out.println(socket.getInetAddress());
            System.out.println(socket.getLocalAddress());
            System.out.println(socket.getPort());
            System.out.println(socket.getLocalPort());


            
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
            socket.receive(request);


            System.out.println("Message received!");

            String text = new String(request.getData(),0,request.getLength());


            //If user is not in the chat room then add them
            if(checkUser(request.getAddress())) {
                System.out.println("user not in room. Adding");
                users.add(new Client(request.getAddress(),request.getPort(), text));

                for (Client c : users){
                    System.out.println(c);
                }

            } else {
                sendTextToClients(text,request.getAddress());
            }



            RunProgram.getController().textReceiving.setText(text);
        }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendTextToClients(String text, InetAddress senderAdress) throws UnknownHostException {


        Client sender = identifyClient(senderAdress);



        System.out.println(sender);

        String username = sender.getUsername();
        text = username + ": " + text;

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

    private Client identifyClient(InetAddress ip) {

        for (Client c : users) {
            if (c.getIp().equals(ip)) return c;
        }
        
        return null;

    }

    private boolean checkUser(InetAddress address) {

        for (Client c : users) {
            if(c.getIp().equals(address)) return false;
        }

        return true;

    }

}