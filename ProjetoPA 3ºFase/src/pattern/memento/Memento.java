/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.memento;

import java.io.Serializable;
import model.Connection;
import model.Corner;
import tads.Edge;
import tads.Vertex;
import tads.MyGraph;

/**
 * Memento representa o momento de uma tabuleiro dado instante
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class Memento implements Serializable {

    private MyGraph<Corner, Connection> figure;


    public Memento(MyGraph<Corner, Connection> figure) {
        this.figure = copy(figure);
    }

    //copia da figura dada como parametro para a figura do objeto, com novas referencia de memoria
    private MyGraph<Corner, Connection> copy(MyGraph<Corner, Connection> figure) {
        MyGraph<Corner, Connection> graph = new MyGraph<>();
        for (Vertex<Corner> v : figure.vertices()) {
            graph.insertVertex(v.element());
        }
        for (Edge<Connection, Corner> e : figure.edges()) {
            graph.insertEdge(e.vertices()[0].element(), e.vertices()[1].element(), new Connection(e.element().getSeletorUser(),e.element().getColor()));
        }
        return graph;
    }

    public MyGraph<Corner, Connection> getFigure() {
        return figure;
    }
}
