package observer;

import entity.User;

/**
 * @author Ibrahim Maïga.
 */

public abstract class LoginInterfaceAdapter extends ObserverAdapter implements LoginInterface, ErrorInterface{

    @Override public void connect(User user) {}

    @Override public void deconnect(User user, boolean type) {}

    @Override public void showError(String message) {}
}
