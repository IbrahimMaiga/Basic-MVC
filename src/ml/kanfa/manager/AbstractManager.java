package ml.kanfa.manager;

import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractManager<T> implements Pingable{

    //private static final Logger LOGGER = Logger.getLogger(AbstractManager.class.getName());
    //protected final QueryString queryString = QueryString.getInstance();

    protected AbstractConnection abstractConnection;
    protected Connection connection;

    public AbstractManager(AbstractConnection abstractConnection){
        this.abstractConnection = abstractConnection;
        this.connection = abstractConnection.getConnection();
    }

    protected abstract boolean delete_impl(T object);
    protected abstract boolean add_impl(T object);
    protected abstract boolean update_impl(T object);
    protected abstract T find_impl(int id);
    protected abstract List<T> findAll_impl();

    public boolean delete(T object){
        try {
            this.checkConnection();
            return delete_impl(object);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean add(T object){
        boolean result;
        try {
            this.checkConnection();
            result = add_impl(object);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean update(T object){
        try {
            this.checkConnection();
            return update_impl(object);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public T find(int id){
        try {
            this.checkConnection();
            return find_impl(id);
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<T> findAll(){
        try {
            this.checkConnection();
            return findAll_impl();
        } finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void checkConnection() {
        try{
            if (this.connection == null || this.connection.isClosed()){
                this.connection = this.abstractConnection.getConnection(true);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    protected void closeConnection() throws SQLException {
        if (this.connection != null){
            this.connection.close();
        }
    }

    @Override
    public boolean ping(){
        boolean bool = false;
        try {
            checkConnection();
            if (this.connection == null) return false;
            bool = this.connection.createStatement().execute("SELECT 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bool;
    }
}