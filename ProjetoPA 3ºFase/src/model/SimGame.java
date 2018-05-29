/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.Serializable;


/**
 * SimGame representa um tipo de jogo onde se defrontarão 2 jogadores.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public class SimGame extends Game implements Serializable{

    private boolean wentBackP1;
    private boolean wentBackP2;

    /**
     * Cria um novo SimGame com os respetivos jogadores e informações.
     *
     * @param player1 Jogador 1.
     * @param player2 Jogador 2.
     */
    public SimGame(User player1, User player2) {
        super(player1, player2);
        wentBackP1 = false;
        wentBackP2 = false;
    }

    /**
     * Permite verificar se o jogador anterior pode ou não desfazer a sua
     * jogada.
     *
     * @return true/false caso possa ou não reverter a jogada.
     */
    public boolean canGoBack() {
        return !super.isFirstPlayerTurn() ? !wentBackP2 && !isFinished()   : !wentBackP1 && !isFinished();
    }

    //permite verificar se a jogada é reversivEL
    private boolean checkIfCanRollBack() {
        if (getPlayerTurn().compareTo(getPlayers()[0]) == 0 && !wentBackP1) {
            wentBackP1 = true;
            return true;
        } else if (!wentBackP2) {
            wentBackP2 = true;
            return true;
        }
        return false;
    }

    /**
     * Permite desfazer a última jogada.
     */
    private void restore() {
        getHistoric().restoreState(getBoard());
    }

    /**
     * Permite desfazer uma jogada anulando a escolha da aresta.
     *
     * @return
     */
    public void rollBack() {
        if (checkIfCanRollBack()) {
            getHistoric().restoreState(getBoard());
            super.changeTurn();
        }
    }
}
