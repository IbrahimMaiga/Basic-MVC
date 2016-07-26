package ml.kanfa.controller;

import ml.kanfa.entity.User;
import ml.kanfa.model.AbstractModel;
import ml.kanfa.model.DisconnectionType;
import ml.kanfa.model.Rb;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.IError;
import ml.kanfa.observer.Observer;

/**
 * @author Ibrahim Maïga.
 */

public class UserController extends AbstractController{

    private UserModel userModel;
    private static User currentUser = new User();

    public UserController(AbstractModel abstractModel, Rb rb) {
        super(abstractModel, rb);
        this.userModel = (UserModel)abstractModel;
    }


    public void control(Observer observer, User user) {
        if (this.userModel.is_correct(user)){
            user = this.userModel.createUser(user.getUsername(), user.getPassword());
            user.setOnline(true);
            this.currentUser = user;
            this.userModel.update(user);
            this.userModel.notifyConnection(observer, user);
        }
        else{
            ((IError)observer).showError(this.rb.get("UserController.login_error_message"));
        }
    }

    public void control(Observer observer, DisconnectionType type){
        switch (type){
            case DISCONNECT:
                this.userModel.notifyDisconnection(observer, this.currentUser, true);
                break;
            case QUIT:
                this.userModel.notifyDisconnection(observer, this.currentUser, false);
                break;
            default:
        }
    }
}
