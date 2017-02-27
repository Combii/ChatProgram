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
   DatagramSocket socket = conn.getSocket();

    public ClientListener() throws SocketException, UnknownHostException {
    }

    @Override
    public void run() {
        System.out.println(socket.getLocalPort());
                while (true) {
                    String text = receiveMessage();

                    if (text != null) {
                        System.out.println("Message received!");
                        System.out.println(text);

                        EnterUsernameController.getController().chatBox.appendText(text + "\n");
                    }
                }
    }

    public String receiveMessage(){
            try {
                DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                socket.receive(request);
                String text = new String(request.getData(), 0, request.getLength());
                System.out.println("Received Text: " + text);
                return text;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }

    public boolean isServerResponse(String checkWord) {
        if(checkWord.equals("--USERNAME-IS-TAKEN--"))
            return false;
        else if(checkWord.equals("--USERNAME-IS-AVAILABLE--"))
        return true;
        return false;
    }


}
