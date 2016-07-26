package ml.kanfa.main.launcher.swing;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import ml.kanfa.views.swing.user.LoginFrame;

import javax.swing.*;

/**
 * @author Ibrahim Maïga.
 */

public class SwingApplication {

    public static void main(String[] args){
        System.setProperty("encoding", "utf8");
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
