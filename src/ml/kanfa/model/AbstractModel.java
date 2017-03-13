package ml.kanfa.model;

import ml.kanfa.manager.AbstractManager;
import ml.kanfa.observer.Observable;
import ml.kanfa.observer.Observer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ibrahim Maïga.
 */
public abstract class AbstractModel<T> implements Observable{

    private final ArrayList<Observer> observers = new ArrayList<>();
    private AbstractManager<T> abstractManager;
    protected WeakReference<ArrayList<Observer>> reference = new WeakReference<>(this.observers);

    @Override public void addObserver(Observer observer) {
        Objects.requireNonNull(observer);
        if (this.reference != null){
            if (!this.reference.get().contains(observer)){
                this.reference.get().add(observer);
            }
        }
    }

    @Override public void removeObserver(Observer observer){
        if (this.reference != null){
            if (this.reference.get().contains(observer)){
                this.reference.get().remove(observer);
            }
        }
    }

    @Override public void notifyObservers(){
        if (this.reference != null){
            this.fireNotify();
        }
    }

    @Override public void notifyObservers(Object o){
        if (this.reference != null){
            this.fireNotify(o);
        }
    }

    private void fireNotify(){
        for (Observer observer : this.reference.get()){
            observer.update();
        }
    }

    private void fireNotify(Object o){
        for (Observer observer : this.reference.get()){
            observer.update(o);
        }
    }

    public abstract boolean add(T object);
    public abstract boolean delete(T object);
    public abstract boolean update(T object);
    public abstract T find(int id);
    public abstract List<T> findAll();
}
