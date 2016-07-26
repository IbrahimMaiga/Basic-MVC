package ml.kanfa.observer;

/**
 * @author Ibrahim Maïga.
 */

public  abstract class ObserverAdapter implements Observer{

    @Override public void update(Object o) {}

    @Override public void update() {}
}
