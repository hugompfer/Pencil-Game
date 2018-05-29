/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import pattern.mvp.presenter.ConfigurationPresenter;

/**
 *
 * @author hugob
 */
public interface ConfigurationInteraction {

    public void showInfo(String info);

    public void setTriggers(ConfigurationPresenter presenter);

    public void clear();
    
    public void putEnable(boolean ativation);
}
