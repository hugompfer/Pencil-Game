/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.singleton;

/**
 * Representa uma exepção provocada por algo relacionado com o mecanismo de
 * Logger.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class LoggerException extends Exception {
    public LoggerException(String str){
        super(str);
    }
}
