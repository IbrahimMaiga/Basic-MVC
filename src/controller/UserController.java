package controller;

import entity.User;
import model.AbstractModel;
import model.DeconnectionType;
import model.Rb;
import model.UserModel;
import observer.LoginInterface;
import views.user.LoginPane;

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

    public void control(LoginInterface loginInterface, DeconnectionType type){
        switch (type){
            case DECONNECT:
                this.userModel.notifyDeconnection(loginInterface, this.currentUser, true);
                break;
            case QUIT:
                this.userModel.notifyDeconnection(loginInterface, this.currentUser, false);
                break;
            default:
        }
    }
}
