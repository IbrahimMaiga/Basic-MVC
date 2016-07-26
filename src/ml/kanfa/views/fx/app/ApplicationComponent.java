package ml.kanfa.views.fx.app;

import javafx.stage.Stage;
import ml.kanfa.model.Rb;

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
}
