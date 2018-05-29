/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tadgraph;

import tads.Vertex;
import tads.MyGraph;
import tads.InvalidVertexException;
import tads.Edge;
import tads.InvalidEdgeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import model.Connection;
import model.Corner;

/**
 *
 * @author tinet
 */
public class MyGraphTest {

    private MyGraph<Integer, String> graph;

    public MyGraphTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        graph = new MyGraph();

        graph.insertVertex(1);
        graph.insertVertex(2);
        graph.insertVertex(3);
        graph.insertVertex(4);
        graph.insertVertex(5);
        graph.insertVertex(6);
        graph.insertVertex(7);
        graph.insertVertex(8);

        graph.insertEdge(2, 6, "Relação1");
        graph.insertEdge(2, 3, "Relação2");
        graph.insertEdge(3, 4, "Relação3");
        graph.insertEdge(4, 5, "Relação4");
        graph.insertEdge(5, 6, "Relação5");
        graph.insertEdge(6, 7, "Relação6");
        graph.insertEdge(7, 8, "Relação7");

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of numVertices method, of class MyGraph.
     */
    @Test
    public void testNumVertices_0() {
        MyGraph graphTest = new MyGraph();
        assertEquals("Existem 0 vértices", 0, graphTest.numVertices());
    }

    @Test
    public void testNumEdges_0() {
        MyGraph graphTest = new MyGraph();
        assertEquals("Existem 0 arestas", 0, graphTest.numEdges());
    }

    @Test
    public void testNumVertices_8() {
        assertEquals("Existem 8 vértices", 8, graph.numVertices());
    }

    @Test
    public void testNumVertices_insert_9() {
        graph.insertVertex(9);
        assertEquals("Existem 9 vértices", 9, graph.numVertices());
    }

    @Test
    public void testNumVertices_remove_7() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();
        graph.removeVertex(p1);
        assertEquals("Existem 7 vértices", 7, graph.numVertices());
    }

    @Test(expected = InvalidVertexException.class)
    public void testNumVertices_remove_null() {
        graph.removeVertex(null);
    }

    @Test(expected = InvalidVertexException.class)
    public void testNumVertices_remove_IncidentEdgesException() {
        //só para remover o segundo
        for (Vertex<Integer> vim : graph.vertices()) {
            if (vim.element() == 2) {
                graph.removeVertex(vim);
            }
        }
    }

    @Test
    public void testNumEdges_7() {
        assertEquals("Existem 7 arestas", 7, graph.numEdges());
    }

    @Test
    public void testNumEdges_remove_6() {
        Edge<String, Integer> p1 = (Edge<String, Integer>) graph.edges().iterator().next();
        graph.removeEdge(p1);
        assertEquals("Existem 6 arestas", 6, graph.numEdges());
    }

    @Test(expected = InvalidEdgeException.class)
    public void testNumEdges_remove_null() {
        graph.removeEdge(null);
    }

    @Test
    public void testNumEdges_insertByElements_8() {
        graph.insertEdge(4, 8, "Relação");
        assertEquals("Existem 8 arestas", 8, graph.numEdges());
    }

    @Test(expected = InvalidVertexException.class)
    public void testNumEdges_insertByVertexes_null() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();
        graph.removeVertex(p1);
        graph.insertEdge(p1, null, "Relação");
    }

    @Test(expected = InvalidVertexException.class)
    public void testNumEdges_insertByElements_null() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();
        graph.removeVertex(p1); //tem elemento 1
        graph.insertEdge(1, 5, "Relação");
    }

    @Test
    public void testNumEdges_insertByVertexes_8() {
        Vertex<Integer> vertex1 = null;
        Vertex<Integer> vertex2 = null;
        for (Vertex<Integer> vim : graph.vertices()) {
            if (vim.element() == 3) {
                vertex1 = vim;
            }
            if (vim.element() == 8) {
                vertex2 = vim;
            }

        }

        graph.insertEdge(vertex1, vertex2, "Relação83");
        assertEquals("Existem 8 arestas", 8, graph.numEdges());
    }

    @Test
    public void testVerticesList() {
        // List<Vertex<Integer>> expected = (List<Vertex<Integer>>) graph.vertices();
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        for (Vertex<Integer> vim : graph.vertices()) {
            assertTrue(expected.contains(vim.element()));
        }
    }

    /**
     * Test of edges method, of class MyGraph.
     */
    @Test
    public void testEdgesList() {
        List<String> expected = Arrays.asList("Relação1", "Relação2", "Relação3", "Relação4",
                "Relação5", "Relação6", "Relação7");
        for (Edge<String, Integer> ed : graph.edges()) {
            assertTrue(expected.contains(ed.element()));
        }
    }

    /**
     * Test of replace method, of class MyGraph.
     */
    @Test
    public void testReplace_Vertex_1() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();
        int i = graph.replace(p1, 10);
        assertEquals("O valor antigo era 1", 1, i);
    }

    @Test
    public void testReplace_Vertex_155() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();
        graph.replace(p1, 155);
        int i = graph.replace(p1, 23);
        assertEquals("O valor antigo era 155", 155, i);
    }

    @Test(expected = InvalidVertexException.class)
    public void testReplace_Vertex_notFound() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();
        graph.removeVertex(p1);
        graph.replace(p1, 155);
    }

    /**
     * Test of replace method, of class MyGraph.
     */
    @Test
    public void testReplace_Edge_Relacao1() {

        String rel = "";
        for (Edge<String, Integer> ed : graph.edges()) {
            if ("Relação1".equals(ed.element())) {
                rel = graph.replace(ed, "Amizade");
            }
        }
        assertEquals(rel, "Relação1");
    }

    @Test
    public void testReplace_Edge_RelacaoNamoro() {
        Edge<String, Integer> p1 = (Edge<String, Integer>) graph.edges().iterator().next();
        graph.replace(p1, "Namoro");
        String rel = graph.replace(p1, "Familiar");
        assertEquals(rel, "Namoro");
    }

    @Test(expected = InvalidEdgeException.class)
    public void testReplace_Edge_notFound() {
        Edge<String, Integer> p1 = (Edge<String, Integer>) graph.edges().iterator().next();
        graph.removeEdge(p1);
        graph.replace(p1, "Relação Complicada");
    }

    /**
     * Test of incidentEdges method, of class MyGraph.
     */
    @Test
    public void testIncidentEdges_Vertex() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();

        ArrayList<Edge<String, Integer>> expected = new ArrayList<>();

        for (Edge<String, Integer> ed : graph.edges()) {
            if (ed.vertices()[0] == p1 || ed.vertices()[1] == p1) {
                expected.add(ed);
            }
        }
        assertEquals(expected, graph.incidentEdges(p1));
    }

    @Test(expected = InvalidVertexException.class)
    public void testIncidentEdges_Vertex_null() {
        Vertex<Integer> p1 = (Vertex<Integer>) graph.vertices().iterator().next();
        graph.removeVertex(p1);
        Iterable<Edge<String, Integer>> list = graph.incidentEdges(p1);
    }

    @Test
    public void testIncidentEdges_Edge() {

        Edge<String, Integer> mainEdge = null;
        HashSet<Edge<String, Integer>> expected = new HashSet<>();

        for (Edge<String, Integer> ed : graph.edges()) {
            if (ed.element().equalsIgnoreCase("Relação4")) {
                mainEdge = ed;
            }
            if (ed.element().equalsIgnoreCase("Relação3") || ed.element().equalsIgnoreCase("Relação5")) {
                expected.add(ed);
            }
        }

        assertTrue(expected.containsAll((HashSet) graph.incidentEdges(mainEdge)));

    }

    @Test(expected = InvalidVertexException.class)
    public void testIncidentEdges_Edge_null() {
        Edge<String, Integer> ed = graph.edges().iterator().next();
        if (ed.vertices()[0] != null) {
            graph.removeVertex(ed.vertices()[0]);
        }
        if (ed.vertices()[1] != null) {
            graph.removeVertex(ed.vertices()[1]);
        }

        graph.incidentEdges(ed);
    }

    /**
     * Test of opposite method, of class MyGraph.
     */
    @Test
    public void testOpposite() {
        Vertex<Integer> vertex = null;
        for (Vertex<Integer> vim : graph.vertices()) {
            if (vim.element() == 7) {
                vertex = vim;
            }
        }
        Edge<String, Integer> edge = null;
        for (Edge<String, Integer> ed : graph.edges()) {
            if (ed.element().equalsIgnoreCase("Relação6")) {
                edge = ed;
            }
        }

        Vertex<Integer> opposite = null;
        for (Vertex<Integer> vim : graph.vertices()) {
            if (vim.element() == 6) {
                opposite = vim;
            }
        }

        assertEquals("Deve ser o mesmo vértice", opposite, graph.opposite(vertex, edge));
    }

    /**
     * Test of areAdjacent method, of class MyGraph.
     */
    @Test
    public void testAreAdjacent_true() {
        Vertex<Integer> vertex1 = null;
        Vertex<Integer> vertex2 = null;
        for (Vertex<Integer> vim : graph.vertices()) {
            if (vim.element() == 2) {
                vertex1 = vim;
            }
            if (vim.element() == 3) {
                vertex2 = vim;
            }
        }
        assertEquals("Propiedade verdadeira", true, graph.areAdjacent(vertex1, vertex2));
    }

    @Test
    public void testAreAdjacent_false() {
        Vertex<Integer> vertex1 = null;
        Vertex<Integer> vertex2 = null;
        for (Vertex<Integer> vim : graph.vertices()) {
            if (vim.element() == 1) {
                vertex1 = vim;
            }
            if (vim.element() == 8) {
                vertex2 = vim;
            }
        }
        assertEquals("Propiedade falsa", false, graph.areAdjacent(vertex1, vertex2));
    }

    @Test
    public void testVertex_element() {
        graph.insertVertex(567);
        Vertex<Integer> vertex1 = null;
        for (Vertex<Integer> vim : graph.vertices()) {
            if (vim.element() == 567) {
                vertex1 = vim;
            }
        }
        assertEquals("Deve ser o mesmo valor", 567, (int) vertex1.element());
    }

    @Test(expected = InvalidVertexException.class)
    public void testVertexElement_null() {
        MyGraph<Integer, String> graphTest = new MyGraph();
        graphTest.insertVertex(null);
        for (Vertex<Integer> vim : graphTest.vertices()) {
            vim.element();
        }
    }

    @Test
    public void testEdge_element() {
        graph.insertVertex(25);
        graph.insertVertex(30);
        graph.insertEdge(25, 30, "Teste");
        Edge<String, Integer> edge = null;
        for (Edge<String, Integer> ed : graph.edges()) {
            if (ed.element().equalsIgnoreCase("Teste")) {
                edge = ed;
            }
        }
        assertEquals("Deve ser o mesmo valor", "Teste", (String) edge.element());
    }

    @Test(expected = InvalidEdgeException.class)
    public void testEdgeElement_null() {
        MyGraph<Integer, String> graphTest = new MyGraph();
        graphTest.insertVertex(25);
        graphTest.insertVertex(30);
        graphTest.insertEdge(25, 30, null);
        for (Edge<String, Integer> ed : graphTest.edges()) {
            ed.element();
        }
    }

    @Test
    public void testEdgeClass_vertices() {
        MyGraph<Integer, String> graphTest = new MyGraph();
        graphTest.insertVertex(40);
        graphTest.insertVertex(50);
        Vertex<Integer>[] vertices = new Vertex[2];
        for (Vertex<Integer> vim : graphTest.vertices()) {
            if (vim.element() == 40) {
                vertices[0] = vim;
            }
            if (vim.element() == 50) {
                vertices[1] = vim;
            }
        }
        Edge<String, Integer> edge1 = null;
        graphTest.insertEdge(40, 50, "Teste");
        for (Edge<String, Integer> ed : graphTest.edges()) {
            edge1 = ed;
        }
        Assert.assertArrayEquals(vertices,graphTest.insertEdge(40, 50, "teste").vertices());
    }

    @Test
    public void testEdgeClass_compare_differentElement() {
        MyGraph<Corner, Connection> graphTest = new MyGraph();
        Connection con1 = new Connection();
        Corner cor1 = new Corner(20, 40);
        Corner cor2 = new Corner(30, 50);

        graphTest.insertVertex(cor1);
        graphTest.insertVertex(cor2);
        graphTest.insertEdge(cor1, cor2, con1);

        Connection con2 = new Connection();
        Corner cor3 = new Corner(60, 70);
        Corner cor4 = new Corner(80, 90);

        graphTest.insertVertex(cor3);
        graphTest.insertVertex(cor4);
        graphTest.insertEdge(cor3, cor4, con2);

        Edge<Connection, Corner> edge1 = null;
        Edge<Connection, Corner> edge2 = null;

        for (Edge<Connection, Corner> ed : graphTest.edges()) {
            if (ed.element() == con1) {
                edge1 = ed;
            }
            if (ed.element() == con2) {
                edge2 = ed;
            }
        }
        assertEquals("são diferentes", -1, edge1.compareTo(edge2));
    }

    @Test
    public void testEdgeClass_compare_differentVertices() {

        //por mesmo con
        //com dif corners pontas
        MyGraph<Corner, Connection> graphTest = new MyGraph();
        Connection con1 = new Connection();
        Corner cor1 = new Corner(20, 40);
        Corner cor2 = new Corner(30, 50);

        graphTest.insertVertex(cor1);
        graphTest.insertVertex(cor2);
        graphTest.insertEdge(cor1, cor2, con1);

        Connection con2 = new Connection();
        Corner cor3 = new Corner(60, 70);
        Corner cor4 = new Corner(80, 90);

        graphTest.insertVertex(cor3);
        graphTest.insertVertex(cor4);
        graphTest.insertEdge(cor3, cor4, con2);

        Edge<Connection, Corner> edge1 = null;
        Edge<Connection, Corner> edge2 = null;

        for (Edge<Connection, Corner> ed : graphTest.edges()) {
            if (ed.element() == con1) {
                edge1 = ed;
            }
            if (ed.element() == con2) {
                graphTest.replace(ed, con1);
                edge2 = ed;
            }
            //ponho o mesmo element e mantenho os vertives distantes

        }
        assertEquals("são diferentes", -1, edge1.compareTo(edge2));
    }

    @Test
    public void testEdgeClass_compare_equal() {
        MyGraph<Corner, Connection> graphTest = new MyGraph();
        Connection con1 = new Connection();
        Corner cor1 = new Corner(20, 40);
        Corner cor2 = new Corner(30, 50);

        graphTest.insertVertex(cor1);
        graphTest.insertVertex(cor2);
        graphTest.insertEdge(cor1, cor2, con1);

        Edge<Connection, Corner> edge1 = null;
        Edge<Connection, Corner> edge2 = null;

        for (Edge<Connection, Corner> ed : graphTest.edges()) {
            edge1 = ed;
            edge2 = ed;
        }
        assertEquals("são iguais", 0, edge1.compareTo(edge2));
    }
}
