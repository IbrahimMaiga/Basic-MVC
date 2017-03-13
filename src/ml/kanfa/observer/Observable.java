package ml.kanfa.observer;

/**
 * @author Ibrahim Maïga.
 */
public interface Observable {
    void addObserver(ml.kanfa.observer.Observer observer);
    void removeObserver(ml.kanfa.observer.Observer observer);
    void notifyObservers(Object o);
    void notifyObservers();
}
