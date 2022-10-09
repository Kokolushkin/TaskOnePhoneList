import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainWindow;

public class Main extends Application {

    private static final double WIDTH = 550;
    private static final double HEIGHT = 500;
    private static final String TITLE = "PhoneInfoApplication";

    public static void main(String[] args){
        launch(args);
    }

    public void start ( Stage stage ) throws Exception {
        stage.setTitle(TITLE);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);

        Scene primaryScene = new Scene(new MainWindow());
        stage.setScene(primaryScene);

        stage.show();
    }
}
