package Client; /**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class RunClient extends Application{

    private static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loader = new FXMLLoader(getClass().getResource("/Client/EnterUsername.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Chat Program");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();

        //Makes sure program close when click red x in window
        primaryStage.setOnCloseRequest(t -> System.exit(0));
    }


    public static void main(String[] args){
        launch(args);
    }
}
