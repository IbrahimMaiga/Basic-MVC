package ml.kanfa.model;

import ml.kanfa.entity.Job;
import ml.kanfa.manager.AbstractManagerFactory;
import ml.kanfa.manager.mysql.em.JobManager;

import java.util.List;

/**
 * @author Ibrahim Maïga.
 */
public class JobModel extends AbstractModel<Job> {

    private static JobModel jobModel = new JobModel();
    public static JobModel instance() {
        return jobModel;
    }

    private static class LazyInitialization {
        private static JobManager jobManager = (JobManager)AbstractManagerFactory.getManagerFactory(AbstractManagerFactory.ManagerType.MYSQL).getManager("job");
    }
    @Override
    public boolean add(Job object) {
        return false;
    }

    @Override
    public boolean delete(Job object) {
        return LazyInitialization.jobManager.delete(object);
    }

    @Override
    public boolean update(Job object) {
        return LazyInitialization.jobManager.update(object);
    }

    @Override
    public Job find(int id) {
        return LazyInitialization.jobManager.find(id);
    }

    @Override
    public List<Job> findAll() {
        return LazyInitialization.jobManager.findAll();
    }
}
