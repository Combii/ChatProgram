package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by BorisGrunwald on 28/02/2017.
 */
public class KeywordListener implements Runnable {

    DatagramSocket socket;
    DatagramPacket packet;
    Set<String> usernames = new HashSet<>();

    @Override
    public void run() {


        try {
            socket = new DatagramSocket(1235);
        } catch (SocketException e) {
            e.printStackTrace();
        }


        while (true) {

            packet = new DatagramPacket(new byte[1024],1024);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String keyword = new String(packet.getData(),0,packet.getLength());

            checkKeyword(keyword);

        }

    }

    private void checkKeyword(String keyword) {

        

    }


}
