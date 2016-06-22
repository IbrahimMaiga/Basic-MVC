package ml.kanfa.views.user;

import ml.kanfa.controller.UserController;
import ml.kanfa.entity.User;
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
import ml.kanfa.model.Rb;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.LoginAdapter;
import ml.kanfa.views.app.AppStage;

/**
 * @author Kanfa.
 */

public class LoginPane extends BorderPane{

    private ConnectAction action;

    private TextField loginField;

    private PasswordField passwordField;

    private Label errorLabel;

    private HBox box;

    private Button connectButton;

    private UserModel userModel;

    private UserController userController;

    private String username;

    private Rb rb;

    public LoginPane(String username, Rb rb){
        this.username = username;
        this.rb = rb;
        this.userModel = new UserModel();
        this.action = new ConnectAction();
        this.userModel.addObserver(this.action);
        this.userController =  new UserController(this.userModel, this.rb);
        this.initialize();
    }

    public LoginPane(){
        this("", new Rb());
    }

    private void initialize(){
        GridPane gridPane = new GridPane();
        this.loginField = new TextField(this.username);
        this.passwordField = new PasswordField();
        this.errorLabel = new Label();
        this.errorLabel.setBorder(new Border(new BorderStroke(Color.valueOf("#c97b7b"), BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
        this.errorLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), new Insets(1))));
        this.errorLabel.setPrefHeight(30);
        this.box = new HBox();
        this.box.setPrefHeight(30);
        this.box.setAlignment(Pos.CENTER);
        this.setBottom(box);
        this.errorLabel.setTextFill(Color.RED);
        this.errorLabel.setFont(new Font("Verdana", 14));
        this.connectButton = new Button(rb.get("LoginPane.connectBtn.text"));
        this.loginField.setPromptText(rb.get("LoginPane.textfield.prompt_text"));
        this.passwordField.setPromptText(rb.get("LoginPane.password_field.prompt_text"));
        this.passwordField.disableProperty().bind(this.loginField.textProperty().isEmpty());
        this.connectButton.disableProperty().bind(this.loginField.textProperty().isEmpty().or(this.passwordField.textProperty().isEmpty()));
        this.connectButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                this.logIn();
            }
        });

        this.connectButton.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER){
                this.logIn();
            }
        });

        gridPane.add(this.loginField, 0, 0, 3, 1);
        gridPane.add(this.passwordField, 0, 1, 3, 1);
        gridPane.add(this.connectButton, 0, 2, 2, 1);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setMargin(this.connectButton, new Insets(5, 0, 0, 0));
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(0))));
        BorderPane.setMargin(box, new Insets(0, 0, 20, 0));
        this.setCenter(gridPane);
    }

    private void logIn(){
        User user = new User();
        user.setUsername(loginField.getText());
        user.setPassword(passwordField.getText());
        this.userController.control(this.action, user);
    }

    public void changeFocus(){
        this.passwordField.requestFocus();
    }
    private void close(){
        this.getScene().getWindow().hide();
    }


    public class ConnectAction extends LoginAdapter {

        @Override public void connect(User user) {
            close();
            Platform.runLater(
                    () -> new AppStage(user, userModel)
            );
        }

        @Override public void showError(String message) {
            Service<Void> service = new Service<Void>() {
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
