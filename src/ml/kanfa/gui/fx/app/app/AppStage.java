package ml.kanfa.gui.fx.app.app;

import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.event.Event;
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
import javafx.stage.StageStyle;
import ml.kanfa.controller.UserController;
import ml.kanfa.entity.User;
import ml.kanfa.gui.fx.user.Login;
import ml.kanfa.model.DIC;
import ml.kanfa.model.DisconnectionType;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.LoginStub;
import ml.kanfa.utils.Session;
import ml.kanfa.utils.verifier.Verification;

import java.awt.*;

/**
 * @author Ibrahim Maïga.
 */
public class AppStage extends ApplicationComponent {

    private final int ADMIN = 1;

    private UserModel userModel;
    private DisconnectionAction action;
    private UserController userController;
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int VERTICAL_SPACE = 82;
    private static final int HORIZONTAL_SPACE = 2;

    public AppStage(){
        super();
        this.action = new DisconnectionAction();
        this.userModel = DIC.getInstance().getModelFactory().getUserModel();
        this.userController = new UserController(this.rb);
        if (Platform.isSupported(ConditionalFeature.UNIFIED_WINDOW)){
            this.initStyle(StageStyle.UNIFIED);
        }
        this.initialize();
    }

    private void initialize(){
        this.setTitle(rb.get("App.title"));
        Platform.setImplicitExit(false);
        this.setOnCloseRequest(Event::consume);
        this.setResizable(false);
        BorderPane container = new BorderPane();
        final MenuItem disconnect = new MenuItem(rb.get("App.disconnect.title"));
        final MenuItem quit = new MenuItem(rb.get("App.quit.title"));
        final Menu file = new Menu("_" + rb.get("App.file.title"));
        file.getItems().addAll(disconnect, new SeparatorMenuItem(), quit);
        final MenuBar menuBar = new MenuBar(file);

        disconnect.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        quit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        disconnect.setOnAction(event -> this.logOut(DisconnectionType.DISCONNECT));
        quit.setOnAction(event -> this.logOut(DisconnectionType.QUIT));
        container.setTop(menuBar);
        container.setCenter(new Label(Session.currentUser().getUsername()));
        this.setScene(new Scene(container, (dimension.width - HORIZONTAL_SPACE), (dimension.height - VERTICAL_SPACE)));
        this.show();
    }
    private void logOut(DisconnectionType type){
        this.userController.closeSession(this.action, type);
    }

    private void closeCurrent(){
        this.hide();
    }

    private final class DisconnectionAction extends LoginStub {

        @Override public void disconnect(boolean type) {
            Verification<ButtonType> verification = new Verification(rb, type, ButtonType.OK) {
                @Override public void executeAction(boolean type) {
                    if (!userModel.isFailed()) {
                        final User user = Session.currentUser();
                        userModel.updateState(user);
                        user.setOnline(false);
                        if (type) {
                            closeCurrent();
                            Session.removeCurrentUser();
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
