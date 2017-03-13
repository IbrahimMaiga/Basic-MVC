package ml.kanfa.model;

/**
 * @author Ibrahim Maïga.
 */
public interface DefaultModelFactory {
    ml.kanfa.model.UserModel getUserModel();
    JobModel getJobModel();
    UserGroupModel getUserGroupModel();
}
