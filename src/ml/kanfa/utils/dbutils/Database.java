package ml.kanfa.utils.dbutils;

/**
 * @author Ibrahim Maïga.
 */

public class Database {

    private String name;

    public Database(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
