package ml.kanfa.utils.system;

import javafx.application.Platform;
import ml.kanfa.parser.XMLParser;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */
public class SystemUtils {

    private static final int STATUS = 0;
    private static Map<String, Object> objects = new HashMap<>();
    private static Map<String, AbstractConnection> connections = new IdentityHashMap<>();

    private SystemUtils(){}

    public static void exit(){
        if (SwingUtilities.isEventDispatchThread())
            System.exit(STATUS);
        else
            Platform.exit();
    }

    public static Object sharedInstance(final Class c, final String methodName, final Map<Class, Object> args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        final String key = c.getName();
        if (!objects.containsKey(key)){
            final Object object = c.newInstance();
            final Class[] paramsType = new Class[args.size()];
            final Object[] values = new Object[args.size()];

            if (!args.isEmpty()){
                int i = 0;
                for (Class cl : args.keySet()){
                    paramsType[i] = cl;
                    values[i] = args.get(cl);
                    i++;
                }
            }

            if (methodName != null && !methodName.isEmpty()) {
                final Method method = c.getMethod(methodName, paramsType);
                method.invoke(object, values);
            }

            objects.put(key, object);
        }

        return objects.get(key);
    }

    public static AbstractConnection sharedConnection(final Class<AbstractConnection> connectionClass, final XMLParser parser){
        AbstractConnection abstractConnection = null;
        if (!connections.containsKey(connectionClass.getName())){
            try {
                abstractConnection = connectionClass.newInstance();
                abstractConnection.setParser(parser);
                storeConnection(abstractConnection);
            } catch (Exception e) {
                System.out.println("Dans la methode sharedConnection");
            }
        }
        return abstractConnection;
    }

    private static void storeConnection(AbstractConnection connection){
        connections.put(connection.getClass().getName(), connection);
    }
}
