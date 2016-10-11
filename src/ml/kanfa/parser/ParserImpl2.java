package ml.kanfa.parser;

import ml.kanfa.utils.dbutils.Database;

/**
 * @author Ibrahim Maïga.
 */
public class ParserImpl2 extends XMLParser implements No_Authentication {

    public ParserImpl2(Database database) {
        super(database);
    }

    @Override public String get() {
        return this.get(getDatabase().getName(), "host");
    }

    @Override protected String getConfigPath() {
        return No_Authentication.CONFIG_PATH;
    }
}
