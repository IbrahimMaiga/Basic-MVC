package ml.kanfa.observer;

import ml.kanfa.entity.User;

/**
 * @author Kanfa.
 */

public abstract class LoginStub extends ObserverAdapter implements ILogin, IError {

    @Override public void connect(User user) {}

    @Override public void disconnect(User user, boolean type) {}

    @Override public void showError(String message) {}
}
