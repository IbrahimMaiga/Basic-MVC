package ml.kanfa.service.authentication;

import ml.kanfa.entity.User;
import ml.kanfa.utils.Session;

/**
 * @author Ibrahim Maïga.
 */
public class AuthenticatorServiceImpl implements ml.kanfa.service.authentication.AuthenticatorService {

    private AuthenticatorSupport support;

    public AuthenticatorServiceImpl(final AuthenticatorSupport support){
        this.support = support;
    }

    @Override public boolean authenticate(final String login, final char[] password) {
        final boolean bool = this.support.checkAccess(login, password);
        if (bool){
            final User currentUser = this.support.createUser(login, password);
            Session.set(Session.CURRENT_USER, currentUser);
        }
        return bool;
    }
}
