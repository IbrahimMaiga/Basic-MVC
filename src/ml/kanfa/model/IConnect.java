package ml.kanfa.model;

import ml.kanfa.entity.User;

/**
 * @author Ibrahim Maïga.
 */
public interface IConnect {
    boolean updateState(final User user);
}
