package ml.kanfa.service.authentication;

/**
 * @author Ibrahim Maïga.
 */
public interface AuthenticatorService {

    boolean authenticate(final String login, final char[] password);
}
