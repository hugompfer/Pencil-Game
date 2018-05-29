/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import pattern.dao.PlayerDAO;
import java.io.Serializable;
import pattern.strategy.ComparatorWinGamesOfPlayer;
import tads.LinkedListAdapter;

/**
 * StatisticsManager representa um gestor de estatisticas
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class StatisticsManager implements Serializable{
   
    private LinkedListAdapter<Player> list;
    private PlayerDAO players;
            
    public StatisticsManager(PlayerDAO players){
       list=new LinkedListAdapter(new ComparatorWinGamesOfPlayer());
       this.players=players;
       inicializeList();
    }
    
    private void inicializeList(){
        for(Player p: players.selectPlayers()){
            list.add(p);
        }
    }
    
    public LinkedListAdapter getList(){
        return list;
    }
    
    public Player find(int id){
        return players.findPlayer(id);
    }
    
}
