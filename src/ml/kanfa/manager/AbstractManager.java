package ml.kanfa.manager;

import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractManager<T> {

    protected AbstractConnection abstractConnection;
    protected Connection connection;

    protected PreparedStatement preparedStatement;
    protected Statement statement;
    protected ResultSet resultSet;

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
        boolean result = false;
        try {
            this.checkConnection();
            result = delete_impl(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean add(T object){
        boolean result = false;
        try {
            this.checkConnection();
            result = add_impl(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean update(T object){
        boolean result = false;
        try {
            this.checkConnection();
            result = update_impl(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public T find(int id){
        T result = null;
        try {
            this.checkConnection();
            result = find_impl(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<T> findAll(){
       List<T> result = new ArrayList<>();
        try {
            this.checkConnection();
            result.addAll(findAll_impl());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                this.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    protected void checkConnection() throws SQLException {
        if (this.connection.isClosed())
            this.connection = this.abstractConnection.getConnection(true);
    }

    protected void closeConnection() throws SQLException {
        if (this.connection != null){
            this.connection.close();
        }
    }
}
