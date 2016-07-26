package ml.kanfa.service.authentication;

/**
 * @author Kanfa.
 */

public class AuthenticatorImpl implements AuthenticatorService{

    private String login;

    private char[] password;

    public AuthenticatorImpl(String login, char[] password){
        this.login = login;
        this.password = password;
    }

    @Override public boolean authenticate(String login, char[] password) {
        
        return false;
    }
}
