package ml.kanfa.observer;

/**
 * @author Ibrahim Maïga.
 */
public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Object o);
    void notifyObservers();
}
