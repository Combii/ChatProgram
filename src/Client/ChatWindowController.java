package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by BorisGrunwald on 21/02/2017.
 */
public class ChatWindowController {


    public Text username;
    public TextArea chatBox;
    public TextArea textToSend;


    @FXML
    public void initialize() throws UnknownHostException, SocketException {
        chatBox.setEditable(false);
        username.setText(EnterUsernameController.staticUsername);
        Thread ping = new Thread(new Ping());
        ping.start();

        try {
         Thread thread = new Thread(new ClientListener());
         thread.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendButton(ActionEvent actionEvent) throws SocketException, UnknownHostException {
        ServerConnection conn = ServerConnection.getConn();
        conn.sendText(textToSend.getText());
    }


    public void quitButton(ActionEvent actionEvent) {
        System.exit(0);
    }
}
