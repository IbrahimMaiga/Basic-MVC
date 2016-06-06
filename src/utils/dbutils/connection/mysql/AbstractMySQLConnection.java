package utils.dbutils.connection.mysql;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import utils.dbutils.UserInterface;
import utils.dbutils.connection.AbstractConnection;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractMySQLConnection extends AbstractConnection {

    AbstractMySQLConnection(){
        super();
    }

    @Override protected void loadLibrary() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override protected void displayError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Erreur de connexion a la base de données");
        alert.setHeaderText("Erreur");
        alert.setTitle("Connexion impossible");
        alert.showAndWait();
        if ((this instanceof UserInterface) && !this.fistConnection){
            Platform.exit();
        }
    }
}
