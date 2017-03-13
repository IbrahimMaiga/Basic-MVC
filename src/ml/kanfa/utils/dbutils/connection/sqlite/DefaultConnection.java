package ml.kanfa.utils.dbutils.connection.sqlite;

import ml.kanfa.parser.XMLParser;

/**
 * @author Ibrahim Maïga.
 */
public class DefaultConnection extends AbstractSQLiteConnection {

    public DefaultConnection(XMLParser xmlParser){
        super(xmlParser);
    }
    public DefaultConnection(){
        super(null);
    }
}
