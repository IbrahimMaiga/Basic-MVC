package ml.kanfa.gui.fx.app.field;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.*;

/**
 * @author Ibrahim Maïga.
 */
public class Field extends VBox{

    private Label label;
    private TextField field;
    private String title;
    private String prompt;

    public enum FieldType {
        TEXT,
        PASSWORD
    }

    private Field(final FieldType type, final String title, final String prompt){
        this.title = title;
        this.prompt = prompt;
        this.field = type == FieldType.TEXT ? new TextField() : new PasswordField();
        this.initialise();
    }

    private Field(final FieldType type, final String text){
        this(type, text, text);
    }

    public static Field newTextField(final String title, final String prompt){
        return new Field(FieldType.TEXT, title, prompt);
    }

    public static Field newPasswordField(final String title, final String prompt){
        return new Field(FieldType.PASSWORD, title, prompt);
    }

    public static Field newTextField(final String text){
        return new Field(FieldType.TEXT, text);
    }

    public static Field newPasswordField(final String text){
        return new Field(FieldType.PASSWORD, text);
    }

    private void initialise(){
        this.label = new Label(this.title);
        this.field.setPromptText(this.prompt);
        this.label.visibleProperty().bind(this.field.focusedProperty().isEqualTo(new SimpleBooleanProperty(true)));
        this.getChildren().addAll(this.label, this.field);
    }

    public String getPromptText(){
        return this.prompt;
    }

    public String getFieldTitle(){
        return this.title;
    }

    public String getFieldText(){
        return this.field.getText();
    }

    public void setSize(final Dimension dimension){
        this.field.setPrefWidth(dimension.width);
        this.field.setPrefHeight(dimension.height);
    }

    public TextField getField(){
        return this.field;
    }
}
