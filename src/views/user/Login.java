package views.user;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Rb;
import model.UserModel;

/**
 * @author Ibrahim Maïga.
 */

public class Login {

    private static UserModel userModel = new UserModel();

    private static Rb rb = new Rb();

    private Login(){}

    public static void showLoginPane(Stage stage, String username){
        LoginPane loginPane = new LoginPane(username, rb);
        Scene scene = new Scene(loginPane, 400, 400);
        stage.setTitle(rb.get("Login.title"));
        stage.centerOnScreen();
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.setScene(scene);

        if (!username.isEmpty())
            loginPane.changeFocus();

        if (userModel.canLaunch())
            stage.show();
    }

    public static void showLoginPane(Stage stage){
        showLoginPane(stage, "");
    }
}
