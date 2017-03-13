package ml.kanfa.manager.mysql;

import ml.kanfa.manager.AbstractManagerFactory;

/**
 * @author Ibrahim Maïga.
 */
public class MySQLManagerFactory extends AbstractManagerFactory {

    @Override protected String getEMPath() {
        return "ml.kanfa.manager.mysql.em";
    }

    @Override protected String getSuffix() {
        return "Manager";
    }

    @Override protected String getConnectionType() {
        return "mysql";
    }

    @Override protected boolean provideAuthentication() {
        return true;
    }
}
