/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Cheker representa a superclasse que faz verificações e validações ao nivel de input do utilizador 
 * e a sua interação com a persistência de dados.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class Checker implements Serializable{

    private PlayersManager manager;

    public Checker(PlayersManager manager) {
        this.manager = manager;
    }

    public PlayersManager getManager() {
        return manager;
    }
    
    /**
     * Permite obter o utilizador atarvés de determinado username.
     * @param username username procurado.
     * @return Número identificador.
     */
    public User findUser(String username) {
        return manager.getPlayersRegisted().findUser(username);
    }
}

