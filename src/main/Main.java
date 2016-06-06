package main;

import javafx.application.Application;
import javafx.stage.Stage;
import views.user.Login;

/**
 * @author Ibrahim Maïga.
 */

public class Main extends Application{


    @Override public void start(Stage primaryStage) throws Exception {
        Login.showLoginPane(primaryStage);
    }

    public static void main(String[] args){
        launch(args);
    }
}
