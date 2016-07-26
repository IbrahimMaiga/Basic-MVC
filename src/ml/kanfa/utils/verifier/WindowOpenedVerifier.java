package ml.kanfa.utils.verifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kanfa.
 */

public class WindowOpenedVerifier {

    private static List<Object> openedFrames = new ArrayList<>();
    private static Map<Object, Object>    windowMap    = new HashMap<>();
    private static Map<Object, Object>    referenced   = new HashMap<>();

    private WindowOpenedVerifier(){}

    public static List<Object> getOpenedFrames() {
        return openedFrames;
    }

    public static int openedCount(){
        return openedFrames.size();
    }

    public static void addOpened(Object window){
        if ((window instanceof javafx.stage.Window) || (window instanceof java.awt.Window)) {
            if (window instanceof VerifierInterface) {
                int d = ((VerifierInterface) window).id();
                String name = ((VerifierInterface) window).name();
                try {
                    if (referenced.containsKey(d) && !referenced.get(d).equals(name)) {
                        throw new Exception("Cette clé existe déja pour la vue " + windowMap.get(d));
                    } else {
                        referenced.put(d, name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (referenced.containsKey(d) && !windowMap.containsKey(d)) {
                    openedFrames.add(window);
                    windowMap.put(d, window);
                }
            }
        }
    }

    public static void dispose(Object window){
        if (window instanceof VerifierInterface) {
            int d = ((VerifierInterface) window).id();
            openedFrames.remove(windowMap.get(d));
            windowMap.remove(d);
            referenced.remove(d);
            checkAndDelete(window);
        }
    }


    public static void disposeAll(){
        openedFrames.forEach(WindowOpenedVerifier::checkAndDelete);
        openedFrames.clear();
        windowMap.clear();
        referenced.clear();
    }

    private static void checkAndDelete(Object window){
        if (window instanceof javafx.stage.Window)
            ((javafx.stage.Window) window).hide();
        if (window instanceof java.awt.Window)
            ((java.awt.Window) window).dispose();
    }

    public static boolean hasElements(){
        return !openedFrames.isEmpty();
    }
}
