package ml.kanfa.parser;

import ml.kanfa.utils.dbutils.Database;

/**
 * @author Ibrahim Maïga.
 */
public class WithAuthenticationParserImpl extends XMLParser implements WithAuthentication {

    public WithAuthenticationParserImpl(Database database) {
        super(database);
    }

    @Override public String get(String type) {
        return this.get(this.getDatabase().getName(), type);
    }

    @Override public String getConfigPath() {
        return getPath();
    }
}
