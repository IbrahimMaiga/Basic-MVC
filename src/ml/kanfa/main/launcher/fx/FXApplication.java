package ml.kanfa.main.launcher.fx;

import javafx.application.Application;
import javafx.stage.Stage;
import ml.kanfa.views.fx.user.Login;

/**
 * @author Kanfa.
 */

public class FXApplication extends Application{


    @Override public void start(Stage primaryStage) throws Exception {
        Login.showLoginPane(primaryStage);
    }

    public static void main(String[] args){
        launch(args);
    }
}
