/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pencilgametest;

import tads.Edge;
import java.time.LocalDate;
import model.Connection;
import org.junit.Before;
import org.junit.Test;
import model.Corner;
import model.Game;
import model.SimGame;
import model.User;
import static org.junit.Assert.*;

/**
 *
 * @author Hugo
 */
public class GameTest {

    private Game game;
    private User p1;
    private User p2;
    private Corner c = new Corner(199.99999999999991, 101.7949192431123);
    private Corner c2 = new Corner(399.9999999999999, 101.79491924311219);
    private Corner c3 = new Corner(399.9999999999999, 101.79491924311219);
    private Corner c4 = new Corner(500.0, 275.0);
    private Corner c5 = new Corner(199.99999999999991, 101.7949192431123);
    private Corner c6 = new Corner(500.0, 275.0);
    private Corner c7 = new Corner(100.0, 275.0);
    private Corner c8 = new Corner(200.00000000000006, 448.20508075688775);
    private Corner c9 = new Corner(400.0, 448.2050807568877);
    private Corner c10 = new Corner(200.00000000000006, 448.20508075688775);

    public GameTest() {
        c = new Corner(199.99999999999991, 101.7949192431123);
        c2 = new Corner(399.9999999999999, 101.79491924311219);
        c3 = new Corner(399.9999999999999, 101.79491924311219);
        c4 = new Corner(500.0, 275.0);
        c5 = new Corner(199.99999999999991, 101.7949192431123);
        c6 = new Corner(500.0, 275.0);
        c7 = new Corner(100.0, 275.0);
        c8 = new Corner(200.00000000000006, 448.20508075688775);
        c9 = new Corner(400.0, 448.2050807568877);
        c10 = new Corner(200.00000000000006, 448.20508075688775);
    }

    @Before
    public void setUp() {
        p1 = new User(1, "Hugo", "1234", "hugo@hotmail.com", LocalDate.now());
        p2 = new User(2, "Tiago", "4321", "tiago@hotmail.com", LocalDate.now());
        game = new SimGame(p1, p2);
    }

    /**
     * Test of start method, of class Game.
     */
    @Test
    public void testStart() {
        assertEquals(0, game.getGameStart());
        game.start();
        assertTrue(game.getGameStart() != 0);
    }

    /**
     * Test of finish method, of class Game.
     */
    @Test
    public void testFinish() {
        game.start();
        assertEquals(0, game.getGameFinish());
        game.finish();
        assertTrue(game.getGameFinish() != 0);
    }

    /**
     * Test of isFinished method, of class Game.
     */
    @Test
    public void testIsFinished() {
        game.start();
        assertEquals(0, game.getGameFinish());
        game.finish();
        assertTrue(game.isFinished());
    }

    /**
     * Test of getGameTime method, of class Game.
     */
    @Test
    public void testGetGameTime() {
        game.start();
        assertEquals(0, game.getGameFinish());
        game.finish();
        assertTrue(game.getGameTime() == 0);
    }

    /**
     * Test of getLoserPlayer method, of class Game.
     */
    @Test
    public void testGetLoserPlayer() {
        game.makeAMove(getEdge(c, c2));
        game.makeAMove(getEdge(c7, c8));
        game.makeAMove(getEdge(c3, c4));
        game.makeAMove(getEdge(c9, c10));
        boolean result = game.makeAMove(getEdge(c5, c6));
        assertEquals(p1.getId(), game.getLoserPlayer().getId());
    }

    /**
     * Test of getWinnerPlayer method, of class Game.
     */
    @Test
    public void testGetWinnerPlayer() {

        game.makeAMove(getEdge(c, c2));
        game.makeAMove(getEdge(c7, c8));
        game.makeAMove(getEdge(c3, c4));
        game.makeAMove(getEdge(c9, c10));
        game.makeAMove(getEdge(c5, c6));
        assertEquals(p2.getId(), game.getWinnerPlayer().getId());
    }

    
     private Edge<Connection, Corner> getEdge(Corner elem1, Corner elem2) {
        for (Edge<Connection, Corner> e : game.getBoard().getFigure().edges()) {
            if (e.vertices()[0].element().getPosX() == elem1.getPosX() && e.vertices()[0].element().getPosY() == elem1.getPosY()
                    && e.vertices()[1].element().getPosX() == elem2.getPosX() && e.vertices()[1].element().getPosY() == elem2.getPosY()
                    || e.vertices()[1].element().getPosX() == elem1.getPosX() && e.vertices()[1].element().getPosY() == elem1.getPosY()
                    && e.vertices()[0].element().getPosX() == elem2.getPosX() && e.vertices()[0].element().getPosY() == elem2.getPosY()) {
                return e;
            }
        }
        return null;
    }
    
    
    /**
     * Test of makeAMove method, of class Game.
     */
    @Test
    public void testMakeAMove() {
        Edge e = game.getBoard().getFigure().edges().iterator().next();
        boolean result;
        assertTrue(!game.isFinished());
        assertTrue(game.getHistoric().getStack().isEmpty());

        game.makeAMove(getEdge(c, c2));
        game.makeAMove(getEdge(c7, c8));
        game.makeAMove(getEdge(c3, c4));
        game.makeAMove(getEdge(c9, c10));
        result = game.makeAMove(getEdge(c, c2));
        assertTrue(!result);
        result = game.makeAMove(getEdge(c5, c6));
        assertTrue(result);
        result = game.makeAMove(getEdge(c9, c10));
        assertTrue(!result);

    }

    /**
     * Test of checkIfLost method, of class Game.
     */
    @Test
    public void testCheckIfLost() {

        game.makeAMove(getEdge(c, c2));
        assertTrue(!game.isFinished());
        game.makeAMove(getEdge(c7, c8));
        assertTrue(!game.isFinished());
        game.makeAMove(getEdge(c3, c4));
        assertTrue(!game.isFinished());
        game.makeAMove(getEdge(c9, c10));
        assertTrue(!game.isFinished());
        game.makeAMove(getEdge(c5, c6));
        assertTrue(game.isFinished());

    }

    /**
     * Test of addShift method, of class Game.
     *
     * @Test public void testAddShift() {
     * game.makeAMove(game.getBoard().getFigure().edges().iterator().next());
     * assertTrue(game.getHistoric().size() > 0);
     *
     * }
     */
    
    
  

}
