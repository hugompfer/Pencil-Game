/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.PlayersManager;

/**
 *
 * @author Tiago
 */
public class VictoryViewer extends Pane {

    private final PlayersManager playersManager;
    private Timeline timeline1;
    private Timeline timeline2;
    private Timeline timeline3;
    private Timeline timeline4;
    private TranslateTransition confeteMove;
    private TranslateTransition trophyMove;
    private TranslateTransition winnerMove;
    private ImageView confete;
    private ImageView trophy;
    private Label winner;

    public VictoryViewer(PlayersManager playersManager, String name) {
        this.playersManager = playersManager;
        setStyle("-fx-background-image:url('/Images/mount.png');  -fx-opacity: 1; -fx-background-size: 650 560");
        confete = new ImageView("/Images/confete.gif");
        trophy = new ImageView("/Images/trophy.png");
        winner = new Label(checkName(name));

        setupLayout();
        initializeTimers();
        setupTranslations();
        changeCursor();
    }

    private String checkName(String name) {
        if (name.lastIndexOf("") > 13) {
            return "O vencedor foi\n" + name.substring(0, 13);
        } else {
            return "O vencedor foi\n" + "   " + name;
        }
    }

    private void setupTranslations() {
        confeteMove = new TranslateTransition(Duration.millis(4400), confete);
        confeteMove.setToY(0);

        trophyMove = new TranslateTransition(Duration.millis(800), trophy);
        trophyMove.setToX(136);

        winnerMove = new TranslateTransition(Duration.millis(800), winner);
        winnerMove.setToX(126);
    }

    private void initializeTimers() {
        timeline1 = new Timeline(new KeyFrame(
                Duration.millis(7000),
                time1 -> returnToMenu()));
        timeline1.play();

        timeline2 = new Timeline(new KeyFrame(
                Duration.millis(50),
                time1 -> confeteMove.play()));
        timeline2.play();

        timeline3 = new Timeline(new KeyFrame(
                Duration.millis(2000),
                time1 -> trophyMove.play()));
        timeline3.play();

        timeline4 = new Timeline(new KeyFrame(
                Duration.millis(2700),
                time1 -> winnerMove.play()));
        timeline4.play();
    }

    private void setupTrophy() {
        trophy.setFitHeight(320);
        trophy.setFitWidth(320);
        trophy.setTranslateX(-320);
        trophy.setTranslateY(95);
    }

    private void setupConfete() {
        confete.setFitHeight(550);
        confete.setFitWidth(600);
        confete.setTranslateX(0);
        confete.setTranslateY(-550);
    }

    private void setupWinner() {
        winner.setTranslateX(-420);
        winner.setTranslateY(260);
        winner.setStyle(" -fx-font-size: 58pt;\n"
                + "    -fx-font-family: \"Mistral\";\n"
                + "    -fx-text-fill: white;\n"
                + "    -fx-effect: dropshadow(three-pass-box, black, 3, 0.8, 0, 0);");
    }

    private void changeCursor() {
        Platform.runLater(() -> {
            Image image = new Image("/images/mouse1.png"); //pass in the image path
            getScene().setCursor(new ImageCursor(image));
        });
    }

    private void setupLayout() {
        setupConfete();
        setupWinner();
        setupTrophy();

        getChildren().addAll(confete, trophy, winner);
    }

    private void returnToMenu() {
        TabsViewer root = new TabsViewer(3, playersManager);

        Scene scene = new Scene(root, 600, 560);

        Image image = new Image("/images/mouse1.png");  //pass in the image path
        scene.setCursor(new ImageCursor(image));

        ((Stage) getScene().getWindow()).setScene(scene);
    }

}
