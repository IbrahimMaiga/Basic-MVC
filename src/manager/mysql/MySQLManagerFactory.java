package manager.mysql;

import manager.AbstractManagerFactory;

/**
 * @author Ibrahim Maïga.
 */

public class MySQLManagerFactory extends AbstractManagerFactory {

    @Override protected String entityPath() {
        return "manager.mysql.em";
    }

    @Override protected String suffix() {
        return "Manager";
    }

    @Override protected String connectionType() {
        return "mysql";
    }
}
