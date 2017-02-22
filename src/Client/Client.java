package Client;

import java.net.*;

/**
 * Created by David Stovlbaek
 * 21 February 2017.
 */
public class Client {

    private String username;
    private DatagramSocket socket;
    private int serverPort;
    private InetAddress serverIP;


    public Client(String username, String serverIp, int serverPort) throws UnknownHostException, SocketException {
        this.username = username;
        this.serverIP = InetAddress.getByName(serverIp);
        this.serverPort = serverPort;
        socket = ClientListener.socket;
        sendText(username);
    }


    public void sendText(String message) {
        try {
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), serverIP, serverPort);
            socket.send(dp);
            System.out.println("Port: "+ serverPort + " \nIp: " + serverIP + "\nSent!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
