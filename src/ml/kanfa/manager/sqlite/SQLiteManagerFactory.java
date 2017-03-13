package ml.kanfa.manager.sqlite;

import ml.kanfa.manager.AbstractManagerFactory;

/**
 * @author Ibrahim Maïga.
 */
public class SQLiteManagerFactory extends AbstractManagerFactory {

    @Override protected String getEMPath() {
        return "ml.kanfa.manager.sqlite.em";
    }

    @Override protected String getSuffix() {
        return "Manager";
    }

    @Override protected String getConnectionType() {
        return "sqlite";
    }

    @Override protected boolean provideAuthentication() {
        return false;
    }
}
