/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.memento;

import java.io.Serializable;
import java.util.Stack;
import model.Board;

/**
 * MementoCareTaker representa um conjunto de momentos
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class MementoCareTaker implements Serializable {

    private Stack<Memento> objMementos;

    public MementoCareTaker() {
        objMementos = new Stack<>();
    }

    /**
     * Permite salvar o estado de um tabuleiro
     *
     * @param board tabuleira a gravar
     */
    public void saveState(Board board) {
        Memento objMemento = board.createMemento();
        objMementos.push(objMemento);
    }

    /**
     * Permite restaurar o estado de um tabuleiro
     *
     * @param board tabueiro a meter novos valores
     */
    public void restoreState(Board board) {
        if (objMementos.isEmpty()) {
            return;
        }
        Memento objMemento = objMementos.pop();
        board.setMemento(objMemento);

    }

    public Stack<Memento> getStack() {
        return objMementos;
    }

}
