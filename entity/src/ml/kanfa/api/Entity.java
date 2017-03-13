package ml.kanfa.api;

/**
 * @author Ibrahim Maïga.
 */
public abstract class Entity implements Initialisable{

    protected int id = 0;
    /**
     * Creates a new constructor with empty params
     */
    public Entity(){
        this.initialise();
    }

    public int getId(){
        return this.id;
    }
}
