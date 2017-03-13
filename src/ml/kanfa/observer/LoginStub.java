package ml.kanfa.observer;

import ml.kanfa.entity.User;

/**
 * @author Ibrahim Maïga.
 */
public abstract class LoginStub extends ObserverAdapter implements ml.kanfa.observer.ILogin, IMessage {

    @Override public void connect(User user) {}

    @Override public void disconnect(boolean type) {}

    @Override public void show(String message) {}
}
