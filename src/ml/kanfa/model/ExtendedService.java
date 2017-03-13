package ml.kanfa.model;

import javafx.concurrent.Service;
import javafx.stage.Stage;
import ml.kanfa.utils.Publisher;

/**
 * @author Ibrahim Maïga.
 */
public class ExtendedService {

    private Service<Boolean> service;
    private Stage waitFrame;
    private ContinuationAction action;

    private ExtendedService(){
    }

    public static ExtendedService newInstance(Service<Boolean> service){
        final ExtendedService extendedService = new ExtendedService();
        extendedService.configure(service);
        extendedService.start();
        return extendedService;
    }

    private void failedAction(){
        closeWaitFrame();
        Publisher.instance().publish(Publisher.CONNECTION_ERROR);
    }

    private void succeededAction() {
        closeWaitFrame();
        final boolean value = this.service.getValue();
        if (action != null){
            action.doAction(value);
        }
    }

    private void closeWaitFrame() {
        if (waitFrame != null){
            waitFrame.close();
        }
    }

    private void runAction() {
        if (this.waitFrame != null){
            this.waitFrame.show();
        }
    }

    private void configure(Service<Boolean> service){
        if (service != null){
            this.service = service;
            this.service.stateProperty().addListener((observable, oldValue, newValue) -> {
                switch (newValue){
                    case RUNNING:
                        runAction();
                        break;
                    case SUCCEEDED:
                        succeededAction();
                        break;
                    case FAILED:
                        failedAction();
                        break;
                }
            });
        }
    }
    private void start(){
        if (this.service != null){
            this.service.start();
        }
    }

    public void setAction(final ContinuationAction newAction){
        this.action = newAction;
    }

    void setWait(Stage waitFrame) {
        this.waitFrame = waitFrame;
    }
}
