package ml.kanfa.utils.dbutils.connection;

import ml.kanfa.parser.XMLParser;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractConnection {

    private Connection instance;
    private boolean error;
    protected boolean fistConnection;
    protected XMLParser parser;

    public AbstractConnection(){
        try {
            this.loadLibrary();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(boolean reload){
        if (reload){
            initParams();
        }
        if ((this.instance == null && !this.error)) {
            try {
                this.instance = this.getDriverManagerConnection();
                this.fistConnection = true;
            } catch (SQLException e) {
                this.error = true;
                displayError();
            }
        }
        return this.instance;
    }

    public Connection getConnection(){
        return this.getConnection(false);
    }

    private void initParams() {
        instance = null;
        error = false;
    }

    public void setParser(XMLParser parser){
        this.parser = parser;
    }

    protected abstract void displayError();
    protected abstract void loadLibrary() throws ClassNotFoundException;
    protected abstract Connection getDriverManagerConnection() throws SQLException;
}
