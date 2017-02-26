package Client;

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
