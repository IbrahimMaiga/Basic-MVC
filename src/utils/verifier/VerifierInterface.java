package utils.verifier;

/**
 * @author Ibrahim Maïga.
 */

public interface VerifierInterface {
    int id();
    default String name(){
        return this.getClass().getName().toLowerCase();
    }
}
