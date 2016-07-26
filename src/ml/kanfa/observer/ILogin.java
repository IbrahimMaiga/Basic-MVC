package ml.kanfa.observer;

import ml.kanfa.entity.User;

/**
 * @author Kanfa.
 */

public interface ILogin extends Observer{
    void connect(User user);
    void disconnect(User user, boolean type);
}
