package ml.kanfa.utils.dbutils.connection.mysql;

import ml.kanfa.parser.XMLParser;
import ml.kanfa.utils.dbutils.Database;

/**
 * @author Kanfa.
 */
public class DefaultConnection extends AbstractMySQLConnection {

    private static DefaultConnection _instance = new DefaultConnection();

    public DefaultConnection(){}

    @Override protected XMLParser getParser() {
        return new XMLParser(new Database("default"));
    }
}
