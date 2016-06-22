package ml.kanfa.manager.mysql;

import javafx.scene.control.Alert;
import ml.kanfa.manager.AbstractManager;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.io.IOException;
import java.net.Socket;
import java.sql.*;

/**
 * @author Kanfa.
 */

public abstract class MySQLManager<T> extends AbstractManager<T> {

    protected boolean dev = true;

    private boolean connection_failed = false;

    private boolean canReload = false;

    private static final String HOST = "127.0.0.1" ;
    private static final int PORT = 80;

    protected PreparedStatement preparedStatement;
    protected Statement statement;
    protected ResultSet resultSet;
    private AbstractConnection abstractConnection;

    public MySQLManager(AbstractConnection abstractConnection){
        this.abstractConnection = abstractConnection;
        this.connection = this.abstractConnection.getConnection();
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
