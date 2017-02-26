package Client;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by David Stovlbaek
 * 21 February 2017.
 */
public class ClientListener implements Runnable {

   static DatagramSocket socket;

    public ClientListener() throws SocketException {
        socket = new DatagramSocket();
    }

    @Override
    public void run() {
        try{
            while (true) {
                System.out.println("Socket PORT: " + socket.getLocalPort());
                DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                socket.receive(request);

                System.out.println("Message received!");

                String text = new String(request.getData(),0,request.getLength());
                System.out.println(text);

                EnterUsernameController.getController().chatBox.appendText(text + "\n");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
