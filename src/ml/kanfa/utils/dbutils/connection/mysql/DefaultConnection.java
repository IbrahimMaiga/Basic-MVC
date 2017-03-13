package ml.kanfa.utils.dbutils.connection.mysql;

import ml.kanfa.parser.XMLParser;

/**
 * @author Ibrahim Maïga.
 */
public class DefaultConnection extends AbstractMySQLConnection {

    public DefaultConnection(XMLParser parser){
        super(parser);
    }
    public DefaultConnection(){
        super(null);
    }
}