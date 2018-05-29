/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.dao;

import pattern.singleton.LoggerConfiguration;

/**
 * ConfigurationManager representa um gestor de configurações
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class ConfigurationManager {

    private ConfigurationDAO configuration;

    public ConfigurationManager() {
        configuration = new ConfigurationDAOSerializable("");
        //configuration = new ConfigurationDAOJSON("");
    }

    /**
     * Permite atualizar as configuration
     *
     * @param lc logger configuration a atualizar
     */
    public void update(LoggerConfiguration lc) throws
            ConfigurationManagerException {

        if (!configuration.updateConfiguration(lc)) {
            throw new ConfigurationException("Objeto invalido");
        }

    }

    public LoggerConfiguration getCurrentConfig() {
        return configuration.getCurrentLoggerConfiguration();
    }

}
