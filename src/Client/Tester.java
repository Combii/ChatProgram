package Client;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by David Stovlbaek
 * 22 February 2017.
 */
public class Tester {
    public static void main(String[] args) throws UnknownHostException {
        try {
            Client client = new Client("Hello", "172.20.10.4", 1234);
            client.sendText("Hej");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }


        InetAddress ownIP=InetAddress.getLocalHost();
        System.out.println("IP of my system is := "+ownIP.getAddress().toString());
    }
}
