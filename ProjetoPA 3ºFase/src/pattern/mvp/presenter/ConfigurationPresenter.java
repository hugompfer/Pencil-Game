/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.presenter;

import pattern.dao.ConfigurationDAO;
import java.util.ArrayList;
import pattern.singleton.LoggerConfiguration.Options;
import pattern.mvp.view.ConfigurationViewer;

/**
 *  ConfigurationPresenter representa a classe presenter que faz interação entre o menu de configurações do logger e a persistência dos dados.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class ConfigurationPresenter {

    private ConfigurationDAO model;
    private ConfigurationViewer view;

    public ConfigurationPresenter(ConfigurationDAO model, ConfigurationViewer view) {
        this.model = model;
        this.view = view;
        view.setTriggers(this);
    }

    
    public ArrayList<Options> getValues() {
        return new ArrayList<>(model.getCurrentLoggerConfiguration().getValues());
    }

    /**
     * Permite ativar uma opção de registo no logger.
     *
     * @param o opção a ativar.
     */
    public void ativateOption(Options o) {
        if (model.updateOption(o, true)) {
            view.showInfo("Ativação efetuada com sucesso");
        } else {
            view.showInfo("Ativação não foi efetuada com sucesso");
        }
        checkOption(o);
        view.clear();
    }

    /**
     * Permite desativar uma opção de registo no logger.
     *
     * @param o opção a desativar.
     */
    public void deactivationOption(Options o) {
        if (model.updateOption(o, false)) {
            view.showInfo("Desativação efetuada com sucesso");
        } else {
            view.showInfo("Desativação não foi efetuada com sucesso");
        }
        checkOption(o);
        view.clear();
    }

    /**
     * Permite ativar ou desativar uma opção de registo no logger consoante o
     * seu estdado atual.
     *
     * @param option opcção a verificar.
     */
    public void checkOption(Options option) {
        if (model.getCurrentLoggerConfiguration().isAtivated(option)) {
            view.putEnable(false);
        } else {
            view.putEnable(true);
        }
    }

}
