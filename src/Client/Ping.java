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
                new Thread(this::waitResponeFromServer).start();
                //Wait for respone
                Thread.sleep(5000);
                if(!responeFromServer)
                    System.out.println("SERVER IS OFFLINE");
                else
                    responeFromServer = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void waitResponeFromServer(){
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
