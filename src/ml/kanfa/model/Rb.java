package ml.kanfa.model;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Ibrahim Maïga.
 */
public class Rb {

    private Locale defaultLocal;
    private ResourceBundle rb;

    public Rb(){
        this.defaultLocal = Locale.FRENCH;
        this.rb  = ResourceBundle.getBundle("Application", defaultLocal);
    }

    public String get(String key){
       return this.rb.getString(key);
    }

    /**
     * Sets Local for resource bundle
     * @param local the target local
     */

    public void setLocale(Locale local){
        if (local != null && local != defaultLocal){
            defaultLocal = local;
            rb = ResourceBundle.getBundle("Application", local);
        }
        else{
            if (local == null) throw new NullPointerException(get("Rb.Locale.error_message"));
         }
    }
}
