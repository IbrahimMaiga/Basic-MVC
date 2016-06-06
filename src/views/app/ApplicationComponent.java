package views.app;

import javafx.stage.Stage;
import model.Rb;

/**
 * @author Ibrahim Maïga.
 */
public class ApplicationComponent extends Stage{

    protected Rb rb;

    public ApplicationComponent(Rb rb){
        this.rb = rb;
    }

    public ApplicationComponent(){
        this(new Rb());
    }

    public Rb getRb(){
        return this.rb;
    }
}
