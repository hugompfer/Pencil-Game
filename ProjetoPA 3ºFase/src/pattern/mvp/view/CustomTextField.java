/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author tinet
 */
public class CustomTextField extends Group {

    private TextField field;
    private Label textOutside;

    public CustomTextField(String info) {
        getStylesheets().add("CssFiles/CustomTextFieldStyle.css");
        setupLayout(info);
        setupGroup();
        setupBehaviour();
    }

    public void clear() {
        field.clear();
    }

    public void setText(String text) {
        field.setText(text);
    }

    public String getText() {
        return field.getText();
    }

    private void setupLayout(String info) {
        setupField();
        setuptextOutside(info);
    }

    private void setupField() {
        field = new TextField("");
        field.setTranslateY(60);
    }

    private void setuptextOutside(String info) {
        textOutside = new Label(info);
        textOutside.setTranslateX(9);
        textOutside.setTranslateY(30);
    }

    private void setupGroup() {
        getChildren().addAll(textOutside, field);
    }

    public TextField getField() {
        return field;
    }

    private void setupBehaviour() {
        textOutside.setOnMouseClicked(e -> {
            field.requestFocus();
        });

        field.setOnMouseEntered(e -> {
            field.requestFocus();
        });
    }

    public StringProperty textProperty() {
        return field.textProperty();
    }

}
