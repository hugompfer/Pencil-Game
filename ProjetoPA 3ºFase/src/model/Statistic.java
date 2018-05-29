/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Statistic representa um estatistica de um jogador
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 1.0 (22/11/2017)
 */
public class Statistic implements Serializable {

    private int numberOfGames;
    private int numberOfWins;
    private int numberOfDefeats;
    private int totalOfTime;
    
    public Statistic(int numberOfGames, int numberOfWins, int numberOfDefeats, int totalOfTime) {
        this.numberOfDefeats = numberOfDefeats;
        this.numberOfGames = numberOfGames;
        this.numberOfWins = numberOfWins;
        this.totalOfTime = totalOfTime;
    }

    public Statistic() {
        this.numberOfDefeats = 0;
        this.numberOfGames = 0;
        this.numberOfWins = 0;
        this.totalOfTime = 0;
    }

    public int getNumberOfDefeats() {
        return numberOfDefeats;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public int getTotalOfTime() {
        return totalOfTime;
    }

    public void incrementNumberOfDefeats() {
        this.numberOfDefeats++;
    }

    public void incrementNumberGames() {
        this.numberOfGames++;
    }

    public void incrementNumberOfWins() {
        this.numberOfWins++;
    }

    public void incrementTotalOfTime(int totalOfTime) {
        this.totalOfTime += totalOfTime;
    }

    public void copyStatistics(int numberOfGames, int numberOfWins, int numberOfDefeats, int totalOfTime) {
        this.numberOfDefeats = numberOfDefeats;
        this.numberOfGames = numberOfGames;
        this.numberOfWins = numberOfWins;
        this.totalOfTime = totalOfTime;
    }

}
