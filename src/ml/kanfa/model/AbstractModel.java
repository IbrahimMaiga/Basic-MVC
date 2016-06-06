package ml.kanfa.model;

import ml.kanfa.observer.Observable;
import ml.kanfa.observer.Observer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kanfa.
 */

public abstract class AbstractModel<T> implements Observable{

    private ArrayList<Observer> observers = new ArrayList<>();

    protected WeakReference<ArrayList<Observer>> reference = new WeakReference<>(this.observers);

    @Override public void addObserver(Observer observer) {
        if (this.reference != null && !this.reference.get().contains(observer)){
            this.reference.get().add(observer);
        }
    }

    @Override public void removeObserver(Observer observer) {
        if (!this.reference.get().contains(observer)) return;
        this.reference.get().remove(observer);
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

    public abstract void add(T object);
    public abstract void delete(T object);
    public abstract void update(T object);
    public abstract T find(int id);
    public abstract List<T> findAll();

}
