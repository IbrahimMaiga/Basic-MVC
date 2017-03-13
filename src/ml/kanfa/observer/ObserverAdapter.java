package ml.kanfa.observer;

/**
 * @author Ibrahim Maïga.
 */
public  abstract class ObserverAdapter implements ml.kanfa.observer.Observer {

    @Override public void update(Object o) {}
    @Override public void update() {}
}
