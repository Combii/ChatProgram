package Client;

import java.net.DatagramSocket;

/**
 * Created by David Stovlbaek
 * 21 February 2017.
 */
public class Client {

    private String username;
    private DatagramSocket socket;


    public Client(String username, DatagramSocket socket) {
        this.username = username;
        this.socket = socket;
    }
}
