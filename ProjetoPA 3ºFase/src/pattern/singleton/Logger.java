/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.singleton;

import pattern.dao.ConfigurationDAO;
import pattern.dao.ConfigurationDAOSerializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import pattern.singleton.LoggerConfiguration.Options;


/**
 * Representa o mecanismo de registo das ações do jogo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public final class Logger {

    private static Logger instance;
    private static final String LOGGERFILE = "logger.txt";
    private PrintWriter pwriter;
    private ConfigurationDAO configuration;

    private Logger() {
        connect();
        configuration = new ConfigurationDAOSerializable("");
        //configuration = new ConfigurationDAOJSON("");
    }

    /**
     * Permite iniciar o ficheiro para escrita.
     *
     * @return true/false caso seja efetuado ou não com sucesso.
     */
    public boolean connect() {
        if (pwriter == null) {
            try {
                File file = new File(LOGGERFILE);
                FileWriter fw = new FileWriter(file, true);
                pwriter = new PrintWriter(file);
            } catch (FileNotFoundException ex) {
                pwriter = null;
                return false;

            } catch (IOException e) {
                pwriter = null;
                return false;
            }
            return true;
        }
        return true;
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Permite escrever algum acontecimento em ficheiro.
     *
     * @param str Texto a incluir.
     * @param option Tipo de registo a efetuar.
     * @throws LoggerException
     */
    public void writeToLog(String str, Options option) throws LoggerException {
        
        if (pwriter == null) {
            throw new LoggerException("Connection failed");
        }
        if (configuration.getCurrentLoggerConfiguration().isAtivated(option)) {
            pwriter.println(new Date().toString() + " " + str);
        }
        
    }

    /**
     * Permite fechar o ficheiro de escrita.
     *
     * @throws LoggerException
     */
    public void closeLog() throws LoggerException {
        if (pwriter == null) {
            throw new LoggerException("Connection failed");
        }
        pwriter.close();
    }

}
