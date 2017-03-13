package ml.kanfa.manager.mysql.em;

import ml.kanfa.annot.ConnectionManager;
import ml.kanfa.entity.Job;
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
public class JobManager extends MySQLManager<Job> {

    public JobManager(AbstractConnection abstractConnection) {
        super(abstractConnection);
    }

    @Override
    protected boolean delete_impl(Job object) {
        return false;
    }

    @Override
    protected boolean add_impl(Job object) {
        return false;
    }

    @Override
    protected boolean update_impl(Job object) {
        return false;
    }

    @Override
    protected Job find_impl(int id) {
        final Job job = new Job();
        ResultSet resultSet = null;
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT job_name, job_description FROM job WHERE job_id = ?")) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                job.setId(id)
                        .setJobName(resultSet.getString("job_name"))
                        .setDescription(resultSet.getString("job_description"));
            }
        } catch (SQLException e) {
            this.debug(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return job;
    }

    @Override
    protected List<Job> findAll_impl() {
        final List<Job> jobs = new ArrayList<>();
        ResultSet resultSet = null;
        try (final Statement statement = this.connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM job");
            while (resultSet.next()) {
                jobs.add(
                        new Job().setId(resultSet.getInt("job_id"))
                                .setJobName(resultSet.getString("job_name"))
                                .setDescription(resultSet.getString("job_description"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return jobs;
    }
}