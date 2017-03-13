package ml.kanfa.utils;

import ml.kanfa.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */
public class Session {

    private static Map<String, Object> sessionsArray = new HashMap<>();
    public static final String CURRENT_USER = "CURRENT_USER";

    public static Object get(String key){
        if (sessionsArray.containsKey(key))
            return sessionsArray.get(key);
        return new Object();
    }

    public static void set(String key, Object value){
        sessionsArray.putIfAbsent(key, value);
    }

    private static void remove(String key){
        if (sessionsArray.containsKey(key))
            sessionsArray.remove(key);
    }

    public static void removeCurrentUser(){
        remove(CURRENT_USER);
    }

    public static void setCurrentUser(final User user){
        removeCurrentUser();
        set(CURRENT_USER, user);
    }
    public static User currentUser(){
        final Object o = get(CURRENT_USER);
        return  o instanceof User ? (User)o : null;
    }
}
