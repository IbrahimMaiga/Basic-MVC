package ml.kanfa.manager.mysql.em;

import ml.kanfa.annot.ConnectionManager;
import ml.kanfa.entity.Job;
import ml.kanfa.entity.User;
import ml.kanfa.entity.UserGroup;
import ml.kanfa.manager.mysql.MySQLManager;
import ml.kanfa.security.Encrypt;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;
import ml.kanfa.utils.dbutils.connection.mysql.UserConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ibrahim Maïga.
 */
@ConnectionManager(value = UserConnection.class, param = "userdb")
public class UserManager extends MySQLManager<User> {

    private static final String sql = "SELECT users.id, users.firstname, users.lastname, " +
            "users.username, users.password, users.created, users.last_connection, online," +
            "user_group.gr_id, user_group.gr_name, user_group.gr_description, job.job_id, job.job_name, " +
            "job.job_description FROM users INNER JOIN user_group ON users.group_id = user_group.gr_id INNER JOIN job ON users.job_id = job.job_id";

    public UserManager(AbstractConnection abstractConnection) {
        super(abstractConnection);
    }

    @Override
    protected boolean delete_impl(User object) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM users WHERE id = ?")){
            preparedStatement.setInt(1, object.getId());
            return !preparedStatement.execute();
        } catch (SQLException e) {
            this.debug(e);
        }
        return false;
    }

    @Override
    protected boolean add_impl(User object) {
        final String sql = "INSERT INTO users (firstname, lastname, username, password, online, group_id, job_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(final PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, object.getFirstName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.setString(3, object.getUsername());
            preparedStatement.setString(4, Encrypt.md5(new String(object.getPassword())));
            preparedStatement.setBoolean(5, object.isOnline());
            preparedStatement.setInt(6, object.getGroup().getId());
            preparedStatement.setInt(7, object.getJob().getId());
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            this.debug(e);
        }
        return false;
    }

    @Override
    protected boolean update_impl(User object) {
        final String sql = "UPDATE users SET firstname = ?, lastname = ?, username = ?, online = ?, group_id = ?, job_id = ? WHERE id = ?";
        try(final PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, object.getFirstName());
            preparedStatement.setString(2, object.getLastName());
            preparedStatement.setString(3, object.getUsername());
            preparedStatement.setBoolean(4, object.isOnline());
            preparedStatement.setInt(5, object.getGroup().getId());
            preparedStatement.setInt(6, object.getJob().getId());
            preparedStatement.setInt(7, object.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            this.debug(e);
        }
        return false;
    }

    @Override
    protected User find_impl(int id) {
        User user = new User();
        ResultSet resultSet = null;
        try(final PreparedStatement preparedStatement = this.connection.prepareStatement(sql + " WHERE users.id = ?")) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final UserGroup group = new UserGroup().setId(resultSet.getInt("gr_id"))
                        .setName(resultSet.getString("gr_name"))
                        .setDescription(resultSet.getString("gr_description"));
                final Job job = new Job().setId(resultSet.getInt("job_id"))
                        .setJobName(resultSet.getString("job_name"))
                        .setDescription(resultSet.getString("job_description"));
                final LocalDate created = getLocalDate(resultSet.getDate("created"));
                final LocalDate lastConnection = getLocalDate(resultSet.getDate("last_connection"));
                user.setId(resultSet.getInt("id"))
                        .setFirstName(resultSet.getString("firstname"))
                        .setLastName(resultSet.getString("lastname"))
                        .setUsername(resultSet.getString("username"))
                        .setPassword(resultSet.getString("password").toCharArray())
                        .setCreated(created)
                        .setLastConnection(lastConnection)
                        .setOnline(resultSet.getBoolean("online"))
                        .setGroup(group)
                        .setJob(job);
            }
        } catch (SQLException e) {
            this.debug(e);
        }finally {
            try{
                if (resultSet != null) resultSet.close();
            }catch (SQLException e0){
                e0.printStackTrace();
            }
        }
        return user;
    }

    @Override
    protected List<User> findAll_impl() {
        final List<User> users = new ArrayList<>();
        ResultSet resultSet = null;
        try(final Statement statement = this.connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT users.id, users.firstname, users.lastname, " +
                    "users.username, users.password, users.created, users.last_connection, online," +
                    "user_group.gr_id, user_group.gr_name, user_group.gr_description, job.job_id, job.job_name, " +
                    "job.job_description FROM users INNER JOIN user_group ON users.group_id = user_group.gr_id INNER JOIN job ON users.job_id = job.job_id");
            while (resultSet.next()) {
                final UserGroup group = new UserGroup().setId(resultSet.getInt("gr_id"))
                        .setName(resultSet.getString("gr_name"))
                        .setDescription(resultSet.getString("gr_description"));
                final Job job = new Job().setId(resultSet.getInt("job_id"))
                        .setJobName(resultSet.getString("job_name"))
                        .setDescription(resultSet.getString("job_description"));
                final LocalDate created = getLocalDate(resultSet.getDate("created"));
                final LocalDate lastConnection = getLocalDate(resultSet.getDate("last_connection"));
                users.add(
                        new User().setId(resultSet.getInt("id"))
                                .setFirstName(resultSet.getString("firstname"))
                                .setLastName(resultSet.getString("lastname"))
                                .setUsername(resultSet.getString("username"))
                                .setPassword(resultSet.getString("password").toCharArray())
                                .setCreated(created)
                                .setLastConnection(lastConnection)
                                .setOnline(resultSet.getBoolean("online"))
                                .setGroup(group)
                                .setJob(job)
                );
            }
        } catch (SQLException e) {
            this.debug(e);
        }finally {
            try{
                if (resultSet != null) resultSet.close();
            }catch (SQLException e0){
                e0.printStackTrace();
            }
        }
        return users;
    }

    public boolean check(final String login, final char[] password) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            this.checkConnection();
            preparedStatement = this.connection.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, Encrypt.md5(String.valueOf(password)));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            this.debug(e);
        } finally {
            close(resultSet, preparedStatement);
        }
        return false;
    }

    public User createUser(String username, char[] password) {
        final User user = new User();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            this.checkConnection();
            preparedStatement = this.connection.prepareStatement( sql + " WHERE users.username = ? AND users.password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, Encrypt.md5(new String(password)));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final LocalDate created = getLocalDate(resultSet.getDate("created"));
                final UserGroup group = new UserGroup().setId(resultSet.getInt("gr_id"))
                                                       .setName(resultSet.getString("gr_name"))
                                                       .setDescription(resultSet.getString("gr_description"));
                final Job job = new Job().setId(resultSet.getInt("job_id"))
                                         .setJobName(resultSet.getString("job_name"))
                                         .setDescription(resultSet.getString("job_description"));
                user.setId(resultSet.getInt("id"))
                        .setFirstName(resultSet.getString("firstname"))
                        .setLastName(resultSet.getString("lastname"))
                        .setCreated(created)
                        .setLastConnection(LocalDate.now())
                        .setUsername(username)
                        .setPassword(password)
                        .setGroup(group)
                        .setJob(job)
                        .setOnline(true);
            }
        } catch (SQLException e) {
            this.debug(e);
        } finally {
            close(resultSet, preparedStatement);
        }
        return user;
    }

    private void close(ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateState(final User user){
        PreparedStatement preparedStatement = null;
        try{
            checkConnection();
            preparedStatement = this.connection.prepareStatement("UPDATE users SET online = ? WHERE id = ?");
            preparedStatement.setBoolean(1, !user.isOnline());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            this.debug(e);
        }finally {
            try{
                if (preparedStatement != null) preparedStatement.close();
                closeConnection();
            }catch (SQLException e0){
                e0.printStackTrace();
            }
        }
        return false;
    }

    private LocalDate getLocalDate(final java.sql.Date d) throws SQLException {
        final Date date = new Date(d.getTime());
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
