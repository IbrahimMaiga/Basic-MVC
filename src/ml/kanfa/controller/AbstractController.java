package ml.kanfa.controller;

import ml.kanfa.model.AbstractModel;
import ml.kanfa.model.DIC;
import ml.kanfa.model.Rb;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractController {

    protected AbstractModel abstractModel;
    protected Rb rb;
    protected DIC dic = DIC.getInstance();

    public AbstractController(Rb rb){
        this.rb = rb;
    }

}
