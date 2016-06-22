package ml.kanfa.model;

import ml.kanfa.entity.User;
import ml.kanfa.manager.AbstractManagerFactory;
import ml.kanfa.manager.mysql.em.UserManager;
import ml.kanfa.observer.ILogin;
import ml.kanfa.observer.Observer;

import java.util.List;

/**
 * @author Kanfa.
 */

public class UserModel extends AbstractModel<User>{

    private UserManager userManager = (UserManager)AbstractManagerFactory.getManagerFactory(AbstractManagerFactory.MYSQL_MANAGER_FACTORY).getManager("user");

    public void notifyConnection(Observer observer, User user) {
            ((ILogin)observer).connect(user);
    }

    public void notifyDeconnection(Observer observer, User user, boolean exit) {
            ((ILogin)observer).deconnect(user, exit);
    }

    @Override public void add(User user) {
        this.userManager.add(user);
    }

    @Override public void delete(User user) {
        this.userManager.delete(user);
    }

    @Override public void update(User user) {
        this.userManager.update(user);
    }

    @Override public User find(int id) {
       return this.userManager.find(id);
    }

    @Override public List<User> findAll() {
        return this.userManager.findAll();
    }

    public boolean is_correct(User user) {
       return this.userManager.is_correct(user);
    }

    public User createUser(String username, String password){
        return this.userManager.createUser(username, password);
    }

    public boolean canLaunch() {
        return this.userManager.canLaunch();
    }

    public boolean isFailed(){
        return this.userManager.isFailed();
    }
}
