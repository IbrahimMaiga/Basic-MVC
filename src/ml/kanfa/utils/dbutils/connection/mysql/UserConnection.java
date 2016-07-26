package ml.kanfa.utils.dbutils.connection.mysql;

import ml.kanfa.parser.XMLParser;

/**
 * @author Ibrahim Maïga.
 */

public class UserConnection extends AbstractMySQLConnection {

    public UserConnection(XMLParser parser) {
        super(parser);
    }
    public UserConnection(){
        super(null);
    }
}
