package ml.kanfa.observer;

import ml.kanfa.entity.User;

/**
 * @author Ibrahim Maïga.
 */

public interface ILogin extends Observer{
    void connect(User user);
    void disconnect(User user, boolean type);
}
