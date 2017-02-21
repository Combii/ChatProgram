package Server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
public class TextWindowController {

    public TextField port;
    public TextField hostAddress;

    public Text textReceiving;
    public TextArea textToSend;
    public Text myIP;

    @FXML
    public void initialize() throws UnknownHostException {
        myIP.setText(InetAddress.getLocalHost().getHostAddress());
    }


    public void sendButton(ActionEvent actionEvent) {
    }
}
