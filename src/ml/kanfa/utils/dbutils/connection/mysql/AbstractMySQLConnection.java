package ml.kanfa.utils.dbutils.connection.mysql;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import ml.kanfa.parser.XMLParser;
import ml.kanfa.utils.dbutils.IUser;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Kanfa.
 */

public abstract class AbstractMySQLConnection extends AbstractConnection {

    AbstractMySQLConnection(){
        super();
    }

    @Override protected void loadLibrary() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override protected void displayError() {
        if ((this instanceof IUser) && !this.fistConnection){
            Platform.exit();
        }
    }

    @Override protected Connection getDriverManagerConnection() throws SQLException {
        return DriverManager.getConnection(
                getParser().getHost(),
                getParser().getUsername(),
                getParser().getPassword()
        );
    }

    protected abstract XMLParser getParser();
}
