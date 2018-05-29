/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.factory;

import enums.Difficulty;
import model.Game;
import model.Player;
import model.SimGame;
import model.User;

/**
 * GameFactory representa a classe que constrói um determinado tipo de jogo.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class GameFactory {

    /**
     * método criador do jogo de acordo com as especificações.
     *
     * @param type tipo de jogo a criar.
     * @param p1 jogador 1.
     * @param p2 jogador 2.
     * @param difficulty tipo de dificuldade do jogo.
     * @return Jogo criado.
     */
    public static Game createGame(String type, Player p1, Player p2, Difficulty difficulty) {
        switch (type) {
            case "SimGame":
                return new SimGame((User) p1, (User) p2);
            case "ComputerGame":
                return ComputerGameFactory.createGame(p1, p2, difficulty);
            default:
                throw new IllegalArgumentException("Tipo não reconhecido");
        }
    }
}
