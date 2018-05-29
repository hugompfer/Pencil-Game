/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import pattern.singleton.SystemConfiguration;
import static pattern.factory.PlayerDAOFactory.PlayerDAOFactory;
import pattern.dao.PlayerDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * PlayersManager representa um gestor de jogadores
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class PlayersManager implements Serializable {

    private List<Player> playersLogged;
    private PlayerDAO playersRegisted;

    public PlayersManager() {
        playersLogged = new ArrayList<>(3);
        playersRegisted = PlayerDAOFactory(SystemConfiguration.getInstance().getPersistance());
    }

    /**
     * Permite adicionar um jogador ao jogadores logados
     *
     * @param player jogador a adicionar
     */
    public void addPlayer(Player player) {
        playersLogged.add(player);
    }

    /**
     * Permite remover um jogador ao jogadores logados
     *
     * @param player jogador a adicionar
     */
    public void removePlayer(Player player) {
        playersLogged.remove(player);
    }

    /**
     * Permite remover o 1 jogador logado
     *
     */
    public void removeFirstLoggedPlayer() {
        playersLogged.remove(getFirstUser());
    }

    /**
     * Permite remover o 2 jogador logado
     *
     */
    public void removeSecondLoggedPlayer() {
        playersLogged.remove(getSecondUser());
    }

    /**
     * Permite atualizar um jogador por outro nos jogadores logados
     *
     */
    public void updateUser(User user) {
        //iterar e encontrar aquele e depois
        for (int i = 0; i < playersLogged.size(); i++) {
            if (playersLogged.get(i).getId() == user.getId()) {
                playersLogged.set(i, user);
                break;
            }
        }

    }

    /**
     * Permite saber se um utilizador esta logado
     *
     * @param u
     */
    public boolean alreadyLogged(User u) {
        User u1 = (User) getFirstUser();
        User u2 = (User) getSecondUser();
        if (u != null && u1 != null && u1.getId() == u.getId()) {
            return true;
        } else if (u != null && u2 != null && u2.getId() == u.getId()) {
            return true;
        }
        return false;
    }

    /**
     * Permite saber se tem 2 uttilizadores logados
     *
     */
    public boolean has2UsersLogged() {
        int i = 0;
        for (Player p : playersLogged) {
            if (p instanceof User) {
                i++;
            }
        }
        return i == 2;
    }

    /**
     * Permite saber se tem 1 uttilizadores logados
     *
     * @return 
     */
    public boolean has1UserLogged() {
        int i = 0;
        for (Player p : playersLogged) {
            if (p instanceof User) {
                i++;
            }
        }
        return i >= 1;
    }

    
    /**
     * Permite saber se tem 2 jogadores logados
     *
     * @return 
     */
    public boolean has2PlayersLogged() {
        return playersLogged.size() >= 2;
    }

    /**
     * Permite receber o primeiro utilizador logado
     *
     * @return 1 utilizador
     */
    public User getFirstUser() {
        for (Player p : playersLogged) {
            if (p instanceof User) {
                return (User) p;
            }
        }
        return null;
    }

     /**
     * Permite receber o segundo utilizador logado
     *
     * @return 2 utilizador
     */
    public User getSecondUser() {
        for (Player p : playersLogged) {
            if (p instanceof User && p != getFirstUser()) {
                return (User) p;
            }
        }
        return null;
    }

    /**
     * Permite receber a maquina logado
     *
     * @return maquina
     */
    public Player getMachine() {
        for (Player p : playersLogged) {
            if (p instanceof Machine) {
                return p;
            }
        }
        return null;
    }

    public PlayerDAO getPlayersRegisted() {
        return playersRegisted;
    }

}
