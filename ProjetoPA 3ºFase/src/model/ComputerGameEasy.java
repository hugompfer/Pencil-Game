/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Random;
import tads.Edge;

/**
 * ComputerGameEasy representa o modo de jogo facil em que o jogador terá de enfrentar 
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public class ComputerGameEasy extends ComputerGame {

    private final Random random;

    public ComputerGameEasy(User p1, Machine p2) {
        super(p1, p2);
        random = new Random();
    }

    
    /**
     * Permite devolver uma edge disponivel de uma forma aleatoria
     *
     * @return aresta escolhida
     */
    @Override
    public Edge<Connection, Corner> chooseEdge(ArrayList<Edge<Connection, Corner>> list) {
        int size = list.size();
        int chosed = size > 2 ? random.nextInt(size - 1) : 0;
        return list.get(chosed);
    }

}
