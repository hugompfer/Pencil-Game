/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Machine representa o Computador que jogará contra o utilizador no modo de
 * jogo adequado.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class Machine extends Player implements Serializable{
    /**
     * Cria uma nova machine com os seus respetivos dados.
     *
     * @param id número identificador do jogador.
     * @param statistics
     * @param hostName Nome da máquina.
     */
    
     public Machine(int id, IndividualStatistics statistics,String hostName) {
        super(id,statistics,hostName);
    }
    
    public Machine(int id,String hostname) {
        super(id,hostname);
    }
    
    public Machine(String hostname) {
        super(hostname);
    }
}
