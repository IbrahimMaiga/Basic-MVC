package ml.kanfa.manager;

import java.sql.SQLException;

/**
 * @author Ibrahim Maïga.
 */
public interface Pingable {
    boolean ping() throws SQLException;
}
