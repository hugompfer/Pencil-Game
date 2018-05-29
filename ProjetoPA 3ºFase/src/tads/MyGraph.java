/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tads;

import java.io.Serializable;
import model.Connection;
import model.Corner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Representa a estrutura de suporte á organização dos elementos do jogo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 * @param <V> Tipo de dados 1
 * @param <E> Tipo de dados 2
 */
/**
 *
 * @author patricia.macedo
 * @param <V>
 * @param <E>
 */
public class MyGraph<V, E> implements Graph<V, E>, Serializable {

    private int nEdges;
    private HashMap<V, Vertex<V>> listVertices;

    public MyGraph() {
        this.nEdges = 0;
        listVertices = new HashMap();
    }

    //verifica se o vértice pode ser utilizado
    private MyVertex checkVertex(Vertex<V> p) throws InvalidVertexException {
        if (p == null) {
            throw new InvalidVertexException("WRONG vertex");
        }
        try {
            return (MyVertex) p;
        } catch (ClassCastException e) {
            throw new InvalidVertexException("WRONG vertex");
        }
    }

    //verifica se a aresta pode ser utilizada
    private MyEdge checkEdge(Edge<E, V> ed) throws InvalidEdgeException {
        if (ed == null) {
            throw new InvalidEdgeException("WRONG edge");
        }
        try {
            return (MyEdge) ed;
        } catch (ClassCastException e) {
            throw new InvalidEdgeException("WRONG edge");
        }
    }

    @Override
    public int numVertices() {
        return listVertices.size();

    }

    @Override
    public int numEdges() {
        return nEdges;
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return listVertices.values();

    }

    @Override
    public Iterable<Edge<E, V>> edges() {
        return setofEdges();
    }

    private Set<Edge<E, V>> setofEdges() {
        Set<Edge<E, V>> edges = new HashSet();
        for (Vertex<V> vertex : vertices()) {

            edges.addAll(checkVertex(vertex).listaEdges);
        }
        return edges;
    }

    /**
     * Permite substituir o elemento de determinado vértice por outro.
     *
     * @param p Vértice onde se pretende efetuar a troca
     * @param elem Novo elemento a inserir
     * @return v Elemento antigo
     * @throws InvalidVertexException
     */
    @Override
    public V replace(Vertex<V> p, V elem) throws InvalidVertexException {
        if (!this.listVertices.containsValue(p)) {
            throw new InvalidVertexException("vertex does not exist");
        }

        MyVertex vertex = checkVertex(p);
        V elem1 = vertex.element();
        vertex.elem = elem;
        return elem1;
    }

    /**
     * Permite substituir o elemento de determinada aresta por outro.
     *
     * @param p Aresta onde se pretende efetuar a troca
     * @param elem Novo elemento a inserir
     * @return v Elemento antigo
     * @throws InvalidEdgeException
     */
    @Override
    public E replace(Edge<E, V> p, E elem) throws InvalidEdgeException {
        if (!existEdge(p)) {
            throw new InvalidEdgeException("Invalid Edge");
        }
        MyEdge edge = checkEdge(p);
        E elem1 = edge.element();
        edge.elem = elem;
        return elem1;
    }

    private boolean existEdge(Edge e) {
        return setofEdges().contains(e);
    }

    /**
     * Permite obter a lista de arestas incidentes num vértice dado
     *
     * @param v Vértice em causa
     * @return lista de aresta incidentes
     * @throws InvalidVertexException
     */
    @Override
    public Iterable<Edge<E, V>> incidentEdges(Vertex<V> v) throws InvalidEdgeException {
        if (!this.listVertices.containsValue(v)) {
            throw new InvalidVertexException("vertex does not exist");
        }
        return checkVertex(v).listaEdges;
    }


    /**
     * Permite obter a lista de arestas incidentes a determinada aresta dada
     *
     * @param e Aresta em causa
     * @return lista de aresta incidentes
     * @throws InvalidVertexException
     */
    @Override
    public Iterable<Edge<E, V>> incidentEdges(Edge<E, V> e) throws InvalidEdgeException {
        if (!this.listVertices.containsValue(e.vertices()[0]) && !this.listVertices.containsValue(e.vertices()[1])) {
            throw new InvalidVertexException("vertexs does not exist");
        }
        HashSet<Edge<E, V>> set = new HashSet<>(checkVertex(e.vertices()[0]).listaEdges);
        set.addAll(new HashSet<>(checkVertex(e.vertices()[1]).listaEdges));
        set.remove(e);
        return set;
    }

    /**
     * Permite obter o vértice oposto a determinado vértice
     *
     * @param v Vértice do qual de pretende oposto
     * @param e Aresta ligada a esse vértice
     * @return Vértice oposto
     * @throws InvalidVertexException
     */
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E, V> e) throws InvalidVertexException, InvalidEdgeException {
        Vertex<V>[] vertices = checkEdge(e).vertices();
        if (vertices[0] == v) {
            return vertices[1];
        }
        if (vertices[1] == v) {
            return vertices[0];
        }
        throw new InvalidVertexException("Invalid vertex");
    }

    /**
     * Permite verificar se dois vértices são adjacentes/possuem alguma aresta
     * em comum
     *
     * @param u Vértice 1
     * @param v Vértice 2
     * @return true/false caso exista ou não adjacência
     * @throws InvalidVertexException
     */
    @Override
    public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidVertexException {
        for (Edge<E, V> e : edges()) {
            MyEdge myedge = checkEdge(e);
            if (myedge.vertexIn == u && myedge.vertexOut == v
                    || myedge.vertexOut == u && myedge.vertexIn == v) {
                return true;
            }
        }
        return false;
    }

    /**
     * Permite inserir um novo vértice no grafo.
     *
     * @param elem elemento a inserir no novo vértice
     * @return Vértice inserido.
     * @throws InvalidVertexException
     */
    @Override
    public Vertex<V> insertVertex(V elem) throws InvalidVertexException {
        if (listVertices.containsKey(elem)) {
            throw new InvalidVertexException(elem + "already exists ");
        }
        MyVertex vertex = new MyVertex(elem);
        listVertices.put(elem, vertex);
        return vertex;

    }

    /**
     * Permite inserir uma nova aresta no grafo dados dois vértices terminais e
     * um elemento a inserir na nova aresta.
     *
     * @param u Vértice terminal 1
     * @param v Vértice terminal 2
     * @param elem Elemento a inserir na nova aresta
     * @return Aresta inserida.
     * @throws InvalidVertexException
     */
    @Override
    public Edge<E, V> insertEdge(Vertex<V> u, Vertex<V> v, E elem) throws InvalidVertexException {
        if (!listVertices.containsKey(u.element())) {
            throw new InvalidVertexException(u.element() + "not exists ");
        }
        if (!listVertices.containsKey(v.element())) {
            throw new InvalidVertexException(v.element() + "not exists ");
        }

        MyEdge edge = new MyEdge(elem, u, v);
        // coloca-lo nos vertices.
        checkVertex(u).listaEdges.add(edge);
        checkVertex(v).listaEdges.add(edge);
        nEdges++;
        return edge;
    }

    /**
     * Permite inserir uma nova aresta no grafo dados dois elementos para os
     * vértices terminais e um elemento para a nova aresta.
     *
     * @param elem1 Elemento para o vértice terminal 1
     * @param elem2 Elemento para o vértice terminal 2
     * @param o Elemento a inserir na nova aresta
     * @return Aresta inserida.
     * @throws InvalidVertexException
     */
    @Override
    public Edge<E, V> insertEdge(V elem1, V elem2, E o) throws InvalidVertexException {
        if ((!listVertices.containsKey(elem1)) || (!listVertices.containsKey(elem2))) {
            throw new InvalidVertexException("Invalid Vertex");
        }

        Vertex<V> v1 = listVertices.get(elem1);
        Vertex<V> v2 = listVertices.get(elem2);
        MyEdge e = new MyEdge(o, v1, v2);
        checkVertex(v1).listaEdges.add(e);
        checkVertex(v2).listaEdges.add(e);
        nEdges++;
        return e;
    }

    /**
     * Permite remover determinado vértice do grafo.
     *
     * @param v Vértice a remover
     * @return elemento do vértice removido.
     * @throws InvalidVertexException
     */
    @Override
    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        if (!listVertices.containsValue(v)) {
            throw new InvalidVertexException("not exists ");
        }
        MyVertex vertex = checkVertex(v);
        if (!vertex.listaEdges.isEmpty()) {
            throw new InvalidVertexException(" vertex has incident edges");
        }
        listVertices.remove(v.element());
        return v.element();

    }

    /**
     * Permite remover determinada aresta do grafo.
     *
     * @param e Aresta a remover.
     * @return Elemento da aresta removida.
     * @throws InvalidVertexException
     */
    @Override
    public E removeEdge(Edge<E, V> e) throws InvalidEdgeException {
        if (!existEdge(e)) {
            throw new InvalidEdgeException("not exists ");
        }
        Vertex<V>[] vertices = checkEdge(e).vertices();
        checkVertex(vertices[0]).listaEdges.remove(e);
        checkVertex(vertices[1]).listaEdges.remove(e);
        nEdges--;
        return e.element();

    }

    
  
    
    private class MyVertex implements Vertex<V> , Serializable{

        /**
         * Representa o vértice a inserir no grafo.
         *
         * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
         * @version 1.0 (22/11/2017)
         */
        private V elem;
        private List<Edge<E, V>> listaEdges;

        public MyVertex(V elem) {
            this.elem = elem;
            this.listaEdges = new ArrayList<>();

        }

        @Override
        public V element() throws InvalidVertexException {
            if (elem == null) {
                throw new InvalidVertexException("vertex null");
            }
            return elem;
        }

    }

    private class MyEdge implements Edge<E, V>, Serializable {

        /**
         * Representa o aresta a inserir no grafo.
         *
         * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
         * @version 1.0 (22/11/2017)
         */
        private E elem;
        private Vertex<V> vertexIn, vertexOut;

        public MyEdge(E elem, Vertex<V> vertexIn, Vertex<V> vertexOut) {
            this.elem = elem;
            this.vertexIn = vertexIn;
            this.vertexOut = vertexOut;
        }

        @Override
        public E element() throws InvalidEdgeException {
            if (elem == null) {
                throw new InvalidEdgeException("edge null");
            }
            return elem;
        }

        @Override
        public Vertex<V>[] vertices() {
            Vertex[] vertices = new Vertex[2];
            vertices[0] = vertexIn;
            vertices[1] = vertexOut;
            return vertices;
        }

        /**
         * Permite fazer a comparação com outra aresta.
         *
         * @return 0/-1 caso sejam arestas iguais ou diferentes.
         */
        @Override
        public int compareTo(Edge<Connection, Corner> choosenEdge) {
            if (elem == choosenEdge.element() && vertexIn == choosenEdge.vertices()[0] && vertexOut == choosenEdge.vertices()[1]) {
                return 0;
            }
            return -1;
        }

     
    }

}
