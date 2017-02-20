package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
public class Client {

    public static void sendText(int port, String ip, String message) {
    try {
        DatagramSocket socket = new DatagramSocket();
        InetAddress netIp = InetAddress.getByName(ip);

        DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), netIp, port);
        socket.send(dp);
        System.out.println("Port: "+ port + " \nIp: " + ip + "\nSent!");
    } catch (Exception e){
        e.printStackTrace();
    }
    }

}
