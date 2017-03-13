package ml.kanfa.model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.stage.Window;
import ml.kanfa.service.authentication.AuthenticatorService;
import ml.kanfa.service.authentication.AuthenticatorServiceImpl;
import ml.kanfa.utils.Publisher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */
public class DIC {

    private static DIC container = new DIC();
    private static DefaultModelFactoryImpl defaultModelFactory = new DefaultModelFactoryImpl();
    private Class cl;
    private final Map<String, Class> registries = new HashMap<>();
    private Window loginFrame;

    private DIC(){
        // Don't Instantiate
    }

    public static DIC getInstance(){
        return container;
    }

    public DefaultModelFactory getModelFactory(){
        if (this.cl != null){
            try {
                DefaultModelFactory defaultModelFactory = ((DefaultModelFactory)cl.newInstance());
                if (registries.containsKey(cl.getName())){
                    return defaultModelFactory;
                }
                registries.put(cl.getName(), cl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultModelFactory;
    }

    public AuthenticatorService getAuthenticator(){
        return new AuthenticatorServiceImpl(getModelFactory().getUserModel());
    }

    public void defineModelFactory(Class cl){
        this.cl = cl;
    }

    public Publisher publisher(){
        return Publisher.instance();
    }

    public void setLoginFrame(Stage loginFrame) {
        this.loginFrame = loginFrame;
    }

    private Window getFrame(){
        return this.loginFrame;
    }

    public ExtendedService service(final String login, final char[] password){
        final ExtendedService service = ExtendedService.newInstance(new Service<Boolean>() {
            @Override protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return getAuthenticator().authenticate(login, password);
                    }
                };
            }
        });
        service.setWait(null);
        return service;
    }

    private static class DefaultModelFactoryImpl implements DefaultModelFactory{
        @Override public UserModel getUserModel() {
            return UserModel.instance();
        }

        @Override public JobModel getJobModel() {
            return JobModel.instance();
        }

        @Override public UserGroupModel getUserGroupModel() {
            return UserGroupModel.instance();
        }
    }
}
