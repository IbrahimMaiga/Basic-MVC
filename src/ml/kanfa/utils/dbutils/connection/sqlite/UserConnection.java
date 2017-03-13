package ml.kanfa.utils.dbutils.connection.sqlite;

import ml.kanfa.parser.XMLParser;

/**
 * @author Ibrahim Maïga.
 */
public class UserConnection extends AbstractSQLiteConnection {

    public UserConnection(XMLParser parser) {
        super(parser);
    }

    public UserConnection(){
        super(null);
    }
}
