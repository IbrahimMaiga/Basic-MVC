package ml.kanfa.config;

import ml.kanfa.parser.NoAuthenticationParserImpl;
import ml.kanfa.parser.WithAuthenticationParserImpl;
import ml.kanfa.utils.system.SystemUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */
public class Config {

    private static Map<Object, Object> objectMap;
    static {
        objectMap = new HashMap<>();
        objectMap.put("mysql", WithAuthenticationParserImpl.class);
        objectMap.put("sqlite", NoAuthenticationParserImpl.class);
    }

    private Config(){
        //Don't instantiate
    }

    /**
     * Returns the object
     * @param type
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object get(String type) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class cl = (Class)objectMap.get(type);
        Constructor[] constructors = cl.getConstructors();
        List<Class>  params = new ArrayList<>();
        if (constructors.length > 0){
            Constructor constructor = constructors[0];
            Parameter[] parameters = constructor.getParameters();
            for (Parameter parameter : parameters){
                params.add(parameter.getType());
            }
        }
        return SystemUtils.sharedInstance(cl, null, null);
    }
}
