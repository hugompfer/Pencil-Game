/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.animation.TranslateTransition;
import javafx.scene.ImageCursor;
import model.Connection;
import model.Corner;
import model.Game;
import tads.Edge;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import pattern.mvp.presenter.GamePresenter;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

/**
 *
 * @author Hugo
 */
public class GameViewer extends Pane implements Interation {

    private Button btnBackwards;
    private ImageView back;
    private TranslateTransition moveWhoIsPlaying;
    private Label whoIsPlaying;

    public GameViewer() {
        setStyle("-fx-background-image:url('/Images/paper.png');  -fx-opacity: 1; -fx-background-size: cover");
        setupLayout();
        changeCursor();
    }

    private void setupBtnBack() {
        back = new ImageView("/Images/previousUser.png");
        back.setFitHeight(48);
        back.setFitWidth(48);
        
        btnBackwards = new Button("", back);
        btnBackwards.setTranslateX(15);
        btnBackwards.setTranslateY(15);
        btnBackwards.setStyle("-fx-background-color: transparent; -fx-opacity: 0.5;");
    }

    private void setupWhoIsPlayingAnimation() {
        whoIsPlaying = new Label("");
        moveWhoIsPlaying = new TranslateTransition(Duration.millis(1700), whoIsPlaying);
        whoIsPlaying.setTranslateX(-400);
        whoIsPlaying.setTranslateY(25);
        whoIsPlaying.setStyle("   -fx-font-size: 34pt;\n"
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
        setupBtnBack();
        setupWhoIsPlayingAnimation();
        getChildren().addAll(btnBackwards, whoIsPlaying);
    }

    public void setupLayout(Game game) {
        for (Edge<Connection, Corner> c : game.getBoard().getFigure().edges()) {
            Line l = new Line(c.vertices()[0].element().getPosX(), c.vertices()[0].element().getPosY(),
                    c.vertices()[1].element().getPosX(), c.vertices()[1].element().getPosY());

            Circle dot = new Circle(c.vertices()[0].element().getPosX(), c.vertices()[0].element().getPosY(), 7, Color.LAWNGREEN);
            Circle dot2 = new Circle(c.vertices()[1].element().getPosX(), c.vertices()[1].element().getPosY(), 7, Color.LAWNGREEN);
            dot.setStroke(Color.BLACK);
            dot.setStrokeWidth(3);
            dot2.setStroke(Color.BLACK);
            dot2.setStrokeWidth(3);
            l.setStrokeWidth(4);
            l.setStroke(c.element().getColorToPaint());
            getChildren().add(0, l);
            getChildren().add(1, dot);
            getChildren().add(1, dot2);
        }
        
        setupLayout();
    }

    public void showPlayers(String player1, String player2) {
        whoIsPlaying.setTranslateX(105);
        whoIsPlaying.setTranslateY(25);
        whoIsPlaying.setText(player1 + " Vs. " + player2);
    }

    public void showWhoIsPlaying(String player1, String player2) {
        whoIsPlaying.setText(player1 + " Vs. " + player2);
        moveWhoIsPlaying.setToX(105);
        moveWhoIsPlaying.play();
    }

    @Override
    public void showResult(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText("Perdeu");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void setTriggers(GamePresenter presenter) {
        for (Node n : getChildren()) {
            if (n instanceof Line) {
                Line l = (Line) n;
                l.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    DropShadow dp = new DropShadow();
                    dp.setRadius(10.0);
                    dp.setColor(Color.YELLOW);
                    l.setEffect(dp);
                });

                l.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                    l.setEffect(null);
                });

                l.setOnMousePressed(e -> {
                    presenter.doMove(l);
                });
            }
        }
        btnBackwards.setOnAction(e
                -> {
            presenter.back();
        }
        );

    }

    @Override
    public void paintLine(Line line, Color c) {
        line.setStroke(c);
    }

    @Override
    public void makeTriangle(Double[] positions) {
        Polygon triangle = new Polygon();
        triangle.setFill(Color.GRAY);
        triangle.setOpacity(0.5);
        triangle.getPoints().addAll(positions);
        getChildren().add(triangle);
    }

}
