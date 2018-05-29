/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import enums.InputField;
import javafx.scene.Scene;


/**
 *
 * @author Tiago
 */
public interface View {

    public void setTriggers(Object presenter);

    public Scene getScene();

    public void showInfo(String info);

    public String getUserInput(InputField field);
}
