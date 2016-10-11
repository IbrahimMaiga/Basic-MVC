package ml.kanfa.manager.sqlite;

import ml.kanfa.manager.AbstractManager;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

/**
 * @author Ibrahim Maïga.
 */
public abstract class SQLiteManager<T> extends AbstractManager<T> {

    public SQLiteManager(AbstractConnection abstractConnection){
        super(abstractConnection);
    }

    public boolean canLaunch(){
        return this.connection != null;
    }

    public boolean isFailed() {
        return false;
    }
}
