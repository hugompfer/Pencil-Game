/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import tads.Edge;
import java.util.ArrayList;


/**
 * ComputerGame representa o modo de jogo em que o jogador terá de enfrentar 
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public abstract class ComputerGame extends Game {

   
    /**
     * Cria um novo jogo contra o computador.
     *
     * @param p1 Jogador 1.
     * @param p2 Computador/Jogador 2.
     *
     */
    public ComputerGame(User p1, Machine p2) {
        super(p1, p2);
    }


    //ver quais as edges que ainda podem ser escolhidas
    private ArrayList<Edge<Connection, Corner>> getAvailableEdges() {
        ArrayList<Edge<Connection, Corner>> list = new ArrayList<>();
        for (Edge<Connection, Corner> e : getBoard().edges()) {
            if (!e.element().isSelected()) {
                list.add(e);
            }
        }
        return list;
    }

    
    public Edge<Connection, Corner> choose() {
        ArrayList<Edge<Connection, Corner>> list=getAvailableEdges();
        return chooseEdge(list);
    }
    
    /**
     * Permite que o computador possa fazer a escolha da aresta que pretende
     * selecionar.
     *
     * @param list
     * @return Aresta escolhida.
     */
    public abstract Edge<Connection, Corner> chooseEdge(ArrayList<Edge<Connection, Corner>> list);
    


}
