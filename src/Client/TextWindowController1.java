package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by BorisGrunwald on 21/02/2017.
 */
public class TextWindowController1 {


    public Text username;

    @FXML
    public void initialize() throws UnknownHostException, SocketException {
        username.setText(EnterUsernameController.staticUsername);
       Client user = new Client(username.getText(), "10.0.1.38", 1234);
    }

    public void sendButton(ActionEvent actionEvent) {



    }


}
