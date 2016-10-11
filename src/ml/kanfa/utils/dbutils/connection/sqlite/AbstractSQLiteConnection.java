package ml.kanfa.utils.dbutils.connection.sqlite;

import ml.kanfa.parser.No_Authentication;
import ml.kanfa.parser.XMLParser;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Ibrahim Maïga.
 */

public abstract class AbstractSQLiteConnection extends AbstractConnection {

    public AbstractSQLiteConnection(XMLParser parser){
        super();
        this.parser = parser;
    }

    @Override protected void displayError() {

    }

    @Override protected void loadLibrary() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    @Override protected Connection getDriverManagerConnection() throws SQLException {
        return DriverManager.getConnection(((No_Authentication)parser).get());
    }

}
