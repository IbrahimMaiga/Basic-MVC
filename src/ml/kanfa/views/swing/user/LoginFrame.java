package ml.kanfa.views.swing.user;

import ml.kanfa.controller.UserController;
import ml.kanfa.entity.User;
import ml.kanfa.model.UserModel;
import ml.kanfa.observer.LoginStub;
import ml.kanfa.views.swing.app.AppWindow;
import ml.kanfa.views.swing.app.ApplicationComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Ibrahim Maïga.
 */
public class LoginFrame extends ApplicationComponent{

    private JPanel container;
    private JPanel content;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton connectBtn;
    private JCheckBox showPassword;
    private UserModel userModel;
    private UserController userController;
    private ConnectAction connectAction;
    private String username;

    public LoginFrame(String username){
        this.username = username;
        this.setTitle(this.rb.get("Login.title"));
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.initComponents();
        this.userModel = new UserModel();
        this.userModel.addObserver((this.connectAction = new ConnectAction()));
        this.userController = new UserController(this.userModel, this.rb);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public LoginFrame(){
        this("");
    }

    private void initComponents(){
        this.container = new JPanel(new BorderLayout());
        this.content = new JPanel(new GridBagLayout());
        this.loginField = new JTextField(20);
        this.passwordField = new JPasswordField(20);
        this.connectBtn = new JButton(this.rb.get("Login.connectBtn.text"));
        this.showPassword = new JCheckBox(this.rb.get("Login.showPass_text"));
        JLabel loginLabel = new JLabel(this.rb.get("Login.textfield.prompt_text"));
        JLabel passwordLabel = new JLabel(this.rb.get("Login.password_field.prompt_text"));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(0, 0, 3, 15);
        this.content.add(loginLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        this.content.add(this.loginField, c);
        c.gridx = 0;
        c.gridy = 1;
        this.content.add(passwordLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        this.content.add(this.passwordField, c);
        c.gridx = 1;
        c.gridy = 2;
        this.content.add(this.showPassword, c);
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.LINE_START;
        this.content.add(this.connectBtn, c);
        this.container.add(this.content, BorderLayout.CENTER);
        this.setContentPane(this.container);
        char echoChar = this.passwordField.getEchoChar();

        if (!this.username.isEmpty()) {
            this.loginField.setText(this.username);
            this.loginField.transferFocus();
        }

        this.showPassword.addItemListener(
                e -> this.check((e.getStateChange() == ItemEvent.SELECTED), echoChar)
        );

        this.showPassword.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    check(showPassword.isSelected(), echoChar);
                    showPassword.setSelected(!showPassword.isSelected());
                }
            }
        });

        this.connectBtn.addActionListener(e -> this.logIn());

        this.connectBtn.addKeyListener(new KeyAdapter() {
            @Override public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    logIn();
                }
            }
        });
    }

    private void logIn(){
        User user = new User();
        user.setUsername(loginField.getText());
        user.setPassword(new String(passwordField.getPassword()));
        this.userController.control(this.connectAction, user);
    }

    private void check(boolean isSelected, char echoChar){
        this.passwordField.setEchoChar(isSelected  ? 0 : echoChar);
    }

    private final class ConnectAction extends LoginStub {

        @Override public void connect(User user) {
            LoginFrame.this.dispose();
            SwingUtilities.invokeLater(() -> new AppWindow(user, userModel));
        }

        @Override public void showError(String message) {
            JOptionPane.showMessageDialog(null, message);
            connectBtn.transferFocus();
        }
    }
}
