package Server;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.net.UnknownHostException;


/**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
public class ServerWindowController {


    public TextArea console;

    @FXML
    public void initialize() throws UnknownHostException {
        console.setEditable(false);
        console.appendText("-- SERVER ONLINE --" + "\n");
    }

}
