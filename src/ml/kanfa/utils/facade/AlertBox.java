package ml.kanfa.utils.facade;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Ibrahim Maïga.
 */
public class AlertBox {

    private AlertBox(){}

    public static ButtonType showInformationAlert(String message, String title, String header){
        return showAlert(message, title, header, Alert.AlertType.INFORMATION);
    }

    public static ButtonType showConfirmAlert(String message, String title, String header){
        return showAlert(message, title, header, Alert.AlertType.CONFIRMATION);
    }

    public static ButtonType showErrorAlert(String message, String title, String header){
        return showAlert(message, title, header, Alert.AlertType.ERROR);
    }

    public static ButtonType showErrorConfirmAlert(String message, String title, String header){
        final ImageView graphics = new ImageView(new Image(AlertBox.class.getResourceAsStream("/ml/kanfa/icon/error-32x32.png")));
        return showAlert(message, title, header, Alert.AlertType.CONFIRMATION, graphics);
    }

    private static ButtonType showAlert(String message, String title, String header, Alert.AlertType type){
        return showAlert(message, title, header, type, null);
    }

    private static ButtonType showAlert(String message, String title, String header, Alert.AlertType type, Node graphics){
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        if (graphics!= null) alert.setGraphic(graphics);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait().get();
    }
}