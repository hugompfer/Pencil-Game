/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.beans.property.StringProperty;
import javafx.scene.control.PasswordField;

/**
 *
 * @author tinet
 */
public class CustomPasswordTextField extends CustomTextField {

    private PasswordField field;

    public CustomPasswordTextField(String info) {
        super(info);
        setupLayout();
        setupBehaviour();
    }

    private void setupLayout() {
        field = new PasswordField();
        super.getChildren().set(1, field);
        field.setTranslateY(60);
    }

    private void setupBehaviour() {
        field.setOnMouseEntered(e -> {
            field.requestFocus();
        });
    }

    @Override
    public StringProperty textProperty() {
        return field.textProperty();
    }

    @Override
    public String getText() {
        return field.getText();
    }

    @Override
    public void setText(String text) {
        field.setText(text);
    }
}
