package ml.kanfa.manager.mysql;

import ml.kanfa.manager.AbstractManagerFactory;

/**
 * @author Kanfa.
 */

public class MySQLManagerFactory extends AbstractManagerFactory {

    @Override protected String entityPath() {
        return "ml.kanfa.manager.mysql.em";
    }

    @Override protected String suffix() {
        return "Manager";
    }

    @Override protected String connectionType() {
        return "mysql";
    }
}
