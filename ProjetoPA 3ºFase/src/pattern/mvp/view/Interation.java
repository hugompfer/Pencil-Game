/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import pattern.mvp.presenter.GamePresenter;
import pattern.mvp.presenter.GamePresenter;

/**
 *
 * @author hugob
 */
public interface Interation {
    
    void showResult(String msg);
    void setTriggers(GamePresenter controllers);
    void paintLine(Line line,Color c);
    void makeTriangle(Double[] positions);
   
}
