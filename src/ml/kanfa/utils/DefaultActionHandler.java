package ml.kanfa.utils;

import java.util.Objects;

/**
 * @author Ibrahim Maïga.
 */
public abstract class DefaultActionHandler implements ActionHandler{

    private final Publisher publisher = Publisher.instance();
    private ActionProperty actionProperty;

    public DefaultActionHandler(){
        register();
    }

    @Override
    public void otherHandle(Class clazz, ActionProperty property) {
        Objects.requireNonNull(clazz);
        ActionHandler actionHandler = publisher.get(clazz.getName());
        if (actionHandler == null){
            try {
                actionHandler = (ActionHandler)clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        actionHandler.handle(property);
    }

    public ActionProperty getProperty(){
        return this.actionProperty;
    }

    public void setProperty(ActionProperty newProperty){
        Objects.requireNonNull(newProperty, "Not accept a null value");
        this.actionProperty = newProperty;
    }

    private void register(){
        Publisher.instance().register(this);
    }
}
