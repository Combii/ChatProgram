package UDP;

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

    public Client(int port) {
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendText(int port, String ip, String message) {
    try {

        InetAddress netIp = InetAddress.getByName(ip);

        DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), netIp, port);
        socket.send(dp);
        System.out.println("Port: "+ port + " \nIp: " + ip + "\nSent!");
    } catch (Exception e){
        e.printStackTrace();
    }
    }

}
