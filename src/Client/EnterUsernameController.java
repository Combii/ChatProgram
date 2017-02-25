package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by BorisGrunwald on 21/02/2017.
 */
public class EnterUsernameController {


    public Button okButton;
    public TextField userName;
    public static String staticUsername = "";

    Stage stage;
    private Parent root;
    private static FXMLLoader loader;

    public void wowButtonClicked(ActionEvent actionEvent) {
    }

    public void okButtonClicked(ActionEvent actionEvent) {
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

    public static ChatWindowController getController(){
        return loader.getController();
    }


}
