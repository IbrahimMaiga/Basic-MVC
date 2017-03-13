package ml.kanfa.gui.fx.user;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ml.kanfa.controller.UserController;
import ml.kanfa.entity.User;
import ml.kanfa.gui.fx.app.app.AppStage;
import ml.kanfa.model.DIC;
import ml.kanfa.model.Rb;
import ml.kanfa.observer.LoginStub;
import ml.kanfa.utils.system.SystemUtils;

/**
 * @author Ibrahim Maïga.
 */
public class LoginPane extends BorderPane{

    private ConnectAction action;
    private TextField loginField;
    private PasswordField passwordField;
    private Label errorLabel;
    private HBox box;
    private UserController userController;
    private String username;
    private Rb rb;
    private DIC container = DIC.getInstance();

    public LoginPane(String username, Rb rb){
        this.username = username;
        this.rb = rb;
        this.action = new ConnectAction();
        this.userController =  new UserController(this.rb);
        this.initialize();
    }

    public LoginPane(){
        this("", Rb.getInstance());
    }

    private void initialize(){
        GridPane gridPane = new GridPane();
        this.loginField = new TextField(this.username);
        this.passwordField = new PasswordField();
        this.errorLabel = new Label();
        this.errorLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), new Insets(1))));
        this.errorLabel.setPrefHeight(30);
        this.box = new HBox();
        this.box.setPrefHeight(30);
        this.box.setAlignment(Pos.CENTER);
        this.setBottom(box);
        this.errorLabel.setTextFill(Color.RED);
        this.errorLabel.setFont(new Font("Verdana", 14));
        final Button connectButton = new Button(rb.get("Login.connectBtn.text"));
        this.loginField.setPromptText(rb.get("Login.textfield.prompt_text"));
        this.passwordField.setPromptText(rb.get("Login.password_field.prompt_text"));
        this.passwordField.disableProperty().bind(this.loginField.textProperty().isEmpty());
        connectButton.disableProperty().bind(this.loginField.textProperty().isEmpty().or(this.passwordField.textProperty().isEmpty()));
        connectButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                this.logIn();
            }
        });

        connectButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                this.logIn();
            }
        });

        this.loginField.setPrefWidth(300);
        this.passwordField.setPrefWidth(300);
        gridPane.add(this.loginField, 0, 0, 3, 1);
        gridPane.add(this.passwordField, 0, 1, 3, 1);
        gridPane.add(connectButton, 0, 2, 2, 1);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setMargin(connectButton, new Insets(5, 0, 0, 0));
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(0))));
        BorderPane.setMargin(box, new Insets(0, 0, 20, 0));
        this.setCenter(gridPane);
    }

    private void logIn(){
        userController.checkAuthentication(action, loginField.getText(), passwordField.getText().toCharArray());
    }

    public void changeFocus(){
        this.passwordField.requestFocus();
    }
    private void close(){
        this.getScene().getWindow().hide();
    }


    public final class ConnectAction extends LoginStub {

        @Override public void connect(User current) {
            close();
            if (container.publisher().getErrors().isEmpty()){
                Platform.runLater(AppStage::new);
            }
            else
                SystemUtils.exit();
        }

        @Override public void show(String message) {
            final Service<Void> service = new Service<Void>() {
                @Override protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override protected Void call() throws Exception {
                            Platform.runLater(() -> {
                                loginField.clear();
                                passwordField.clear();
                                if (box.getChildren().isEmpty()){
                                    errorLabel.setText(message);
                                    box.getChildren().add(errorLabel);
                                }
                            });
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    };
                }
            };

            service.stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
                switch (newValue) {
                    case SUCCEEDED:
                        if (!box.getChildren().isEmpty()){
                            errorLabel.setText("");
                            box.getChildren().remove(errorLabel);
                        }
                        break;
                }
            });
            service.start();
        }
    }
}
