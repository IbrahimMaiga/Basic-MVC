package ml.kanfa.controller;

import ml.kanfa.model.AbstractModel;
import ml.kanfa.model.Rb;

/**
 * @author Kanfa.
 */

public abstract class AbstractController {

    protected AbstractModel abstractModel;
    protected Rb rb;

    public AbstractController(AbstractModel abstractModel, Rb rb){
        this.abstractModel = abstractModel;
        this.rb = rb;
    }
}
