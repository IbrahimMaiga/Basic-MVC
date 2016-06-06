package utils.verifier;

import javafx.stage.Window;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */

public class IdGen {


    public static int currentId = 0;
    private static Map<String, Integer> ids = new HashMap<>();

    private IdGen(){}

    public static int getId(Window window){
        String key = ((VerifierInterface)window).name();
        if (!ids.containsKey(key)){
            currentId++;
            ids.put(key, currentId);
        }
        return ids.get(key);
    }
}
