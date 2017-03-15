package ml.kanfa.parser;

import ml.kanfa.utils.dbutils.Database;

/**
 * @author Ibrahim Maïga.
 */
public class NoAuthenticationParserImpl extends XMLParser implements NoAuthentication {

    public NoAuthenticationParserImpl(Database database) {
        super(database);
    }

    @Override public String get() {
        return this.get(getDatabase().getName(), "host");
    }

    @Override protected String getConfigPath() {
        return getPath();
    }
}
