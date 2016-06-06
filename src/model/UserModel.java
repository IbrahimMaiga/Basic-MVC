package model;

import entity.User;
import manager.AbstractManagerFactory;
import manager.mysql.em.UserManager;
import observer.LoginInterface;
import observer.Observer;

import java.util.List;

/**
 * @author Ibrahim Maïga.
 */

public class UserModel extends AbstractModel<User>{

    private UserManager userManager = (UserManager)AbstractManagerFactory.getManagerFactory(AbstractManagerFactory.MYSQL_MANAGER_FACTORY).getManager("user");

    public void notifyConnection(Observer observer, User user) {
            ((LoginInterface)observer).connect(user);
    }

    public void notifyDeconnection(Observer observer, User user, boolean exit) {
            ((LoginInterface)observer).deconnect(user, exit);
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
