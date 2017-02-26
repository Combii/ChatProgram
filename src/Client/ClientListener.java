package Client;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by David Stovlbaek
 * 21 February 2017.
 */
public class ClientListener implements Runnable {

   ServerConnection conn = ServerConnection.getConn();
   DatagramSocket socket;

    public ClientListener() throws SocketException, UnknownHostException {
    }

    @Override
    public void run() {

        socket = conn.getSocket();

        System.out.println(socket.getLocalPort());

        try{

            while (true) {

                DatagramPacket request = new DatagramPacket(new byte[1024], 1024);

                socket.receive(request);

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
