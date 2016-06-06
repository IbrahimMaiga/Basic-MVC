package controller;

import model.AbstractModel;
import model.Rb;

/**
 * @author Ibrahim Maïga.
 */

public abstract class AbstractController {

    protected AbstractModel abstractModel;
    protected Rb rb;

    public AbstractController(AbstractModel abstractModel, Rb rb){
        this.abstractModel = abstractModel;
        this.rb = rb;
    }
}
