package Client;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by BorisGrunwald on 25/02/2017.
 */
public class Ping implements Runnable {


    ServerConnection conn = ServerConnection.getConn();

    static boolean responseFromServer = false;

    public Ping() throws SocketException, UnknownHostException {
    }


    @Override
    public void run() {

        String pingMessage = "--PING-CHECK--";

        while (true) {
            try {
                //Ping server every minute
                Thread.sleep(60000);
                conn.sendKeyword(pingMessage);
                //Wait for response
                Thread.sleep(5000);
                if (!responseFromServer)
                    EnterUsernameController.getController().chatBox.appendText("--SERVER-IS-OFFLINE--\n");
                responseFromServer = false;
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
