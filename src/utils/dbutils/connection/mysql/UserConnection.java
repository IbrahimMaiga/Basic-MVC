package utils.dbutils.connection.mysql;

import parser.XMLParser;
import utils.dbutils.Database;
import utils.dbutils.UserInterface;

/**
 * @author Ibrahim Maïga.
 */

public class UserConnection extends AbstractMySQLConnection {


    public UserConnection() {
        super();
    }

    @Override protected XMLParser getParser() {
        return new XMLParser(new Database("userdb"));
    }
}
