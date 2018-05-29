/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * RowData representa uma linha de uam tabela
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public final class RowData implements Serializable {

    private int id;
    private String name;
    private int totalOfWinGames;

    public RowData(Player p) {
        inicialize(p);
    }

    //inicializar o jogador
    private void inicialize(Player p) {
        this.id = p.getId();
        this.name = p.getIdentification();
        this.totalOfWinGames = p.getIndividualStatistics().getTotalOfwins();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalOfWinGames() {
        return totalOfWinGames;
    }

}
