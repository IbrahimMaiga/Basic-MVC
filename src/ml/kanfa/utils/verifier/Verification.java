package ml.kanfa.utils.verifier;

import javafx.scene.control.ButtonType;
import ml.kanfa.model.Rb;
import ml.kanfa.utils.facade.MessageBox;

/**
 * @author Ibrahim Maïga.
 */
public abstract class Verification<T> {

    private T t;
    private Rb rb;
    private boolean type;
    private boolean can = true;
    private boolean btask = false;
    private boolean yes_option = false;

    public Verification(Rb rb, boolean type, T t){
        this.t = t;
        if (!isCorrectClass(t)){
            try {
                throw  new RuntimeException(rb.get("Verification.RE_Text"));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.rb = rb;
        this.type = type;
    }

    private boolean isCorrectClass(T t){
        return (t.getClass().equals(ButtonType.class)) || (t.getClass().equals(Integer.class));
    }

    private boolean isPassed(T t){
        return this.isCorrectClass(t)  && t.equals(this.t);
    }

    public void run(){
        String message = type ? rb.get("App.Alert.disconnect_confirm_message") :
                rb.get("App.Alert.disconnect_quit_message");
        if (WindowOpenedVerifier.hasElements()){
            String str = String.valueOf(WindowOpenedVerifier.openedCount()) + " ";
            str += (WindowOpenedVerifier.openedCount() == 1) ? rb.get("App.OneOtherFrame.warning_msg") :
                    rb.get("App.OtherFrame.warning_msg");

            T response1 = (T)MessageBox.showConfirmBox
                    (
                            str + " " + (type ? rb.get("App.disconnect_msg") : rb.get("App.quit_msg")),
                            type ? rb.get("App.ConfirmAlert1.disconnect_title") : rb.get("App.ConfirmAlert1.quit_title"),
                            type ? rb.get("App.ConfirmAlert1.disconnect_header") : rb.get("App.ConfirmAlert1.quit_header")
                    );
            if (this.isPassed(response1)){
                System.out.println("La premiere reponse : " + this.isPassed(response1));
                WindowOpenedVerifier.disposeAll();
                btask = true;
            }
            else{
                can = false;
            }
        }
        if (can){
            if (!btask){
                T response2 = (T)MessageBox.showConfirmBox
                        (
                                message,
                                type ? rb.get("App.ConfirmAlert2.disconnect_title") : rb.get("App.ConfirmAlert2.quit_title"),
                                type ? rb.get("App.ConfirmAlert2.disconnect_header") : rb.get("App.ConfirmAlert2.quit_header")
                        );

                if (this.isPassed(response2)){
                    yes_option = true;
                }
                else return;
            }
            if (yes_option || btask){
                executeAction(type);
            }
        }
    }

    public abstract void executeAction(boolean type);
}
