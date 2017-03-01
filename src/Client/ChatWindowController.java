package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by BorisGrunwald on 21/02/2017.
 */
public class ChatWindowController {


    public Text username;
    public TextArea chatBox;
    public TextArea textToSend;
    public TextArea onlineUsers;


    @FXML
    public void initialize() throws SocketException, UnknownHostException {
        chatBox.setEditable(false);
        onlineUsers.setEditable(false);
        username.setText(EnterUsernameController.staticUsername);
        Thread ping = new Thread(new Ping());
        ping.start();

        try {
         Thread thread = new Thread(new ClientListener());
         thread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //Shut down Thread
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ServerConnection.getConn().sendKeyword("--QUIT--:" + username.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "Shutdown-thread"));
    }

    public void sendButton(ActionEvent actionEvent) throws SocketException, UnknownHostException {
        ServerConnection conn = ServerConnection.getConn();
        conn.sendText(textToSend.getText());
        textToSend.clear();
    }

    public void quitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}
