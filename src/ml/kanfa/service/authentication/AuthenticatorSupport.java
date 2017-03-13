package ml.kanfa.service.authentication;

import ml.kanfa.entity.User;

/**
 * @author Ibrahim Maïga.
 */
public interface AuthenticatorSupport {

    boolean checkAccess(final String login, final char[] password);

    User createUser(final String login, final char[] password);
}
