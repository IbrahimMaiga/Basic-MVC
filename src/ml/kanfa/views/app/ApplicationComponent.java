package ml.kanfa.views.app;

import javafx.stage.Stage;
import ml.kanfa.model.Rb;

/**
 * @author Kanfa.
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
