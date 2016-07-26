package ml.kanfa.manager;

import ml.kanfa.annot.ConnectionManager;
import ml.kanfa.manager.mysql.MySQLManagerFactory;
import ml.kanfa.manager.sqlite.SQLiteManagerFactory;
import ml.kanfa.parser.ParserImpl1;
import ml.kanfa.parser.ParserImpl2;
import ml.kanfa.parser.XMLParser;
import ml.kanfa.parser.YMLParser;
import ml.kanfa.utils.KFUtils;
import ml.kanfa.utils.dbutils.Database;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;
import ml.kanfa.utils.system.SystemUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ibrahim Maïga.
 */

public abstract class AbstractManagerFactory {

    public static final int MYSQL_MANAGER_FACTORY = 1;
    public static final int SQLITE_MANAGER_FACTORY = 2;
    private static final String DEFAULT_PARAM = "default";
    private AbstractConnection param_instance = null;

    protected AbstractManagerFactory(){}

    public AbstractManager getManager(final String class_name) {
        final String format_class_name = class_name.split("_").length >= 2 ? KFUtils._camlCase(class_name)
                                                                            : KFUtils.ucfirst(class_name);
        final String name = getEMPath() + "." + format_class_name + getSuffix();
        AbstractManager abstractManager = null;
        final Class param_class;

        try {
            final Class c = Class.forName(name);
            final Class[] params = new Class[]{AbstractConnection.class};
            final Constructor constructor = c.getConstructor(params);
            final Annotation[] annotations = c.getAnnotationsByType(ConnectionManager.class);
            String param = DEFAULT_PARAM;
            if (annotations.length == 0){
                param_class = Class.forName((new YMLParser()).get(getConnectionType()));
            }
            else{
                param_class = ((ConnectionManager)annotations[0]).value();
                param = ((ConnectionManager)annotations[0]).param();
            }

            final XMLParser parser;

            if (getConnectionType().equals("sqlite"))
                parser = new ParserImpl2(new Database(param));
            else
                parser = new ParserImpl1(new Database(param));

            Map<Class, Object> connectionMap = new HashMap<>();
            connectionMap.put(XMLParser.class, parser);
            param_instance = (AbstractConnection) SystemUtils.sharedInstance(param_class, "setParser", connectionMap);
            abstractManager = (AbstractManager) constructor.newInstance(param_instance);
        }
        catch (Exception e) {e.printStackTrace();}

        return abstractManager;
    }

    public static AbstractManagerFactory getManagerFactory(int type){
        switch(type){
            case MYSQL_MANAGER_FACTORY:
                return new MySQLManagerFactory();
            case SQLITE_MANAGER_FACTORY:
                return new SQLiteManagerFactory();
            default:
                return null;
        }
    }

    protected abstract String getEMPath();

    protected abstract String getSuffix();

    protected abstract String getConnectionType();

}
