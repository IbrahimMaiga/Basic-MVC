package ml.kanfa.manager.mysql;

import javafx.scene.control.Alert;
import ml.kanfa.manager.AbstractManager;
import ml.kanfa.utils.Publisher;
import ml.kanfa.utils.dbutils.connection.AbstractConnection;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
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
            final Properties properties = new Properties();
            properties.load(MySQLManager.class.getResourceAsStream(PROPERTY_PATH));
            HOST = (String)properties.get("host");
            PORT = Integer.parseInt(String.valueOf(properties.get("port")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean dev = true;
    private boolean connectionFailed = false;
    private boolean canReload = false;

    public MySQLManager(AbstractConnection abstractConnection){
        super(abstractConnection);
    }

    protected void debug(SQLException e){
        boolean error = e.getErrorCode() == 0;
        if ((this.dev && this.connectionFailed) || !error){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("");
            errorAlert.setHeaderText("");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
        Publisher.instance().error(Publisher.DATABASE_ERROR, e.getMessage());
    }

    private void checkState(){
        try(Socket ignored = new Socket(HOST, PORT)) {
            this.connectionFailed = false;
        } catch (IOException e) {
            this.connectionFailed = true;
            canReload = true;
            try { this.connection.close(); } catch (SQLException ex) {}
        }

        if (!this.connectionFailed && this.canReload){
            this.connection = this.abstractConnection.getConnection(true);
        }
    }

    public boolean canLaunch(){
        return this.connection != null;
    }

    public boolean isFailed(){
        this.connectionFailed = !ping();
        return this.connectionFailed;
    }
}
