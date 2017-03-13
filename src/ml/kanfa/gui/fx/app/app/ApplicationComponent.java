package ml.kanfa.gui.fx.app.app;

import javafx.stage.Stage;
import ml.kanfa.model.DIC;
import ml.kanfa.model.Rb;

/**
 * @author Ibrahim Maïga.
 */
public class ApplicationComponent extends Stage{

    protected Rb rb;
    protected DIC dic = DIC.getInstance();
    public ApplicationComponent(Rb rb){
        this.rb = rb;
    }

    public ApplicationComponent(){
        this(Rb.getInstance());
    }

    public String getCallerClassName(String cls) {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        String callerClassName = null;
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(cls) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                if (callerClassName == null) {
                    callerClassName = ste.getClassName();
                } else if (!callerClassName.equals(ste.getClassName())) {
                    return ste.getClassName();
                }
            }
        }
        return "";
    }
}
