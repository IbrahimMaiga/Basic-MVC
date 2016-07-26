package ml.kanfa.service.authentication;

/**
 * @author Ibrahim Maïga.
 */
public interface AuthenticatorService {


    boolean authenticate(String login, char[] password);
}
