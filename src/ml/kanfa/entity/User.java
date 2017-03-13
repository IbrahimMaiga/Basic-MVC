package ml.kanfa.entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import ml.kanfa.api.Entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Ibrahim Maïga.
 */
public class User extends Entity {

    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty username;
    private char[] password;
    private LocalDate lastConnection;
    private LocalDate created;
    private SimpleBooleanProperty online;
    private UserGroup group;
    private Job job;

    public static final User NONE = new User();

    public User(){
       super();
    }

    @Override
    public void initialise() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.username = new SimpleStringProperty("");
        this.password = new char[0];
        this.online = new SimpleBooleanProperty(false);
        this.lastConnection = LocalDate.now();
        this.created = LocalDate.now();
        this.group = new UserGroup();
        this.job = new Job();
    }

    // Getter and setter

    public int getId() {
        return id;
    }

    /**
     * The id of the object is changed only when the new id is greater than zero,
     * if it is less than or equal to zero, the object with the old value
     * of the id is returned
     * @param newId the new id
     * @return user instance
     */
    public User setId(int newId) {
        if (newId > 0){
            this.id = newId;
        }
        return this;
    }

    public String getFirstName(){
        return this.firstName.get();
    }

    public User setFirstName(String newName){
        Objects.requireNonNull(newName, "First Name must not be null");
        this.firstName.set(newName);
        return this;
    }

    public String getLastName(){
        return this.lastName.get();
    }

    public User setLastName(String newName){
        Objects.requireNonNull(newName, "Last Name must not be null");
        this.lastName.set(newName);
        return this;
    }

    public String getUsername() {
        return this.username.get();
    }

    /**
     * Returns the instance with the new value of username
     * @param newUsername new value of username
     * @return user instance
     */
    public User setUsername(String newUsername) {
        Objects.requireNonNull(newUsername, "Username must not be null");
        this.username.set(newUsername);
        return this;
    }

    public char[] getPassword() {
        return this.password;
    }

    public User setPassword(char[] newPassword) {
        Objects.requireNonNull(newPassword, "Password must not be null");
        this.password = newPassword;
        return this;
    }

    public User passwordFrom(String password) {
        this.password = password.toCharArray();
        return this;
    }

    public LocalDate getCreated(){
        return this.created;
    }

    public User setCreated(final LocalDate newDate){
        Objects.requireNonNull(newDate);
        this.created = newDate;
        return this;
    }

    public LocalDate getLastConnection(){
        return this.lastConnection;
    }

    public User setLastConnection(final LocalDate newDate){
        Objects.requireNonNull(newDate);
        this.lastConnection = newDate;
        return this;
    }

    public boolean isOnline() {
        return online.get();
    }

    public User setOnline(boolean online) {
        this.online.set(online);
        return this;
    }

    public UserGroup getGroup(){
        return this.group;
    }

    public User setGroup(UserGroup group){
        Objects.requireNonNull(group);
        this.group = group;
        return this;
    }

    public Job getJob(){
        return this.job;
    }

    public User setJob(Job newJob){
        Objects.requireNonNull(newJob);
        this.job = newJob;
        return this;
    }

    @Override
    public String toString() {
        return    "id : " + this.id + "\n"
                + "First Name : " + getFirstName() + "\n"
                + "Last Name : " + getLastName() + "\n"
                + "username : " + getUsername() + "\n"
                + "online : " + isOnline() + "\n"
                + "Group : " + this.group.toString() + "\n"
                + "Job : " + this.job.toString() + "\n";
    }
}
