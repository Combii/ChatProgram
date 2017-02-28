package Client;

import java.io.IOException;
import java.net.*;

/**
 * Created by David Stovlbaek
 * 22 February 2017.
 */
public class Tester {
    public static void main(String[] args) throws UnknownHostException {
        try {
            DatagramSocket socket = new DatagramSocket();
            //Client client = new Client("David", "localhost", 50078);
            InetAddress inetAddress = InetAddress.getByName("localhost");
            DatagramPacket dp = new DatagramPacket("Hej".getBytes(), "Hej".length(), inetAddress, 50078);
            socket.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
