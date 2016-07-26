package ml.kanfa.utils.facade;

import javax.swing.*;

/**
 * @author Kanfa.
 */
public class MessageBox {

    private MessageBox(){}

    public static void showDialogBox(String message){
        if (SwingUtilities.isEventDispatchThread())
            JOptionPane.showMessageDialog(null, message);
        else
            AlertBox.showInformationAlert(message, "Information", "Message");
    }

    public static Object showConfirmBox(String message, String title, String header){
        if (SwingUtilities.isEventDispatchThread())
            return JOptionPane.showConfirmDialog(null, message, title ,JOptionPane.YES_NO_OPTION);
        else
            return AlertBox.showConfirmAlert(message, title, header);
    }

    public static Object showConfirmBox(String message, String title){
        return showConfirmBox(message, title, "");
    }
}
