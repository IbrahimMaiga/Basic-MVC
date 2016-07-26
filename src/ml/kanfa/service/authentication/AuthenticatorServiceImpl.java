package ml.kanfa.service.authentication;

/**
 * @author Ibrahim Maïga.
 */

public class AuthenticatorServiceImpl implements AuthenticatorService{

    private String login;

    private char[] password;

    public AuthenticatorServiceImpl(String login, char[] password){
        this.login = login;
        this.password = password;
    }

    @Override public boolean authenticate(String login, char[] password) {
        
        return false;
    }
}
