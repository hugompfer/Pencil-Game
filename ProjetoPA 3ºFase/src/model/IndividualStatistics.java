/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enums.TypeOfGames;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * IndividualStatistics representa o conjunto de estaistica do um jogador
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public class IndividualStatistics implements Serializable {

    private Map<TypeOfGames, Statistic> statistics;

    public IndividualStatistics() {
        this.statistics = new HashMap<>();
        statistics.put(TypeOfGames.SIMGAME, new Statistic());
        statistics.put(TypeOfGames.EASYCOMPUTERGAME, new Statistic());
        statistics.put(TypeOfGames.HARDCOMPUTERGAME, new Statistic());
    }

    /**
     * Permite incrementar o numero de derotas de um determinado tipo de jogo
     *
     * @param type tipo de jogo
     */
    public void incrementNumberOfDefeats(TypeOfGames type) {
        statistics.get(type).incrementNumberOfDefeats();
    }
    
    /**
     * Permite incrementar o numero de jogos de um determinado tipo de jogo
     *
     * @param type tipo de jogo
     */
    public void incrementNumberGames(TypeOfGames type) {
        statistics.get(type).incrementNumberGames();
    }

    /**
     * Permite incrementar o numero de jogos ganhos de um determinado tipo de jogo
     *
     * @param type tipo de jogo
     */
    public void incrementNumberOfWins(TypeOfGames type) {
        statistics.get(type).incrementNumberOfWins();
    }

    /**
     * Permite incrementar o numero de tempo de jogo de um determinado tipo de jogo
     *
     * @param type tipo de jogo
     * @param totalOfTime tempo de jogo
     */
    public void incrementTotalOfTime(TypeOfGames type, int totalOfTime) {
        statistics.get(type).incrementTotalOfTime(totalOfTime);
    }

    /**
     * Permite devolver o numero total de jogos ganhos de todas os tipos de jogo
     *
     * @return int numero total de vitorias
     */
    public int getTotalOfwins() {
        return statistics.get(TypeOfGames.SIMGAME).getNumberOfWins()
                + statistics.get(TypeOfGames.EASYCOMPUTERGAME).getNumberOfWins()
                + statistics.get(TypeOfGames.HARDCOMPUTERGAME).getNumberOfWins();
    }

    /**
     * Permite devolver o numero total de jogos de todas os tipos de jogo
     *
     * @return int numero total de jogos
     */
    public int getTotalOfGames() {
        return statistics.get(TypeOfGames.SIMGAME).getNumberOfGames()
                + statistics.get(TypeOfGames.EASYCOMPUTERGAME).getNumberOfGames()
                + statistics.get(TypeOfGames.HARDCOMPUTERGAME).getNumberOfGames();
    }

    /**
     * Permite devolver o numero total de derotas de todas os tipos de jogo
     *
     * @return int numero total de derrotas
     */
    public int getTotalOfdefeats() {
        return statistics.get(TypeOfGames.SIMGAME).getNumberOfDefeats()
                + statistics.get(TypeOfGames.EASYCOMPUTERGAME).getNumberOfDefeats()
                + statistics.get(TypeOfGames.HARDCOMPUTERGAME).getNumberOfDefeats();
    }

     /**
     * Permite devolver o numero total de jogos,vitorias, derrotas e tempo de jogo
     *
     * @return int numero total 
     */
    public int getTotalValue() {
        return getTotalOfwins()+getTotalOfGames()+getTotalOfdefeats()+getTotalOftimePlayed();
    }
    
    /**
     * Permite devolver o numero total de tempo de jogo em minutos de todas os tipos de jogo
     *
     * @return int numero total de derrotas
     */
    public int getTotalOftimePlayed() {
        return secToMin(statistics.get(TypeOfGames.SIMGAME).getTotalOfTime()
                + statistics.get(TypeOfGames.EASYCOMPUTERGAME).getTotalOfTime()
                + statistics.get(TypeOfGames.HARDCOMPUTERGAME).getTotalOfTime());
    }
    
    //converte segundo para minutos
    private int secToMin(int sec){
        return sec/60;
    }

    public Map<TypeOfGames, Statistic> getStatistics(){
        return statistics;
    }
    

    /**
     * Permite copiar um uma estaistica individual para a atual
     *
     * @param statistics para atualizar
     */
    public void copy(IndividualStatistics statistics) {
        for (Entry<TypeOfGames, Statistic> m : this.statistics.entrySet()) {
            Statistic s=statistics.getStatistics().get(m.getKey());
            m.getValue().copyStatistics(s.getNumberOfGames(),
                    s.getNumberOfWins(),s.getNumberOfDefeats(),s.getTotalOfTime());
        }
    }

}
