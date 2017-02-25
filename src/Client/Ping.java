package Client;

import java.net.DatagramPacket;

/**
 * Created by BorisGrunwald on 25/02/2017.
 */
public class Ping implements Runnable {

    private Client pingSender;

    public Ping(Client pingSender) {
        this.pingSender = pingSender;
    }


    @Override
    public void run() {

        String pingMessage = "PING-CHECK";

        DatagramPacket ping = new DatagramPacket(pingMessage.getBytes(),pingMessage.length(),pingSender.getServerIP(),pingSender.getServerPort());

        while (true) {

            try {
                //Ping server every minute
                Thread.sleep(10000);
                pingSender.sendText(pingMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    }
}
