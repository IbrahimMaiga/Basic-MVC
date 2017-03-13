package ml.kanfa.manager;

import ml.kanfa.annot.ConnectionManager;
import ml.kanfa.manager.mysql.MySQLManagerFactory;
import ml.kanfa.manager.sqlite.SQLiteManagerFactory;
import ml.kanfa.parser.NoAuthenticationParserImpl;
import ml.kanfa.parser.WithAuthenticationParserImpl;
import ml.kanfa.parser.XMLParser;
import ml.kanfa.parser.YMLParser;
import ml.kanfa.utils.KFUtils;
import ml.kanfa.utils.dbutils.Database;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;
import ml.kanfa.utils.system.SystemUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractManagerFactory {

    public enum ManagerType{
        MYSQL, SQLITE
    }

    private static final String DEFAULT_PARAM = "default";

    //Logger
    private static final Logger logger = Logger.getLogger(AbstractManagerFactory.class.getName());

    protected AbstractManagerFactory(){}

    public AbstractManager getManager(final String className) {
        final String name = getClassName(format(className));
        AbstractManager abstractManager = null;
        final Class paramClass;

        try {
            final Class managerClass = Class.forName(name);
            final Class[] params = new Class[]{AbstractConnection.class};
            final Constructor<AbstractManager> constructor = managerClass.getConstructor(params);
            final Annotation[] annotations = managerClass.getAnnotationsByType(ConnectionManager.class);
            String param = DEFAULT_PARAM;
            if (annotations.length == 0){
                paramClass = Class.forName((new YMLParser()).get(getConnectionType()));
            }
            else{
                paramClass = ((ConnectionManager)annotations[0]).value();
                param = ((ConnectionManager)annotations[0]).param();
            }

            final AbstractConnection connection = SystemUtils.sharedConnection(paramClass, getParser(param));
            abstractManager = constructor.newInstance(connection);
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return abstractManager;
    }

    private String getClassName(String formatClassName) {
        return getEMPath() + "." + formatClassName + getSuffix();
    }

    private XMLParser getParser(String param) {
        return provideAuthentication() ? new WithAuthenticationParserImpl(new Database(param))
                                       : new NoAuthenticationParserImpl(new Database(param));
    }

    private String format(String className) {
        return className.split("_").length >= 2 ?
                KFUtils.camlCase(className) : KFUtils.ucFirst(className);
    }

    public static AbstractManagerFactory getManagerFactory(ManagerType type){
        switch(type){
            case MYSQL:
                return new MySQLManagerFactory();
            case SQLITE:
                return new SQLiteManagerFactory();
            default:
                return null;
        }
    }

    protected abstract String getEMPath();

    protected abstract String getSuffix();

    protected abstract String getConnectionType();

    protected abstract boolean provideAuthentication();
}
