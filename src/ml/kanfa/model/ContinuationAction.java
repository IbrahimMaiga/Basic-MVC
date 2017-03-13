package ml.kanfa.model;

/**
 * @author Ibrahim Maïga.
 */
public interface ContinuationAction {

    void ifAction();

    void elseAction();

    default void doAction(final boolean value){
        if (value){
            ifAction();
        }else {
            elseAction();
        }
    }
}
