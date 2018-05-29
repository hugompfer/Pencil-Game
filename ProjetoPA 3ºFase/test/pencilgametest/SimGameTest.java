/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pencilgametest;

import java.time.LocalDate;
import pattern.memento.Memento;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.SimGame;
import model.User;
import static org.junit.Assert.*;

/**
 *
 * @author Hugo
 */
public class SimGameTest {

    private SimGame simGame;
    private User p1;
    private User p2;

    public SimGameTest() {

    }

    @Before
    public void setUp() {
        p1 = new User(1, "Hugo", "1234", "hugo@hotmail.com", LocalDate.now());
        p2 = new User(2, "Tiago", "4321", "tiago@hotmail.com", LocalDate.now());
        simGame = new SimGame(p1, p2);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of canGoBack method, of class SimGame.
     */
    @Test
    public void testCanWentBack(){
        simGame.makeAMove(simGame.getBoard().getFigure().edges().iterator().next());
        boolean result = simGame.canGoBack();
        assertEquals(true, result);
        simGame.rollBack();
        simGame.makeAMove(simGame.getBoard().getFigure().edges().iterator().next());
        result = simGame.canGoBack();
        assertEquals(false, result);
    }

    @Test
    public void testRollBack(){
        simGame.makeAMove(simGame.getBoard().getFigure().edges().iterator().next());
        Memento last = simGame.getHistoric().getStack().get(simGame.getHistoric().getStack().size()-1);
        boolean turn = simGame.isFirstPlayerTurn();
        simGame.rollBack();
        boolean turn2 = simGame.isFirstPlayerTurn();      
        assertTrue(turn != turn2);
        assertTrue(simGame.getHistoric().getStack().isEmpty());
    }
    
 

}
