/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.singleton;

import enums.Persistance;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * Representa as configuraçoes de sistema, tendo a persistencia de dados a
 * utilizar
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public final class SystemConfiguration implements Serializable {

    private final Persistance persistanceType;
    private static SystemConfiguration instance;

    private SystemConfiguration() {
        this.persistanceType = inicializePersistance();
    }

    //le do ficheiro o tipo de persistencia a utilizar
    private Persistance inicializePersistance() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");
            prop.load(input);
            return choosePersistance(prop.getProperty("persistencia"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //escolhe a persistencia
    private Persistance choosePersistance(String type) {
        switch (type) {
            case "serializable":
                return Persistance.SERIALIZABLE;
            case "json":
                return Persistance.JSON;
            case "sqlite":
                return Persistance.SQLITE;
            default:
                return Persistance.SERIALIZABLE;
        }
    }

    
    public static SystemConfiguration getInstance() {
        if (instance == null) {
            instance = new SystemConfiguration();
        }
        return instance;
    }

    public Persistance getPersistance() {
        return persistanceType;
    }
}
