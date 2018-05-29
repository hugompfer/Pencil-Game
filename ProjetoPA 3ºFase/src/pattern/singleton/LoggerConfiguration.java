/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.singleton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Representa o uam configuação de logger
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 1.0 (22/11/2017)
 */
public class LoggerConfiguration implements Serializable {

    private final HashMap<Options, Boolean> configuration;

    /**
     * Representa as opçoes de configuaçao do logger
     *
     * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
     * @version 2.0 (08/01/2018)
     */
    public enum Options implements Serializable {
        REGISTERS,
        AUTENTICATION,
        GAME
    };

    public LoggerConfiguration() {
        configuration = new HashMap<>();
        inicializeConfiguration();
    }

    //inicializa as configurações
    private void inicializeConfiguration() {
        configuration.put(Options.REGISTERS, Boolean.FALSE);
        configuration.put(Options.GAME, Boolean.FALSE);
        configuration.put(Options.AUTENTICATION, Boolean.FALSE);
    }

    /**
     * Permite ativatar/desativar uma opçao de opçao das configurações
     *
     * @param option opçao a alterar
     * @param ativation ativar=true ou desativar=false
     */
    public void AlterOption(Options option, Boolean ativation) {
        configuration.replace(option, ativation);
    }

    /**
     * Permite verificar se a opção esta ativada
     *
     * @param option opçao a verificar
     * @return true se tiver atiado
     */
    public boolean isAtivated(Options option) {
        return configuration.get(option);
    }

    /**
     * Permite devolver a lista de opçoes
     *
     * @return a lista de opçoes
     */
    public Set<Options> getValues() {
        return configuration.keySet();
    }
}
