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
    private static Client client;

    @FXML
    public void initialize() throws UnknownHostException, SocketException {
        username.setText(EnterUsernameController.staticUsername);
        client = new Client(username.getText(), "localhost", 1234);
        chatBox.setEditable(false);
    }

    public static Client getCLient(){
        return client;
    }

    public void sendButton(ActionEvent actionEvent) {
        client.sendText(textToSend.getText());
    }


}
