/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashSet;
import tads.Edge;

/**
 * ComputerGameHard representa o modo de jogo facil em que o jogador terá de enfrentar 
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class ComputerGameHard extends ComputerGame {

    public ComputerGameHard(User p1, Machine p2) {
        super(p1, p2);
    }

    
    /**
     * Permite devolver uma edge disponivel de uma forma "racional"
     *
     * @return aresta escolhida
     */
    @Override
    public Edge<Connection, Corner> chooseEdge(ArrayList<Edge<Connection, Corner>> list) {

        for (Edge<Connection, Corner> e : list) {
            if (isNotIncidentEdge(e)){
                return e;
            }
        }

        for (Edge<Connection, Corner> e : list) {
            if (!lost(e, getOtherPlayerTurn()) && !lost(e, getPlayerTurn())) {
                return e;
            }
        }
        
        for (Edge<Connection, Corner> e : list) {
            if (!lost(e, getPlayerTurn())) {
                return e;
            }
        }

        return list.get(0);
    }

    //retorna se uma dada edge tem edges incidentes que foram selecionados
    private boolean isNotIncidentEdge(Edge<Connection, Corner> edge) {
        HashSet<Edge<Connection, Corner>> set = (HashSet<Edge<Connection, Corner>>) getBoard().incidentEdges(edge);
        for (Edge<Connection, Corner> e : set) {
            if (e.element().isSelected()) {
                return false;
            }
        }
        return true;
    }
}
