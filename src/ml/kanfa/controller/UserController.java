package ml.kanfa.controller;

import ml.kanfa.entity.User;
import ml.kanfa.model.AbstractModel;
import ml.kanfa.model.DeconnectionType;
import ml.kanfa.model.Rb;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.ILogin;
import ml.kanfa.views.user.LoginPane;

/**
 * @author Kanfa.
 */

public class UserController extends AbstractController{

    private UserModel userModel;
    private static User currentUser = new User();

    public UserController(AbstractModel abstractModel, Rb rb) {
        super(abstractModel, rb);
        this.userModel = (UserModel)abstractModel;
    }


    public void control(LoginPane.ConnectAction observer, User user) {
        if (this.userModel.is_correct(user)){
            user = this.userModel.createUser(user.getUsername(), user.getPassword());
            this.currentUser = user;
            user.setOnline(true);
            this.userModel.update(user);
            this.userModel.notifyConnection(observer, user);
        }
        else{
            observer.showError(this.rb.get("UserController.login_error_message"));
        }
    }

    public void control(ILogin ILogin, DeconnectionType type){
        switch (type){
            case DECONNECT:
                this.userModel.notifyDeconnection(ILogin, this.currentUser, true);
                break;
            case QUIT:
                this.userModel.notifyDeconnection(ILogin, this.currentUser, false);
                break;
            default:
        }
    }
}
