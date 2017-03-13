package ml.kanfa.model;

import ml.kanfa.entity.UserGroup;
import ml.kanfa.manager.AbstractManagerFactory;
import ml.kanfa.manager.mysql.em.UserGroupManager;

import java.util.List;

/**
 * @author Ibrahim Maïga.
 */
public class UserGroupModel extends ml.kanfa.model.AbstractModel<UserGroup> {

    private static UserGroupModel userGroupModel = new UserGroupModel();
    public static UserGroupModel instance() {
        return userGroupModel;
    }

    private static class LazyInitialisation {
        private static UserGroupManager userGroupManager = (UserGroupManager) AbstractManagerFactory.getManagerFactory(AbstractManagerFactory.ManagerType.MYSQL).getManager("user_group");
    }
    @Override
    public boolean add(UserGroup userGroup) {
        return LazyInitialisation.userGroupManager.add(userGroup);
    }

    @Override
    public boolean delete(UserGroup userGroup) {
        return false;
    }

    @Override
    public boolean update(UserGroup userGroup) {
        return false;
    }

    @Override
    public UserGroup find(int id) {
        return LazyInitialisation.userGroupManager.find(id);
    }

    @Override
    public List<UserGroup> findAll() {
        return LazyInitialisation.userGroupManager.findAll();
    }
}
