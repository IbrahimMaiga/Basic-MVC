package ml.kanfa.utils.system;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */
public class SystemUtils {

    private static final int STATUS = 0;
    private static Map<String, Object> objects = new HashMap<>();

    private SystemUtils(){}

    public static void exit(){
        System.exit(STATUS);
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

            final Method method = c.getMethod(methodName, paramsType);
            method.invoke(object, values);
            objects.put(key, object);
        }

        return objects.get(key);
    }
}
