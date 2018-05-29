/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.presenter;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import pattern.observer.FinishGame;
import pattern.observer.StartGame;
import model.ComputerGame;
import model.Connection;
import model.Corner;
import model.Game;
import model.SimGame;
import tads.Edge;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;
import model.Machine;
import model.PlayersManager;
import model.User;
import pattern.mvp.view.GameViewer;
import pattern.mvp.view.SimGameViewer;
import pattern.mvp.view.TabsViewer;
import pattern.mvp.view.VictoryViewer;

/**
 * GamePresenter representa a classe presenter que faz interação entre o menu de
 * jogo e as implementações.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class GamePresenter {

    private Game model;
    private GameViewer view;
    private PlayersManager playersManager;

    public GamePresenter(Game g, GameViewer gi, PlayersManager playersManager) {
        model = g;
        view = gi;
        this.playersManager = playersManager;
        model.addObserver(new StartGame());
        model.addObserver(new FinishGame(playersManager));
        model.start();
        view.setupLayout(model);
        showPlayers();
        view.setTriggers(this);
    }

    private void showPlayers() {
        String player1 = getPlayersNames()[0];
        String player2 = getPlayersNames()[1];
        view.showWhoIsPlaying(player1, player2);

    }

    private String[] getPlayersNames() {
        String[] lista = new String[2];
        lista[0] = model.getPlayers()[0].getIdentification();;
        lista[1] = model.getPlayers()[1].getIdentification();
        return lista;
    }

    public Game getGame() {
        return model;
    }

    /**
     * Permite efetuar uma jogada.
     *
     * @param l aresta selecionada.
     */
    public void doMove(Line l) {
        Edge<Connection, Corner> edge = getEdge(l);
        move(edge);
    }

    private Double[] getPositions(List<Corner> list) {
        Double[] positions = new Double[6];
        int x = 0;
        for (int i = 0; i < 6; i += 2) {
            Corner c = list.get(x++);
            positions[i] = c.getPosX();
            positions[i + 1] = c.getPosY();
        }
        return positions;
    }

    private void move(Edge<Connection, Corner> edge) {
        if (model.makeAMove(edge)) {
            view.paintLine(findLine(edge), edge.element().getColorToPaint());
            if (model.isFinished()) {
                view.makeTriangle(getPositions(model.getTriangle()));
                Timeline timeline1 = new Timeline(new KeyFrame(
                        Duration.millis(1900),
                        time1 -> playVictoryScene()));
                timeline1.play();
            }
        }

        if (!model.isFirstPlayerTurn()) {
            makeAnotherMove();
        }
        checkBtn();
    }

    private void makeAnotherMove() {
        if (model instanceof ComputerGame && !model.isFinished()) {
            waitMoment(false);
        }
    }

    /**
     * Permite desfazer a jogada anterior.
     */
    public void undo() {
        if (model instanceof SimGame) {
            ((SimGame) model).rollBack();
            ((SimGameViewer) view).erase();
            waitMoment(true);

        }
    }

    private void waitMoment(boolean back) {
        Timer delay = new Timer();
        GamePresenter p = this;
        delay.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (back) {
                        ((SimGameViewer) view).redraw(model);
                        view.setTriggers(p);
                        view.showPlayers(getPlayersNames()[0], getPlayersNames()[1]);
                    } else {
                        Edge<Connection, Corner> edge = ((ComputerGame) model).choose();
                        move(edge);
                    }
                    delay.cancel();
                });
            }

        }, back ? 2200 : new Random().nextInt(1500));
    }

    /**
     * Permite regressar ao menu selecionador de modo de jogo.
     */
    public void back() {
        TabsViewer root = new TabsViewer(3, playersManager);
        Scene scene = new Scene(root, 600, 550);
        ((Stage) view.getScene().getWindow()).setScene(scene);
    }

    private void checkBtn() {
        if (model instanceof SimGame) {
            if (!((SimGame) model).canGoBack()) {
                ((SimGameViewer) view).disableButton();
            } else {
                ((SimGameViewer) view).enableButton();
            }
        }
    }

    private Line findLine(Edge<Connection, Corner> edge) {
        for (Node n : view.getChildren()) {
            if (n instanceof Line) {
                Line l = (Line) n;
                if (isSameCoordenates(l, edge)) {
                    return l;
                }
            }
        }
        return null;
    }

    private boolean isSameCoordenates(Line l, Edge<Connection, Corner> edge) {
        return edge.vertices()[0].element().equals(l.getStartX(), l.getStartY())
                && edge.vertices()[1].element().equals(l.getEndX(), l.getEndY());
    }

    public Edge<Connection, Corner> getEdge(Line l) {
        for (Edge<Connection, Corner> e : model.getBoard().edges()) {
            if (isSameCoordenates(l, e)) {
                return e;
            }
        }
        return null;
    }

    private void playVictoryScene() {
        String name= model.getWinnerPlayer().getIdentification();
        VictoryViewer root = new VictoryViewer(playersManager, name);
        Scene scene = new Scene(root, 600, 550);
        ((Stage) view.getScene().getWindow()).setScene(scene);
    }

}
