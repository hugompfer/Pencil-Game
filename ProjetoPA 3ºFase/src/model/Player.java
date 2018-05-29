/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Player representa um participante do jogo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class Player implements Serializable, Comparable {

    private int id;
    private static int quantityOfPlayers;
    private IndividualStatistics statitics;
    private String identification;

    /**
     * Cria um novo jogo .
     *
     * @param identification
     */
    public Player(String identification) {
        this(++quantityOfPlayers, identification);
    }

    public Player(int id, String identification) {
        this.id = id;
        statitics = new IndividualStatistics();
        this.identification = identification;
    }

    public Player(int id, IndividualStatistics statitics, String identification) {
        this.id = id;
        this.statitics = statitics;
        this.identification = identification;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public IndividualStatistics getIndividualStatistics() {
        return statitics;
    }

    public static void setQuantityOfPlayers(int number) {
        quantityOfPlayers = number;
    }

    /**
     * Permite obter o número identificador do jogador.
     *
     * @return Número identificador.
     */
    public int getId() {
        return id;
    }

    /**
     * Permite obter em forma de leitura, as informações do jogador.
     *
     * @return String texto com as informações.
     */
    @Override
    public String toString() {
        return "ID: " + id;
    }

    /**
     * Permite saber se dois jogadores são iguais.
     *
     * @return 0 se for igual, <0 se o 1 id for menor que o 2, >0 se o 2 id for
     * menor que o 1
     */
    @Override
    public int compareTo(Object o) {
        return id - ((Player) o).getId();
    }
  
}
