package ml.kanfa.entity;

import javafx.beans.property.SimpleStringProperty;
import ml.kanfa.api.Entity;

/**
 * @author Ibrahim Maïga.
 */
public class Job extends Entity{

    private int id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;

    public Job(){
        super();
    }
    @Override
    public void initialise() {
        this.id = 0;
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
    }

    public int getId(){
        return this.id;
    }

    public Job setId(int newId){
        if (newId > 0){
            this.id = newId;
        }
        return this;
    }

    public String getJobName(){
        return this.name.get();
    }

    public Job setJobName(String newValue){
        //Objects.requireNonNull(newValue);
        this.name.set(newValue);
        return this;
    }

    public String getDescription(){
        return this.description.get();
    }

    public Job setDescription(String newDescription){
        //Objects.requireNonNull(newDescription);
        this.description.set(newDescription);
        return this;
    }

    @Override
    public String toString() {
        return  "[Name : " + getJobName()  + ")] [Description : " + getDescription() + ")]";
    }
}
