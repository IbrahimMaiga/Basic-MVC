package ml.kanfa.observer;

import ml.kanfa.entity.User;

/**
 * @author Kanfa.
 */

public interface ILogin extends Observer{
    void connect(User user);
    void deconnect(User user, boolean type);
}
