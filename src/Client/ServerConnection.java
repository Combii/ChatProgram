package Client;

import java.io.IOException;
import java.net.*;

/**
 * Created by David Stovlbaek
 * 26 February 2017.
 */
public class ServerConnection {

    private DatagramSocket socket = new DatagramSocket();
    private final int messagePort = 1234;
    private final int keyWordPort = 1235;
    private final InetAddress serverIP = InetAddress.getByName("localhost");

    private static ServerConnection conn;

    private ServerConnection() throws UnknownHostException, SocketException {
    }

    public static ServerConnection getConn() throws SocketException, UnknownHostException {
        if(conn == null) {
            conn = new ServerConnection();
            return conn;
        } else return conn;

    }

    public DatagramSocket getSocket(){
        return socket;
    }

    public void sendText(String message) {
        try {
            if(EnterUsernameController.staticUsername.length() != 0)
            message = EnterUsernameController.staticUsername + ": " + message;

            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), serverIP, messagePort);
            socket.send(dp);
            System.out.println("Port: "+ messagePort + " \nIp: " + serverIP + "\nSent!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendKeyword(String keyword) throws IOException {

        DatagramPacket p = new DatagramPacket(keyword.getBytes(),keyword.length(),serverIP,keyWordPort);
        socket.send(p);
        System.out.println("SENT KEYWORD: " + keyword);
    }
}
