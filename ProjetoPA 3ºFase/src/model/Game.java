/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import enums.Colour;
import java.io.Serializable;
import tads.Edge;
import java.util.List;
import java.util.Observable;
import pattern.memento.MementoCareTaker;


/**
 * Game representa uma partida 2 jogadores.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public abstract class Game extends Observable implements Serializable {

    private long gameStart;
    private long gameFinish;
    private final Board board;
    private final Player[] players;
    private MementoCareTaker historic;
    private boolean firstPlayerTurn;

    /**
     * Cria um novo jogo com os respetivos jogadores e identificador.
     *
     * @param p1 Jogador 1.
     * @param p2 Jogador 2.
     */
    public Game(Player p1, Player p2) {
        board = new Board();
        players = new Player[]{p1, p2};
        historic = new MementoCareTaker();
        this.gameStart = 0;
        this.gameFinish = 0;
        firstPlayerTurn = true;

    }

    /**
     * Permite obter o momento detalhado do inicio do jogo.
     *
     * @return tempo detalhado.
     */
    public long getGameStart() {
        return gameStart;
    }

    /**
     * Permite obter o momento detalhado de término do jogo.
     *
     * @return tempo detalhado.
     */
    public long getGameFinish() {
        return gameFinish;
    }

    /**
     * Permite saber se é a vez do primeiro jogador a efetuar uma jogada
     *
     * @return true se for/false o contrario
     */
    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    /**
     * Permite mudar a vez de jogada dos jogadores
     *
     */
    public void changeTurn() {
        this.firstPlayerTurn = !firstPlayerTurn;
    }

    /**
     * Permite saber o jogador atual da jogada
     *
     * @return jogador
     */
    public Player getPlayerTurn() {
        return firstPlayerTurn ? players[0] : players[1];
    }

    public Player getOtherPlayerTurn() {
        return !firstPlayerTurn ? players[0] : players[1];
    }

    /**
     * Permite iniciar um jogo definindo a seu momento de inicio como o momento
     * atual.
     */
    public void start() {
        this.gameStart = System.currentTimeMillis();
        setChanged();
        notifyObservers("Start");
    }

    /**
     * Permite terminar um jogo definindo a seu momento de término como o
     * momento atual.
     *
     */
    public void finish() {
        this.gameFinish = System.currentTimeMillis();
        setChanged();
        notifyObservers("Finish");
    }

    /**
     * Permite saber se um jogo se encontra finalizado.
     *
     * @return true/false caso esteja ou não terminado.
     */
    public boolean isFinished() {
        return gameFinish != 0;
    }

    /**
     * Permite obter o tempo de duração do jogo e segundos.
     *
     * @return Tempo de duração do jogo.
     */
    public int getGameTime() {
        long diffMs = gameFinish - gameStart;
        long diffSec = diffMs / 1000;
        long sec = diffSec*60 / 60;
        return (int) sec;
    }

    /**
     * Permite obter o campo/hexágono do jogo.
     *
     * @return Board do jogo.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Permite obter a lista de jogadores que participam no jogo.
     *
     * @return Lista de jogadores.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Permite obter o jogador derrotado.
     *
     * @return Player derrotado ou null caso o jogo ainda não tenha terminado.
     */
    public Player getLoserPlayer() {
        if (isFinished()) {
            return getPlayerTurn();
        }
        return null;
    }

    /**
     * Permite obter o jogador que venceu o jogo.
     *
     * @return Player vencedor ou null caso o jogo ainda não tenha terminado.
     */
    public Player getWinnerPlayer() {
        if (isFinished()) {
            return getOtherPlayerTurn();
        }
        return null;
    }

    //ddevolve a cor a pintar
    private Colour getColorTurn() {
        if (firstPlayerTurn) {
            return Colour.RED;
        }
        return Colour.BLUE;
    }

    /**
     * Permite efetuar uma jogada e saber se foi efetuada com sucesso
     * verificando se com a mesma o jogo terminou
     *
     * @param edge Aresta selecionada.
     * @return true/false caso a continue ou não.
     */
    public boolean makeAMove(Edge<Connection, Corner> edge) {
        if (!isFinished()) {
            if (edge.element().getSeletorUser() == null) {
                historic.saveState(board);
                Player p = getPlayerTurn();
                Connection c = edge.element();
                c.setSelectorUser(p);
                c.setColor(getColorTurn());
                if (lost(edge, p)) {
                    finish();
                } else {
                    changeTurn();
                }
                return true;

            }
        }
        return false;
    }

    /**
     * Permite verificar se a escolha do jogador o levou ou não a perder o jogo.
     *
     * @param edge Aresta selecionada.
     * @param player Jogador a jogar.
     * @return true/false caso o jogador tenha ou não perdido o jogo.
     */
    public boolean lost(Edge<Connection, Corner> edge, Player player) {
        return getBoard().hasTriangule(edge, player);
    }

    /**
     * Permite obter a lista das rondas/jogadas efetuadas ao longo do decorrer
     * do jogo.
     *
     * @return Lista de jogadas.
     */
    public MementoCareTaker getHistoric() {
        return historic;
    }
    
    
    /**
     * Permite receber a lista de posiçoes de triangulos
     *
     * @return Lista de de posiçoes do triangulo.
     */
    public List<Corner> getTriangle(){
        return board.getTriangle();
    }

}
