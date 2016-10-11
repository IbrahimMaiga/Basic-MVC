package ml.kanfa.manager.sqlite.em;

import ml.kanfa.entity.UserGroup;
import ml.kanfa.manager.sqlite.SQLiteManager;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.util.List;

/**
 * @author Ibrahim Maïga.
 */
public class UserGroupManager extends SQLiteManager<UserGroup> {

    public UserGroupManager(AbstractConnection abstractConnection) {
        super(abstractConnection);
    }

    @Override
    protected boolean delete_impl(UserGroup object) {
        return false;
    }

    @Override
    protected boolean add_impl(UserGroup object) {
        return false;
    }

    @Override
    protected boolean update_impl(UserGroup object) {
        return false;
    }

    @Override
    protected UserGroup find_impl(int id) {
        return null;
    }

    @Override
    protected List<UserGroup> findAll_impl() {
        return null;
    }

}
