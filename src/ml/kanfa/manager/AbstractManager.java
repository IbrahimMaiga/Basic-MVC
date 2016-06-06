package ml.kanfa.manager;

import java.sql.Connection;
import java.util.List;

/**
 * @author Kanfa.
 */
public abstract class AbstractManager<T> {
    protected Connection connection;
    public abstract void delete(T object);
    public abstract void add(T object);
    public abstract void update(T object);
    public abstract T find(int id);
    public abstract List<T> findAll();
}
