package ml.kanfa.manager.mysql.em;

import ml.kanfa.annot.ConnectionManager;
import ml.kanfa.entity.UserGroup;
import ml.kanfa.manager.mysql.MySQLManager;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;
import ml.kanfa.utils.dbutils.connection.mysql.UserConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ibrahimn Maïga.
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
        final UserGroup group = new UserGroup();
        ResultSet resultSet = null;
        try(
            final PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT gr_name, gr_description FROM user_group WHERE gr_id = ?")
        ){
            preparedStatement.setInt(1, id);
            resultSet  = preparedStatement.executeQuery();
            if (resultSet.next()){
                group.setId(id)
                     .setName(resultSet.getString("gr_name"))
                     .setDescription(resultSet.getString("gr_description"));
            }
        }catch (SQLException e){
            this.debug(e);
        }finally {
            try{
                if (resultSet != null) resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return group;
    }

    @Override
    protected List<UserGroup> findAll_impl() {
        final List<UserGroup> groups = new ArrayList<>();
        ResultSet resultSet = null;
        try(final Statement statement = this.connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM user_group");
            while (resultSet.next()){
                final UserGroup userGroup = new UserGroup().setId(resultSet.getInt("gr_id"))
                                                           .setName(resultSet.getString("gr_name"))
                                                           .setDescription(resultSet.getString("gr_description"));
                groups.add(userGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return groups;
    }
}
