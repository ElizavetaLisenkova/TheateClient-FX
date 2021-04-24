import javafx.application.Application;
import javafx.stage.Stage;
import controllers.Login;

public class RunFXApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setResizable(false);
        Login.loadView(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
