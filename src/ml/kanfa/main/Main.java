package ml.kanfa.main;

import javafx.application.Application;
import javafx.stage.Stage;
import ml.kanfa.views.user.Login;

/**
 * @author Kanfa.
 */

public class Main extends Application{


    @Override public void start(Stage primaryStage) throws Exception {
        Login.showLoginPane(primaryStage);
    }

    public static void main(String[] args){
        launch(args);
    }
}
