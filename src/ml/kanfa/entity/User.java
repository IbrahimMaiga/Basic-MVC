package ml.kanfa.entity;

/**
 * @author Kanfa.
 */

public class User {

    private int id;

    private String username;

    private String password;

    private boolean online;

    private UserGroup group;

    public User(){}


    // Getter and setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public UserGroup getGroup(){
        return this.group;
    }

    public void setGroup(UserGroup group){
        this.group = group;
    }
}
