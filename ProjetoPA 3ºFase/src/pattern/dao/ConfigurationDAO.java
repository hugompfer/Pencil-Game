/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.dao;

import pattern.singleton.LoggerConfiguration;
import pattern.singleton.LoggerConfiguration.Options;


/**
 * ConfigurationDAO representa uma interface de configurações
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public interface ConfigurationDAO {
     public boolean updateConfiguration(LoggerConfiguration lc);
     public LoggerConfiguration getCurrentLoggerConfiguration();
     public boolean updateOption(Options option,boolean ativation);
}
