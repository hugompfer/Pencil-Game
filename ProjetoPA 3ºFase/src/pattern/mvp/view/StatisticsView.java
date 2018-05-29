/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.scene.Scene;
import pattern.mvp.presenter.StatisticsPresenter;

/**
 *
 * @author hugob
 */
public interface StatisticsView {

    public void setTriggers(StatisticsPresenter presenter);

    public Scene getScene();


}
