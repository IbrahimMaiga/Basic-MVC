package ml.kanfa.manager.mysql.em;

import ml.kanfa.annot.ConnectionManager;
import ml.kanfa.entity.User;
import ml.kanfa.manager.AbstractManagerFactory;
import ml.kanfa.manager.mysql.MySQLManager;
import ml.kanfa.security.Encryt;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;
import ml.kanfa.utils.dbutils.connection.mysql.UserConnection;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kanfa.
 */

@ConnectionManager(value = UserConnection.class, param = "userdb")
public class UserManager extends MySQLManager<User> {

    private UserGroupManager userGroupManager;

    public UserManager(AbstractConnection abstractConnection) {
        super(abstractConnection);
        this.userGroupManager = (UserGroupManager)AbstractManagerFactory.
                getManagerFactory(AbstractManagerFactory.MYSQL_MANAGER_FACTORY)
                .getManager("user_group");
    }


    @Override public boolean delete_impl(User object) {
        try{
            this.preparedStatement = this.connection.prepareStatement("DELETE FROM users WHERE id = ?");
            this.preparedStatement.setInt(1, object.getId());
            this.preparedStatement.execute();
            this.preparedStatement.close();
            return true;
        }catch (SQLException e){
            this.debug(e);
        }
        return false;
    }

    @Override public boolean add_impl(User object) {
        try{
            this.preparedStatement = this.connection.prepareStatement("INSERT INTO users (username, password, online, group_id) VALUES (?, ?, ?, ?)");
            this.preparedStatement.setString(1, object.getUsername());
            this.preparedStatement.setString(2, Encryt.md5(new String(object.getPassword())));
            this.preparedStatement.setBoolean(3, object.isOnline());
            this.preparedStatement.setInt(4, object.getGroup().getId());
            this.preparedStatement.execute();
            this.preparedStatement.close();
            return true;
        }catch (SQLException e){
            this.debug(e);
        }
        return false;
    }

    @Override public boolean update_impl(User object) {
        try{
            this.preparedStatement = this.connection.prepareStatement("UPDATE users SET username = ?, password = ?, online = ?, group_id = ? " +
                    "WHERE id = ?");
            this.preparedStatement.setString(1, object.getUsername());
            this.preparedStatement.setString(2, Encryt.md5(new String(object.getPassword())));
            this.preparedStatement.setBoolean(3, object.isOnline());
            this.preparedStatement.setInt(4, object.getGroup().getId());
            this.preparedStatement.setInt(5, object.getId());
            this.preparedStatement.execute();
            this.preparedStatement.close();
            return true;
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
            this.debug(e);
        }
        return false;
    }

    @Override public User find_impl(int id) {
        User user = new User();
        try{
            this.preparedStatement = this.connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            this.preparedStatement.setInt(1, id);
            this.resultSet  = this.preparedStatement.executeQuery();
            if (this.resultSet.next()){
                user.setId(id);
                user.setUsername(this.resultSet.getString("username"));
                user.setPassword(this.resultSet.getString("password").toCharArray());
                user.setOnline(this.resultSet.getBoolean("online"));
                user.setGroup(this.userGroupManager.find(this.resultSet.getInt("group_id")));
            }
            this.preparedStatement.close();
            this.resultSet.close();
        }catch (SQLException e){
            this.debug(e);
        }
        return user;
    }

    @Override public List<User> findAll_impl() {
        List<User> users = new ArrayList<>();
        try{
            this.statement = this.connection.createStatement();
            this.resultSet  = this.statement.executeQuery("SELECT * FROM users");
            while(this.resultSet.next()){
                User user = new User();
                user.setId(this.resultSet.getInt("id"));
                user.setUsername(this.resultSet.getString("username"));
                user.setPassword(this.resultSet.getString("password").toCharArray());
                user.setOnline(this.resultSet.getBoolean("online"));
                user.setGroup(this.userGroupManager.find(this.resultSet.getInt("group_id")));
                users.add(user);
            }
            this.statement.close();
            this.resultSet.close();
        }catch (SQLException e){
            this.debug(e);
        }
        return users;
    }

    public boolean is_correct(User user) {
        try{
            this.checkConnection();
            this.preparedStatement = this.connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            this.preparedStatement.setString(1, user.getUsername());
            this.preparedStatement.setString(2, Encryt.md5(new String(user.getPassword())));
            this.resultSet = this.preparedStatement.executeQuery();
            if (this.resultSet.next()) {
                return true;
            }
            this.preparedStatement.close();
            this.resultSet.close();
        }catch (SQLException e){
            this.debug(e);
        }finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public User createUser(String username, char[] password){
        User user = new User();
        try{
            this.checkConnection();
            this.preparedStatement = this.connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            this.preparedStatement.setString(1, username);
            this.preparedStatement.setString(2, Encryt.md5(new String(password)));
            this.resultSet  = this.preparedStatement.executeQuery();
            if (this.resultSet.next()){
                user.setId(this.resultSet.getInt("id"));
                user.setUsername(username);
                user.setPassword(password);
                user.setOnline(this.resultSet.getBoolean("online"));
                user.setGroup(this.userGroupManager.find(this.resultSet.getInt("group_id")));
            }
            this.preparedStatement.close();
            this.resultSet.close();
        }catch (SQLException e){
            this.debug(e);
        }finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
