package ml.kanfa.utils.facade;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @author Ibrahim Maïga.
 */

public class AlertBox {

    private AlertBox(){}

    public static void showInformationAlert(String message, String title, String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    public static ButtonType showConfirmAlert(String message, String title, String header){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait().get();
    }
}