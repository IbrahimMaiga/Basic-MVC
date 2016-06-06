package utils.dbutils.connection.mysql;

import parser.XMLParser;
import utils.dbutils.Database;

/**
 * @author Ibrahim Maïga.
 */
public class DefaultConnection extends AbstractMySQLConnection {


    public DefaultConnection(){}

    @Override protected XMLParser getParser() {
        return new XMLParser(new Database("default"));
    }
}
