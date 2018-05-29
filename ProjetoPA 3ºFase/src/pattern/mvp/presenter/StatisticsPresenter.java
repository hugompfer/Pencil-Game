/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.presenter;

import model.Player;
import model.StatisticsManager;
import tads.LinkedListAdapter;
import pattern.mvp.view.StatisticsViewer;

/**
 * StatisticsPresenter representa a classe presenter que faz interação entre o menu de estatisticas e a persistência dos dados.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class StatisticsPresenter {

    private StatisticsManager model;
    private StatisticsViewer view;
    
    public StatisticsPresenter(StatisticsManager model,StatisticsViewer view){
        this.model=model;
        this.view=view;
        this.view.setTriggers(this);
    }
    
    public LinkedListAdapter<Player> getList(){
        return  model.getList();
    }
    
      /**
     * Permite obter determinado jogador através do seu id.
     *
     * @param id id a verificar.
     * @return Players jogador encontrado.
     */
   public Player find(int id){
       return model.find(id);
   }
    
}
