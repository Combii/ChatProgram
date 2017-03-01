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

                    if (text.equals("ALVE")) {
                    Ping.responseFromServer = true;
                    }
                    else if(text.contains("--USERNAMES--")){
                        showOnlineUsers(text);
                    }

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

    public boolean isKeyWord(String checkWord) {
        if(checkWord.equals("J_ERR"))
            return false;
        else if(checkWord.equals("J_OK"))
        return true;
        else if (checkWord.equals("ALVE"))
            Ping.responseFromServer = true;
        return false;
    }


    private void showOnlineUsers(String users){
        users = users.replaceAll("--USERNAMES--", "");
        EnterUsernameController.getController().onlineUsers.setText(users);
    }


}
