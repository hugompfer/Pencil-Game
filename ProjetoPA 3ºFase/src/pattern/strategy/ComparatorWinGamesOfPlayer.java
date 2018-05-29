/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.strategy;

import java.io.Serializable;
import java.util.Comparator;
import model.Player;

/**
 * Representa um comparador entre jogadores por numero total de vitorias
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public class ComparatorWinGamesOfPlayer implements Comparator<Player>,Serializable{

     /**
     * Permite comparar se dois jogadores tem numero de vitorias igual, retornando =0 se for igual,<0 se p1 tiver menos jogos ganhos e >0 se p1 tiver mais jogos ganhos
     *
     * @param p1 jogador 1
     * @param p2 jogador 2
     */
    @Override
    public int compare(Player p1, Player p2) {
        return p1.getIndividualStatistics().getTotalOfwins()-p2.getIndividualStatistics().getTotalOfwins();
    }
    
}
