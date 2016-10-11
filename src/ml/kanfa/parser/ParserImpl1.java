package ml.kanfa.parser;

import ml.kanfa.utils.dbutils.Database;

/**
 * @author Ibrahim Maïga.
 */
public class ParserImpl1 extends XMLParser implements With_Authentication {

    public ParserImpl1(Database database) {
        super(database);
    }

    @Override public String get(String type) {
        return this.get(this.getDatabase().getName(), type);
    }

    @Override public String getConfigPath() {
        return With_Authentication.CONFIG_PATH;
    }
}
