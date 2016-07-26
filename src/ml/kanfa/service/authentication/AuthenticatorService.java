package ml.kanfa.service.authentication;

/**
 * @author Kanfa.
 */
public interface AuthenticatorService {


    boolean authenticate(String login, char[] password);
}
