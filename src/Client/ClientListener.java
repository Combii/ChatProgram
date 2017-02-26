package Client;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by David Stovlbaek
 * 21 February 2017.
 */
public class ClientListener implements Runnable {

   ServerConnection conn = ServerConnection.getConn();

    public ClientListener() throws SocketException {
    }

    @Override
    public void run() {
        try{
            while (true) {

                DatagramPacket request = conn.receivePacket();

                System.out.println("Message received!");

                String text = new String(request.getData(),0,request.getLength());

                //if(isServerResponse(text))
                System.out.println(text);

                EnterUsernameController.getController().chatBox.appendText(text + "\n");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /*public boolean isServerResponse(String text) {


    }*/


}
