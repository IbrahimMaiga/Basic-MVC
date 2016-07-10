package ml.kanfa.utils.dbutils.connection.mysql;

import javafx.application.Platform;
import ml.kanfa.parser.XMLParser;
import ml.kanfa.utils.dbutils.IUser;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import javax.swing.*;
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
