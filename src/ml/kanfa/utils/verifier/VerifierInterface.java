package ml.kanfa.utils.verifier;

/**
 * @author Kanfa.
 */

public interface VerifierInterface {
    int id();
    default String name(){
        return this.getClass().getName().toLowerCase();
    }
}
