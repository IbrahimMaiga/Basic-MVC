package ml.kanfa.manager.mysql.em;

import ml.kanfa.annot.ManagerConnection;
import ml.kanfa.entity.UserGroup;
import ml.kanfa.manager.mysql.MySQLManager;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;
import ml.kanfa.utils.dbutils.connection.mysql.UserConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Kanfa.
 */

@ManagerConnection(UserConnection.class)
public class UserGroupManager extends MySQLManager<UserGroup> {


    public UserGroupManager(AbstractConnection abstractConnection) {
        super(abstractConnection);
    }

    @Override public void delete(UserGroup object) {

    }

    @Override public void add(UserGroup object) {

    }

    @Override public void update(UserGroup object) {

    }

    @Override public UserGroup find(int id) {
        UserGroup group = new UserGroup();
        try{
            this.preparedStatement = this.connection.prepareStatement("SELECT gr_name FROM user_group WHERE id = ?");
            this.preparedStatement.setInt(1, id);
            this.resultSet  = this.preparedStatement.executeQuery();
            if (this.resultSet.next()){
                group.setId(id);
                group.setName(this.resultSet.getString("gr_name"));
            }
            this.preparedStatement.close();
            this.resultSet.close();
        }catch (SQLException e){
            this.debug(e);
        }
        return group;
    }

    @Override public List<UserGroup> findAll() {
        return null;
    }
}
