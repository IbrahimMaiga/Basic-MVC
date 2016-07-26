package ml.kanfa.views.fx.app;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import ml.kanfa.controller.UserController;
import ml.kanfa.entity.User;
import ml.kanfa.model.DisconnectionType;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.LoginStub;
import ml.kanfa.utils.verifier.Verification;
import ml.kanfa.views.fx.user.Login;

import java.awt.*;

/**
 * @author Ibrahim Maïga.
 */

public class AppStage extends ApplicationComponent {

    private User user;

    private BorderPane container;

    private UserModel userModel;

    private DisconnectionAction action;

    private UserController userController;

    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int VRESIDU = 82;
    private static final int HRESIDU = 2;

    public AppStage(User user, UserModel userModel){
        super();
        this.user = user;
        this.userModel = userModel;
        this.userModel.addObserver((this.action = new DisconnectionAction()));
        this.userController = new UserController(this.userModel, this.rb);
        this.initialize();
        (new Exple()).show();
    }


    private void initialize(){
        this.setTitle(rb.get("App.title"));
        Platform.setImplicitExit(false);
        this.setOnCloseRequest(event -> event.consume());
        this.setResizable(false);
        this.container = new BorderPane();
        this.container.setCenter(new Label(rb.get("App.test") + " " + this.user.getUsername() + " !"));
        MenuItem disconnect = new MenuItem(rb.get("App.MenuItem.disconnect"));
        MenuItem quit = new MenuItem(rb.get("App.MenuItem.quit"));
        Menu file = new Menu("_" + rb.get("App.Menu.file"));
        file.getItems().addAll(disconnect, new SeparatorMenuItem(), quit);
        MenuBar menuBar = new MenuBar(file);
        disconnect.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        quit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));

        disconnect.setOnAction(event -> this.logOut(DisconnectionType.DISCONNECT));

        quit.setOnAction(event -> this.logOut(DisconnectionType.QUIT));

        this.container.setTop(menuBar);
        this.setScene(new Scene(this.container, (dimension.width - HRESIDU), (dimension.height - VRESIDU)));
        this.show();
    }

    private void logOut(DisconnectionType type){
        this.userController.control(this.action, type);
    }

    private void closeCurrent(){
        this.hide();
    }

    private final class DisconnectionAction extends LoginStub {

        @Override public void disconnect(User currentUser, boolean type) {
            user = currentUser;
            Verification<ButtonType> verification = new Verification(rb, type, ButtonType.OK) {
                @Override public void executeAction(boolean type) {
                    if (!userModel.isFailed()) {
                        user.setOnline(false);
                        userModel.update(user);
                        if (type) {
                            closeCurrent();
                            Platform.runLater
                                    (
                                            () -> Login.showLoginPane(new ApplicationComponent(), user.getUsername())
                                    );
                        } else {
                            Platform.exit();
                        }
                    }
                }
            };
            verification.run();
        }
    }
}
