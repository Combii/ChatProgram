package Client;

import java.io.IOException;
import java.net.*;

/**
 * Created by David Stovlbaek
 * 26 February 2017.
 */
public class ServerConnection {

    private DatagramSocket socket = new DatagramSocket();
    private int serverPort = 1234;
    private InetAddress serverIP = InetAddress.getByName("localhost");

    private static ServerConnection conn;

    private ServerConnection() throws UnknownHostException, SocketException {
        conn = new ServerConnection();
    }

    public static ServerConnection getConn(){
        return conn;
    }


    public DatagramPacket receivePacket() {
        DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
        try {
            socket.receive(request);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
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
