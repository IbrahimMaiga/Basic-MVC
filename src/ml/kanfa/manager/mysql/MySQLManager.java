package ml.kanfa.manager.mysql;

import javafx.scene.control.Alert;
import ml.kanfa.manager.AbstractManager;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.Properties;

/**
 * @author Ibrahim Maïga.
 */

public abstract class MySQLManager<T> extends AbstractManager<T> {

    private static String HOST;
    private static int PORT;
    private static final String PROPERTY_PATH = "/ml/kanfa/config/sconfig.properties";

    static {
        try {
            Properties properties = new Properties();
            properties.load(MySQLManager.class.getResourceAsStream(PROPERTY_PATH));
            HOST = (String)properties.get("host");
            PORT = Integer.parseInt(String.valueOf(properties.get("port")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean dev = true;

    private boolean connection_failed = false;

    private boolean canReload = false;

    public MySQLManager(AbstractConnection abstractConnection){
        super(abstractConnection);
    }

    protected void debug(SQLException e){
        boolean error = e.getErrorCode() == 0;
        if ((this.dev && this.connection_failed) || !error){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("");
            errorAlert.setHeaderText("");
            errorAlert.setContentText("");
            errorAlert.showAndWait();
        }
    }

    private void checkState(){
        try(Socket socket = new Socket(HOST, PORT)) {
            this.connection_failed = false;
        } catch (IOException e) {
            this.connection_failed = true;
            canReload = true;
            try { this.connection.close(); } catch (SQLException ex) {}
        }

        if (!this.connection_failed && this.canReload){
            this.connection = this.abstractConnection.getConnection(true);
        }
    }

    public boolean canLaunch(){
        return this.connection != null;
    }

    public boolean isFailed(){
        this.checkState();
        return this.connection_failed;
    }
}
