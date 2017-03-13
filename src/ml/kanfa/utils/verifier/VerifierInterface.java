package ml.kanfa.utils.verifier;

/**
 * @author Ibrahim Maïga.
 */
public interface VerifierInterface {
    int id();
    void addOpened();
    void close();
    default String name(){
        return this.getClass().getName().toLowerCase();
    }
}
