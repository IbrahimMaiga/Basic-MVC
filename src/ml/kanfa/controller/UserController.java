package ml.kanfa.controller;

import ml.kanfa.model.DisconnectionType;
import ml.kanfa.model.Rb;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.Observer;
import ml.kanfa.model.DIC;
import ml.kanfa.model.ContinuationAction;
import ml.kanfa.observer.IMessage;
import ml.kanfa.utils.Session;

/**
 * @author Ibrahim Maïga.
 */
public class UserController extends AbstractController {

    private final UserModel userModel;

    public UserController(final Rb rb) {
        super(rb);
        this.userModel = this.dic.getModelFactory().getUserModel();
    }

    public void checkAuthentication(Observer observer, final String login, final char[] password) {
        DIC.getInstance().service(login, password).setAction(new ContinuationAction() {
            @Override
            public void ifAction() {
                userModel.notifyConnection(observer, Session.currentUser());
            }
            @Override
            public void elseAction() {
                ((IMessage) observer).show(rb.get("UserController.login_error_message"));
            }
        });
    }

    public void closeSession(Observer observer, DisconnectionType type) {
        this.userModel.notifyDisconnection(observer, (type == DisconnectionType.DISCONNECT));
    }
}