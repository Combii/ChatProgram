package Server; /**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.SocketException;


public class RunServer extends Application{

    private static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loader = new FXMLLoader(getClass().getResource("/Server/ServerWindow.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Chat Program");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static ServerWindowController getController(){
        return loader.getController();
    }


    public static void main(String[] args) throws SocketException {

        new Thread(new ServerListener()).start();
        new Thread(new KeywordListener()).start();

        launch(args);

    }
}
