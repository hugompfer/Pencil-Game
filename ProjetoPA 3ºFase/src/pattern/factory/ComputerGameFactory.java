/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.factory;

import enums.Difficulty;
import model.ComputerGame;
import model.ComputerGameEasy;
import model.ComputerGameHard;
import model.Machine;
import model.Player;
import model.User;

/**
 * ComputerGameFactory representa a classe que constrói um determinado tipo de jogo contra o computador.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class ComputerGameFactory {
    /**
     * método criador do jogo de acordo com as especificações.
     *
     * @param p1 jogador 1.
     * @param p2 jogador 2.
     * @param difficulty tipo de dificuldade do jogo.
     * @return Jogo criado.
     */
    public static ComputerGame createGame(Player p1, Player p2, Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return new ComputerGameEasy((User) p1, (Machine) p2);
            case HARD:
                return new ComputerGameHard((User) p1, (Machine) p2);
            default:
                throw new IllegalArgumentException("Tipo não reconhecido");
        }
    }
}
