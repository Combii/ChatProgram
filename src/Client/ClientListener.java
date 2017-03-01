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
                while (true) {
                    String text = receiveMessage();

                    if(!isKeyWord(text))
                    EnterUsernameController.getController().chatBox.appendText(text + "\n");
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
    
    private boolean isKeyWord(String checkMessage){
        if (checkMessage.equals("ALVE")) {
            Ping.responseFromServer = true;
            return true;
        }
        else if(checkMessage.contains("--USERNAMES--")) {
            showOnlineUsers(checkMessage);
            return true;
        }
        else if(checkMessage.contains("--USERNAMES--")){
            showOnlineUsers(checkMessage);
            return true;
        }
        return false;
    }

    private void showOnlineUsers(String users){
        users = users.replaceAll("--USERNAMES--", "");
        EnterUsernameController.getController().onlineUsers.setText(users);
    }


}
