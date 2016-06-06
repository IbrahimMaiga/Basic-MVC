package ml.kanfa.utils.dbutils.connection.mysql;

import ml.kanfa.parser.XMLParser;
import ml.kanfa.utils.dbutils.Database;

/**
 * @author Kanfa.
 */

public class UserConnection extends AbstractMySQLConnection {


    public UserConnection() {
        super();
    }

    @Override protected XMLParser getParser() {
        return new XMLParser(new Database("userdb"));
    }
}
