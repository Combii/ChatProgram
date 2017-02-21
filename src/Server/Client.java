package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
public class Client {

    private DatagramSocket socket;
    private String username;
    private InetAddress ip;
    private int port;

    public Client(InetAddress ip, int port) {
        try {
            this.socket = new DatagramSocket(port);
            this.ip = ip;
            this.port = port;
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort () {
        return socket.getLocalPort();
    }

    public String getUsername() {
        return username;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void sendText(String message) {
        try {

            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, port);
            socket.send(dp);
            System.out.println("Port: "+ port + " \nIp: " + ip + "\nSent!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
