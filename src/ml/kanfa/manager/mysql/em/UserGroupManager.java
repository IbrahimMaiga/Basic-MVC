package ml.kanfa.manager.mysql.em;

import ml.kanfa.annot.ConnectionManager;
import ml.kanfa.entity.UserGroup;
import ml.kanfa.manager.mysql.MySQLManager;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;
import ml.kanfa.utils.dbutils.connection.mysql.UserConnection;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Kanfa.
 */

@ConnectionManager(value = UserConnection.class, param = "userdb")
public class UserGroupManager extends MySQLManager<UserGroup> {


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

    @Override
    protected List<UserGroup> findAll_impl() {
        return null;
    }

}
