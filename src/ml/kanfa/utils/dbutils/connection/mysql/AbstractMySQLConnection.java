package ml.kanfa.utils.dbutils.connection.mysql;

import ml.kanfa.parser.WithAuthentication;
import ml.kanfa.parser.XMLParser;
import ml.kanfa.utils.Publisher;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractMySQLConnection extends AbstractConnection {

    private static final String HOST = "host";
    private static final String USER = "user";
    private static final String PASS = "password";

    AbstractMySQLConnection(XMLParser parser){
        super();
        this.parser = parser;
    }


    @Override protected void loadLibrary() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override protected void displayError() {
//        MessageBox.showDialogBox("Impossible de se connecter à la base de données");
//        SystemUtils.exit();²
//        if ((this instanceof IUser) && !this.fistConnection){
//        }
        Publisher publisher = Publisher.instance();
        publisher.publish(Publisher.CONNECTION_ERROR);
    }

    @Override protected Connection getDriverManagerConnection() throws SQLException {
        return DriverManager.getConnection(
                ((WithAuthentication) parser).get(HOST),
                ((WithAuthentication) parser).get(USER),
                ((WithAuthentication) parser).get(PASS)
        );
    }

}
