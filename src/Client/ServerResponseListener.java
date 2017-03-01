package Client;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by BorisGrunwald on 28/02/2017.
 */
public class ServerResponseListener implements Runnable {

    DatagramSocket socket;

    @Override
    public void run() {

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }
}
