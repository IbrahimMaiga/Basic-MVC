package ml.kanfa.views.app;

import ml.kanfa.controller.UserController;
import ml.kanfa.entity.User;
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
import ml.kanfa.model.DeconnectionType;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.LoginAdapter;
import ml.kanfa.utils.verifier.WindowOpenedVerifier;
import ml.kanfa.views.user.Login;

import java.awt.*;
import java.util.Optional;

/**
 * @author Kanfa.
 */

public class AppStage extends ApplicationComponent {

    private User user;

    private BorderPane container;

    private UserModel userModel;

    private DeconnectionAction action;

    private UserController userController;

    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    private static final int VRESIDU = 82;
    private static final int HRESIDU = 2;

    public AppStage(User user, UserModel userModel){
        super();
        this.user = user;
        this.userModel = userModel;
        this.userModel.addObserver((this.action = new DeconnectionAction()));
        this.userController = new UserController(this.userModel, this.rb);
        this.initialize();
        (new Exple()).show();
    }


    private void initialize(){
        this.setTitle(rb.get("AppStage.title"));
        Platform.setImplicitExit(false);
        this.setOnCloseRequest(event -> event.consume());
        this.setResizable(false);
        this.container = new BorderPane();
        this.container.setCenter(new Label(rb.get("AppStage.test") + " " + this.user.getUsername() + " !"));
        MenuItem deconnect = new MenuItem(rb.get("AppStage.MenuItem.deconnect"));
        MenuItem quit = new MenuItem(rb.get("AppStage.MenuItem.quit"));
        Menu file = new Menu(rb.get("AppStage.Menu.file"));
        file.getItems().addAll(deconnect, new SeparatorMenuItem(), quit);
        MenuBar menuBar = new MenuBar(file);
        deconnect.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        quit.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        deconnect.setOnAction(event -> {
            this.logOut(DeconnectionType.DECONNECT);
        });

        quit.setOnAction(event -> {
            this.logOut(DeconnectionType.QUIT);
        });

        this.container.setTop(menuBar);
        this.setScene(new Scene(this.container, (dimension.width - HRESIDU), (dimension.height - VRESIDU)));
        this.show();
    }

    private void logOut(DeconnectionType type){
        this.userController.control(this.action, type);
    }

    private void closeCurrent(){
        this.hide();
    }

    public class DeconnectionAction extends LoginAdapter {

        @Override public void deconnect(User currentUser, boolean type) {
            boolean can = true;
            boolean btask = false;
            boolean yes_option = false;
            user = currentUser;
            String message = type ? rb.get("AppStage.Alert.deconnect_confirm_message") :
                    rb.get("AppStage.Alert.deconnect_quit_message");
            if (WindowOpenedVerifier.hasElements()){
                String str = String.valueOf(WindowOpenedVerifier.openedCount()) + " ";
                str += (WindowOpenedVerifier.openedCount() == 1) ? rb.get("AppStage.OneOtherFrame.warning_msg") :
                        rb.get("AppStage.OtherFrame.warning_msg");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                alert.setTitle(type ? rb.get("AppStage.ConfirmAlert1.deconnect_title") :
                                       rb.get("AppStage.ConfirmAlert1.quit_title"));

                alert.setHeaderText(type ? rb.get("AppStage.ConfirmAlert1.deconnect_header") :
                                            rb.get("AppStage.ConfirmAlert1.quit_header"));
                alert.setContentText(str + " " + (type ? rb.get("AppStage.deconnect_msg") : rb.get("AppStage.quit_msg")));
                Optional<ButtonType> response1 = alert.showAndWait();
                if (response1.get() == ButtonType.OK){
                    WindowOpenedVerifier.disposeAll();
                    btask = true;
                }
                else{
                    can = false;
                }
            }
            if (can){
                if (!btask){
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle(type ? rb.get("AppStage.ConfirmAlert2.deconnect_title") : rb.get("AppStage.ConfirmAlert2.quit_title"));
                    confirmAlert.setHeaderText(type ? rb.get("AppStage.ConfirmAlert2.deconnect_header") : rb.get("AppStage.ConfirmAlert2.quit_header"));
                    confirmAlert.setContentText(message);
                    confirmAlert.getButtonTypes();
                    Optional<ButtonType> response2 = confirmAlert.showAndWait();
                    if (response2.get() == ButtonType.OK){
                        yes_option = true;
                    }
                    else return;
                }
                if (yes_option || btask){
                    executeAction(type);
                }
            }

        }

        private void executeAction(boolean type) {
            if (!userModel.isFailed()) {
                user.setOnline(false);
                userModel.update(user);
                if (type) {
                    closeCurrent();
                    Platform.runLater(
                            () -> Login.showLoginPane(new ApplicationComponent(), user.getUsername())
                    );
                } else {
                    Platform.exit();
                }
            }
        }
    }

}
