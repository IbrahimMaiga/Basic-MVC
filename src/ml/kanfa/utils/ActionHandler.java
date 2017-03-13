package ml.kanfa.utils;

/**
 * @author Ibrahim Maïga.
 */
public interface ActionHandler {

    ActionHandler handle(ActionProperty property);

    ActionProperty getProperty();

    void setProperty(final ActionProperty property);

    default void otherHandle(final Class clazz, ActionProperty property) {
        Publisher.instance().get(clazz.getName()).handle(property);
    }
}
