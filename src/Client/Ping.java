package Client;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by BorisGrunwald on 25/02/2017.
 */
public class Ping implements Runnable {


    ServerConnection conn = ServerConnection.getConn();

    private boolean responeFromServer = false;

    public Ping() throws SocketException, UnknownHostException {
    }


    @Override
    public void run() {

        String pingMessage = "--PING-CHECK--";

        while (true) {
            try {
                //Ping server every minute
                Thread.sleep(10000);
                conn.sendText(pingMessage);
                new Thread(this::waitResponseFromServer).start();
                //Wait for response
                Thread.sleep(5000);
                if(!responeFromServer)
                    EnterUsernameController.getController().chatBox.appendText("--SERVER IS OFFLINE--\n");
                else
                    responeFromServer = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void waitResponseFromServer(){
                String message;
                try {
                    message = new ClientListener().receiveMessage();
                    if (message.equals("ALVE"))
                        responeFromServer = true;
                } catch (SocketException | UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
