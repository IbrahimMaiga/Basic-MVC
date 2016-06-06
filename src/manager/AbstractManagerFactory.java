package manager;

import annot.ManagerConnection;
import manager.mysql.MySQLManagerFactory;
import parser.YMLParser;
import utils.KFUtils;
import utils.dbutils.connection.AbstractConnection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractManagerFactory {

    public static final int MYSQL_MANAGER_FACTORY = 1;

    public AbstractManager getManager(String class_name) {

        String format_class_name = class_name.split("_").length >= 2 ?
                                        KFUtils._camlCase(class_name) :
                                        KFUtils.ucfirst(class_name);
        String name = entityPath() + "." + format_class_name + suffix();
        AbstractManager abstractManager = null;
        AbstractConnection param_instance;
        Class param_class;

        try {
            Class c = Class.forName(name);
            Class[] params = new Class[]{AbstractConnection.class};
            Constructor constructor = c.getConstructor(params);
            Annotation[] annotations = c.getAnnotations();
            if (annotations == null){
                param_class = Class.forName((new YMLParser()).get(connectionType()));
            }
            else{
                param_class = ((ManagerConnection)annotations[0]).value();
            }
            param_instance = (AbstractConnection)param_class.newInstance();
            abstractManager = (AbstractManager) constructor.newInstance(param_instance);
        }
        catch (Exception e) {e.printStackTrace();}

        return abstractManager;
    }

    public static AbstractManagerFactory getManagerFactory(int type){
        switch(type){
            case MYSQL_MANAGER_FACTORY:
                return new MySQLManagerFactory();
            default:
                return null;
        }
    }

    protected abstract String entityPath();

    protected abstract String suffix();

    protected abstract String connectionType();

}
