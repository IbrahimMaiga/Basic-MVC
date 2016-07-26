package ml.kanfa.views.swing.app;

import ml.kanfa.model.Rb;

import javax.swing.*;

/**
 * @author Ibrahim Maïga.
 */
public abstract class ApplicationComponent extends JFrame{

    protected Rb rb;

    public ApplicationComponent(Rb rb){
        this.rb = rb;
    }

    public ApplicationComponent(){
        this(new Rb());
    }
}
