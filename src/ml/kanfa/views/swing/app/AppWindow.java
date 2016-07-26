package ml.kanfa.views.swing.app;

import ml.kanfa.controller.UserController;
import ml.kanfa.entity.User;
import ml.kanfa.model.DisconnectionType;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.LoginStub;
import ml.kanfa.utils.verifier.Verification;
import ml.kanfa.views.swing.user.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Ibrahim Maïga.
 */

public class AppWindow extends ApplicationComponent{

    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private User user;
    private UserModel userModel;
    private UserController userController;
    private DisconnectAction disconnectAction;

    public AppWindow(User user, UserModel userModel){
        super();
        this.user = user;
        this.userModel = userModel;
        this.userModel.addObserver((this.disconnectAction = new DisconnectAction()));
        this.userController = new UserController(this.userModel, this.rb);
        this.setTitle(this.rb.get("App.Window_Title"));
        this.setSize(dim.width, dim.height - 45);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.add(new JLabel(this.rb.get("App.test") + " " + this.user.getUsername(), JLabel.CENTER), BorderLayout.CENTER);
        this.initMenu();
        this.initComponents();
        this.setVisible(true);
    }

    private void initComponents(){

    }

    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file =  new JMenu(this.rb.get("App.Menu.file"));
        JMenuItem disconnect = new JMenuItem(this.rb.get("App.MenuItem.disconnect"));
        JMenuItem quit = new JMenuItem(this.rb.get("App.MenuItem.quit"));
        file.addSeparator();
        file.add(disconnect);
        file.addSeparator();
        file.add(quit);
        disconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
        file.setMnemonic('F');
        menuBar.add(file);

        disconnect.addActionListener(
                e -> this.userController.control(this.disconnectAction, DisconnectionType.DISCONNECT)
        );

        quit.addActionListener(
                e -> this.userController.control(this.disconnectAction, DisconnectionType.QUIT)
        );

        this.setJMenuBar(menuBar);
    }

    private final class DisconnectAction extends LoginStub{

        @Override public void disconnect(User currentUser, boolean type) {
            user = currentUser;
            Verification<Integer> verification = new Verification(rb, type, JOptionPane.YES_OPTION) {
                @Override public void executeAction(boolean type) {
                    if (!userModel.isFailed()) {
                        user.setOnline(false);
                        userModel.update(user);
                        if (type) {
                            AppWindow.this.dispose();
                            SwingUtilities.invokeLater
                                    (
                                            () -> new LoginFrame(user.getUsername())
                                    );
                        } else {
                            System.exit(0);
                        }
                    }
                }
            };
            verification.run();
        }

        @Override public void showError(String message) {
            super.showError(message);
        }
    }
}
