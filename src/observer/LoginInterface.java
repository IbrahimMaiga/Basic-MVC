package observer;

import entity.User;

/**
 * @author Ibrahim Maïga.
 */

public interface LoginInterface extends Observer{
    void connect(User user);
    void deconnect(User user, boolean type);
}
