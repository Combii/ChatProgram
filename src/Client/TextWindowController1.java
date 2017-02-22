package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.net.UnknownHostException;

/**
 * Created by BorisGrunwald on 21/02/2017.
 */
public class TextWindowController1 {


    public Text username;

    @FXML
    public void initialize() throws UnknownHostException {
        username.setText(EnterUsernameController.staticUsername);
       // Client user = new Client()
    }

    public void sendButton(ActionEvent actionEvent) {



    }


}
