package ml.kanfa.model;

import ml.kanfa.entity.User;
import ml.kanfa.manager.AbstractManagerFactory;
import ml.kanfa.manager.mysql.em.UserManager;
import ml.kanfa.observer.ILogin;
import ml.kanfa.observer.Observer;
import ml.kanfa.service.authentication.AuthenticatorSupport;

import java.util.List;

/**
 * @author Ibrahim Maïga.
 */
public class UserModel extends AbstractModel<User> implements AuthenticatorSupport, IConnect{

    private static UserModel userModel = new UserModel();

    private UserModel(){
    }

    public static UserModel instance(){
        return userModel;
    }

    private final static class LazyInitialisation {
        private static UserManager userManager = (UserManager)AbstractManagerFactory.getManagerFactory(AbstractManagerFactory.ManagerType.MYSQL).getManager("user");
    }

    @Override
    public boolean checkAccess(String login, char[] password) {
        return LazyInitialisation.userManager.check(login, password);
    }

    @Override
    public boolean updateState(final User user) {
        return LazyInitialisation.userManager.updateState(user);
    }

    public void notifyConnection(Observer observer, User user) {
        ((ILogin)observer).connect(user);
    }

    public void notifyDisconnection(Observer observer, boolean exit) {
            ((ILogin)observer).disconnect(exit);
    }

    @Override public boolean add(User user) {
        return LazyInitialisation.userManager.add(user);
    }

    @Override public boolean delete(User user) {
        return LazyInitialisation.userManager.delete(user);
    }

    @Override public boolean update(User user) {
        return LazyInitialisation.userManager.update(user);
    }

    @Override public User find(int id) {
       return LazyInitialisation.userManager.find(id);
    }

    @Override public List<User> findAll() {
        return LazyInitialisation.userManager.findAll();
    }

    public User createUser(String username, char[] password){
        return LazyInitialisation.userManager.createUser(username, password);
    }

    public boolean canLaunch() {
        return LazyInitialisation.userManager.canLaunch();
    }

    public boolean isFailed(){
        return LazyInitialisation.userManager.isFailed();
    }
}
