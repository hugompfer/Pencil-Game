/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tads;

import model.Connection;
import model.Corner;

/**
 * Representa uma definição da estrutura e comportamento de uma aresta.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public interface Edge<E, V> {

    public E element() throws InvalidEdgeException;

    public Vertex<V>[] vertices();

    public int compareTo(Edge<Connection, Corner> choosenEdge);
}
