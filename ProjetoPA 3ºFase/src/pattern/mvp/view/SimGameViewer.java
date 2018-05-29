/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.Game;
import pattern.mvp.presenter.GamePresenter;

/**
 *
 * @author hugob
 */
public class SimGameViewer extends GameViewer implements SimGameInteration {

    private Button btnBack;
    private ImageView rubber;
    private TranslateTransition rubberBackToPlace;
    private TranslateTransition rubberMovement;
    private Timeline timeline1;

    public SimGameViewer() {
        super();

        setupLayout();
    }

    private void setupBackButton() {
        rubber = new ImageView("/Images/rubber.png");
        rubber.setFitHeight(170);
        rubber.setFitWidth(170);

        btnBack = new Button("", rubber);
        btnBack.setTranslateX(415);
        btnBack.setTranslateY(385);
        //btnBack.toFront();
        btnBack.setStyle("-fx-background-color: transparent;");
        disableButton();

        getChildren().add(btnBack);
    }

    private void setupRubberMovement() {
        rubberMovement = new TranslateTransition(Duration.millis(500), btnBack);
        rubberMovement.setToX(20);
        rubberMovement.setToY(20);

        rubberBackToPlace = new TranslateTransition(Duration.millis(500), btnBack);
        rubberBackToPlace.setToX(415);
        rubberBackToPlace.setToY(385);

        timeline1 = new Timeline(new KeyFrame(
                Duration.millis(1200),
                time1 -> rubberBackToPlace.play()));
    }

    private void setupLayout() {
        setupBackButton();
        setupRubberMovement();
    }

    @Override
    public void setTriggers(GamePresenter presenter) {
        super.setTriggers(presenter);
        btnBack.setOnAction(e
                -> {
            presenter.undo();
        }
        );
    }

    public void redraw(Game game) {
        super.getChildren().clear();
        setupLayout(game);
        setupLayout();
    }

    public void erase() {
        //timeline para ir para o sitio dela la em cima
        rubberMovement.play();
        createTransitions();
    }

    private void createTransitions() {
        TranslateTransition tt
                = new TranslateTransition(Duration.millis(300), btnBack);
        tt.setFromX(20);
        tt.setFromY(20);
        tt.setToX(5);
        tt.setToY(80);

        TranslateTransition tt1
                = new TranslateTransition(Duration.millis(300), btnBack);
        tt1.setToX(280);
        tt1.setToY(80);

        TranslateTransition tt2
                = new TranslateTransition(Duration.millis(300), btnBack);
        tt2.setToX(20);
        tt2.setToY(120);

        TranslateTransition tt3
                = new TranslateTransition(Duration.millis(300), btnBack);
        tt3.setToX(350);
        tt3.setToY(200);

        TranslateTransition tt4
                = new TranslateTransition(Duration.millis(300), btnBack);
        tt4.setToX(15);
        tt4.setToY(300);

        TranslateTransition tt5
                = new TranslateTransition(Duration.millis(300), btnBack);

        tt5.setToX(415);
        tt5.setToY(385);
        createTransitionsSequence(tt, tt1, tt2, tt3, tt4, tt5);
    }

    private void createTransitionsSequence(TranslateTransition tt, TranslateTransition tt1,
            TranslateTransition tt2, TranslateTransition tt3, TranslateTransition tt4,
            TranslateTransition tt5) {
        tt.setOnFinished(e -> {
            tt1.play();
        });
        tt1.setOnFinished(e -> {
            tt2.play();
        });
        tt2.setOnFinished(e -> {
            tt3.play();
        });
        tt3.setOnFinished(e -> {
            tt4.play();
        });
        tt4.setOnFinished(e -> {
            tt5.play();
        });
        rubberMovement.setOnFinished(e -> {
            tt.play();
        });
    }

    @Override
    public void disableButton() {
        btnBack.setDisable(true);
    }

    @Override
    public void enableButton() {
        btnBack.setDisable(false);
    }

}
