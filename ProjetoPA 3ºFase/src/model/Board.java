package model;

import java.io.Serializable;
import tads.Edge;
import tads.InvalidVertexException;
import tads.MyGraph;
import tads.Vertex;
import java.util.ArrayList;
import java.util.List;
import pattern.memento.Memento;

/**
 * Board representa a tela onde os vértices e arestas são distrubidos pelos
 * lugares respetivos de forma a formarem o hexágono.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
  * @version 2.0 (08/01/2018)
 */
public class Board implements Serializable {

    private MyGraph<Corner, Connection> figure;

    /**
     * Cria um novo Board efetuando a distribuição dos vértices e arestas pelas
     * devidas posições de forma a formar um hexágono.
     */
    public Board() {
        figure = new MyGraph<>();
        inicialize();
    }

    // distribui os vértices pelas respetivas posições de forma a formar um hexágono
    private void inicialize() {
        double theta = 2 * Math.PI / 6;
        for (int i = 0; i < 6; i++) {
            figure.insertVertex(new Corner((200 * Math.cos(i * theta) + 300),
                    (200 * Math.sin(i * theta) + 275)));
        }

        for (Vertex<Corner> c : figure.vertices()) {
            for (Vertex<Corner> c2 : figure.vertices()) {
                if (c != c2 && !figure.areAdjacent(c, c2)) {
                    figure.insertEdge(c, c2, new Connection());
                }
            }
        }
    }

    /**
     * Permite obter uma lista com todos os vértices.
     *
     * @return lista de vértices.
     */
    public ArrayList<Corner> getPositions() {
        ArrayList<Corner> list = new ArrayList<>();
        for (Vertex<Corner> c : figure.vertices()) {
            list.add(c.element());
        }
        return list;
    }

    /**
     * Pemite obter o grafo/figura que sustenta o Board.
     *
     * @return grafo.
     */
    public MyGraph<Corner, Connection> getFigure() {
        return figure;
    }
    
    public Iterable<Edge<Connection, Corner>> incidentEdges(Edge<Connection, Corner> edge){
        return figure.incidentEdges(edge);
    }

    public Iterable<Edge<Connection, Corner>> edges() {
        return figure.edges();
    }
  

    /**
     * Permite Permite se com um jogador e uma aresta dada formam um triangulo
     * 
     *
     * @param edge
     * @param player
     *
     * @return true se fizer triangulo, false se não

     */
    public boolean hasTriangule(Edge<Connection, Corner> edge, Player player) {
        Vertex<Corner> v1 = edge.vertices()[0];
        Vertex<Corner> v2 = edge.vertices()[1];
        for (Edge<Connection, Corner> c : figure.incidentEdges(v1)) {
            Vertex<Corner> v3 = c.vertices()[0];
            Vertex<Corner> v4 = c.vertices()[1];
            if (c != edge && c.element().getSeletorUser() == player) {
                Vertex<Corner> v = (v1 == v3) ? v4 : v3;
                for (Edge<Connection, Corner> e2 : figure.incidentEdges(v)) {
                    Vertex<Corner> v5 = e2.vertices()[0];
                    Vertex<Corner> v6 = e2.vertices()[1];
                    if (e2 != c && e2.element().getSeletorUser() == player && (v2 == v5 || v2 == v6)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    
    /**
     * Permite Permite criar um momento do tabuleiro atual
     * 
     *
     * @return Memento memento criado

     */
    public Memento createMemento() {
        return new Memento(figure);
    }

    /**
     * Permite Permite atualizar o estado do tabuleiro por um dado por parameto
     * 
     *
     * @param memento memento dado para atualizar
     */
    public void setMemento(Memento memento) {
        figure = memento.getFigure();
    }

    
    /**
     * Permite Permite receber a lista das 3 posiçoes que formam o triangulo ou null se não existir
     * 
     *
     * @return lista das 3 posiçoes que formar o triangulo ou null
     */
    public List<Corner> getTriangle() {
        for (Edge<Connection, Corner> e : figure.edges()) {
            List<Corner> list = getTriangle(e);
            if (list != null) {
                return list;
            }
        }
        return null;
    }

    
    //devolve a lista de corner que formam um triangulo, se a aresta dada tiver 2 arestas incidentes do mesmo utilizador ou null se não formar
    private List<Corner> getTriangle(Edge<Connection, Corner> edge) {
        List<Corner> list = new ArrayList<>();
        Vertex<Corner> v1 = edge.vertices()[0];
        Vertex<Corner> v2 = edge.vertices()[1];
        list.add(v1.element());
        Player p = edge.element().getSeletorUser();
        if (p == null) {
            return null;
        }
        for (Edge<Connection, Corner> c : figure.incidentEdges(v1)) {
            Vertex<Corner> v3 = c.vertices()[0];
            Vertex<Corner> v4 = c.vertices()[1];
            if (c != edge && c.element().getSeletorUser() == edge.element().getSeletorUser()) {
                Vertex<Corner> v = (v1 == v3) ? v4 : v3;
                list.add(v.element());
                for (Edge<Connection, Corner> e2 : figure.incidentEdges(v)) {
                    Vertex<Corner> v5 = e2.vertices()[0];
                    Vertex<Corner> v6 = e2.vertices()[1];
                    if (e2 != c && e2.element().getSeletorUser() == edge.element().getSeletorUser() && (v2 == v5 || v2 == v6)) {
                        list.add(v2.element());
                        return list;
                    }
                }
                list.remove(v.element());
            }
        }
        return null;
    }
}
