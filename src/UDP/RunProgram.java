package UDP; /**
 * Created by David Stovlbaek
 * 16 February 2017.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class RunProgram extends Application{

    private static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception {

        loader = new FXMLLoader(getClass().getResource("/UDP/textWindow.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Another CC Login");
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static TextWindowController getController(){
        return loader.getController();
    }


    public static void main(String[] args) {
        Thread thread = new Thread(new ServerListener());
        thread.start();

        launch(args);
    }
}
