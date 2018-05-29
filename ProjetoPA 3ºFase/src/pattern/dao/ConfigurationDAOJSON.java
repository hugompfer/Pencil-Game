/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pattern.singleton.LoggerConfiguration;

/**
 * ConfigurationDAOJSON representa uma configuração do tipo json
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class ConfigurationDAOJSON implements ConfigurationDAO {

    private final String  basePath;
    private LoggerConfiguration configuration;
    private final static String filename = "players.dat";

    public ConfigurationDAOJSON(String basePath) {
        this.basePath = basePath;
        configuration = new LoggerConfiguration();
        loadAll();
    }

    //le do ficheiro
    private void loadAll() {
        try {
            FileInputStream fileIn = new FileInputStream(this.basePath + filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.configuration = (LoggerConfiguration) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            // i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            return;
        }

    }

    //da save
    private void saveAll() {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(basePath + filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(configuration);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }

    }

    /**
     * Permite atualizar as configuration
     *
     * @param lc logger configuration a atualizar
     * @return true se atualizar- false o contrario
     */
    @Override
    public boolean updateConfiguration(LoggerConfiguration lc) {
        if (lc == null) {
            return false;
        }
        configuration = lc;
        saveAll();
        return true;
    }

    @Override
    public LoggerConfiguration getCurrentLoggerConfiguration() {
        return configuration;
    }
    
    
    /**
     * Permite atualizar as uma opçao das configuraões
     *
     * @param option
     * @param ativation
     * @return true se atualizar- false o contrario
     */
    @Override
    public boolean updateOption(LoggerConfiguration.Options option, boolean ativation) {
        if (option == null ) {
            return false;
        }
        configuration.AlterOption(option, ativation);
        saveAll();
        return true;
    }
}
