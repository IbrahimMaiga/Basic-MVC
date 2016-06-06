package ml.kanfa.observer;

import ml.kanfa.entity.User;

/**
 * @author Kanfa.
 */

public abstract class LoginInterfaceAdapter extends ObserverAdapter implements LoginInterface, ErrorInterface{

    @Override public void connect(User user) {}

    @Override public void deconnect(User user, boolean type) {}

    @Override public void showError(String message) {}
}
