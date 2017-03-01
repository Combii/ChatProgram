package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by BorisGrunwald on 21/02/2017.
 */
public class EnterUsernameController {


    public Button okButton;
    public TextField userName;
    public static String staticUsername = "";
    public Text warningMessage;

    Stage stage;
    private Parent root;
    private static FXMLLoader loader;

    public void wowButtonClicked(ActionEvent actionEvent) {
    }

    public void okButtonClicked(ActionEvent actionEvent) throws IOException {
        if(!checkUsernameValid(userName.getText())){
            warningMessage.setText("Username must be max 12 chars long, only letters, digits, ‘-‘ and ‘_’ allowed!");
        }
        else if(checkUsernameIsUnique(userName.getText())){
            staticUsername = userName.getText();

            stage = (Stage) okButton.getScene().getWindow();
            try {
                loader = new FXMLLoader(getClass().getResource("/Client/ChatWindow.fxml"));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            warningMessage.setText("Username already taken");
        }
    }

    private boolean checkUsernameValid(String userName){
        return userName.matches("^[a-zA-Z0-9_-]*$") && userName.length() <= 12 && userName.length() != 0;
    }

    private boolean checkUsernameIsUnique(String userName) throws IOException {
        ClientListener listener = new ClientListener();

        ServerConnection conn = ServerConnection.getConn();
        conn.sendKeyword("--USERNAME--" + userName);


        String receiveText = listener.receiveMessage();
        System.out.println(receiveText);

        return checkUsername(receiveText);
    }

    private boolean checkUsername(String checkMessage) {
        if(checkMessage.equals("J_ERR"))
            return false;
        else if(checkMessage.equals("J_OK"))
            return true;
        return false;
    }

    public static ChatWindowController getController(){
        return loader.getController();
    }
}
