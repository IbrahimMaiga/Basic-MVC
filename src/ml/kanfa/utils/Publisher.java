package ml.kanfa.utils;

import ml.kanfa.utils.facade.MessageBox;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */
public class Publisher {

    private static final Map<String, ActionHandler> handlers = new HashMap<>();
    private static final Map<String, String> errors = new HashMap<>();
    private static final Publisher publisher = new Publisher();
    public static final String DATABASE_ERROR = "DATABASE_ERROR";
    public static final String CONNECTION_ERROR = "DATABASE_ERROR";

    /*static {
        errors.put(DATABASE_ERROR, Rb.getInstance().get("Publisher.DatabaseError"));
        errors.put(CONNECTION_ERROR, Rb.getInstance().get("Publisher.ConnectionError"));
    }*/

    // Only static methods
    private Publisher() {
    }

    public void publish(String key){
        MessageBox.showDialogBox(errors.get(key));
    }


    public boolean contains(String key){
        return handlers.containsKey(key);
    }
    public static Publisher instance(){
        return publisher;
    }

    public ActionHandler get(final String key){
        return handlers.get(key);
    }

    public Map<String, String> getErrors(){
        return errors;
    }

    public void error(String type, String message) {
        if (!errors.containsKey(type)){
            errors.put(type, message);
        }
    }

    public void register(ActionHandler actionHandler) {
        handlers.putIfAbsent(actionHandler.getClass().getName(), actionHandler);
    }
}
