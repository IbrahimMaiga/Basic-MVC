package ml.kanfa.utils.verifier;

import javafx.stage.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kanfa.
 */

public class WindowOpenedVerifier {

    private static List<Window> openedFrames = new ArrayList<>();
    private static Map<Object, Window>    windowMap    = new HashMap<>();
    private static Map<Object, Object>    referenced   = new HashMap<>();

    private WindowOpenedVerifier(){}

    public static List<Window> getOpenedFrames() {
        return openedFrames;
    }

    public static int openedCount(){
        return openedFrames.size();
    }

    public static void addOpened(Window window){
        if (window instanceof VerifierInterface){
            int d =((VerifierInterface)window).id();
            String name = ((VerifierInterface)window).name();
            try {
                if (referenced.containsKey(d) && !referenced.get(d).equals(name)){
                    throw new Exception("Cette clé existe déja pour la vue " + windowMap.get(d));
                }
                else {
                    referenced.put(d, name);
                }
            } catch (Exception e) {e.printStackTrace();}
            if (referenced.containsKey(d) && !windowMap.containsKey(d)){
                openedFrames.add(window);
                windowMap.put(d, window);
            }
        }
    }

    public static void dispose(Window window){
        if (window instanceof VerifierInterface) {
            int d = ((VerifierInterface) window).id();
            openedFrames.remove(windowMap.get(d));
            windowMap.remove(d);
            referenced.remove(d);
            window.hide();
        }
    }


    public static void disposeAll(){
        openedFrames.stream().forEach(Window::hide);
        openedFrames.clear();
        windowMap.clear();
        referenced.clear();
    }

    public static boolean hasElements(){
        return !openedFrames.isEmpty();
    }
}
