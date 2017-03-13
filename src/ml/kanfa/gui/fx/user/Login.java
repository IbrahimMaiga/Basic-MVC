package ml.kanfa.gui.fx.user;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ml.kanfa.model.DIC;
import ml.kanfa.model.Rb;

/**
 * @author Ibrahim Maïga.
 */
public class Login {

    private static Rb rb = Rb.getInstance();
    private Login(){}

    public static void showLoginPane(Stage stage, String username){
        DIC.getInstance().setLoginFrame(stage);
        final LoginPane loginPane = new LoginPane(username, rb);
        Scene scene = new Scene(loginPane, 600, 300);
        stage.setTitle(rb.get("Login.title"));
        stage.centerOnScreen();
        stage.setOnCloseRequest(event -> Platform.exit());
        if (Platform.isSupported(ConditionalFeature.UNIFIED_WINDOW)){
            stage.initStyle(StageStyle.UNIFIED);
        }
        stage.setScene(scene);
        if (!username.isEmpty())
            loginPane.changeFocus();

        //if (userModel.canLaunch())
            stage.show();
    }

    public static void showLoginPane(Stage stage){
        showLoginPane(stage, "");
    }
}
