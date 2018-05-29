/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import enums.Difficulty;
import pattern.factory.GameFactory;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Game;
import model.Player;
import model.PlayersManager;
import model.User;
import pattern.mvp.presenter.GamePresenter;

/**
 *
 * @author Tiago
 */
public final class GameSelectionViewer extends Pane {

    private Button btnPlayHuman;
    private Button btnPlayComputerEasy;
    private Button btnPlayComputerHard;
    private Label gameTitle;
    private Timeline timeline1;
    private Timeline timeline2;
    private Timeline timeline3;
    private ComboBox userSelector;
    private final PlayersManager playersManager;

    public GameSelectionViewer(PlayersManager playersManager) {
        this.playersManager = playersManager;
        getStylesheets().add("CssFiles/GameSelectionViewerStyle.css");
        setNormalBackground();
        setupLayout();
        setupTimelines();
        setupEffects();
        setupBehaviour();
        changeCursor();
    }

    private void changeCursor() {
        Platform.runLater(() -> {
            Image image = new Image("/images/mouse1.png"); //pass in the image path
            getScene().setCursor(new ImageCursor(image));
        });
    }

    private void setupLayout() {
        setupBtnComputerHard();
        setupBtnPlayComputerEasy();
        setupGameTitle();
        setupUserSelector();
        setupBtnPlayHuman();

        if (!playersManager.has2UsersLogged()) {
            userSelector.setVisible(false);
        }
        getChildren().addAll(btnPlayHuman, btnPlayComputerEasy, btnPlayComputerHard, gameTitle, userSelector);
    }

    private void setupGameTitle() {
        gameTitle = new Label("Jogo SIM (Paper and Pencil)");
        gameTitle.setTranslateY(25);
        gameTitle.setTranslateX(16);
    }

    private void setupUserSelector() {
        userSelector = new ComboBox();
        userSelector.setTranslateY(41);
        userSelector.setTranslateX(422);
        userSelector.setMaxWidth(90);
        userSelector.setMaxHeight(90);
        userSelector.getItems().addAll("User 1", "User 2");
        userSelector.getSelectionModel().select(0);
    }

    private void setupBtnPlayHuman() {
        btnPlayHuman = new Button("Humano-vs-Humano");
        btnPlayHuman.setTranslateY(134);
        btnPlayHuman.setTranslateX(129);
        btnPlayHuman.setId("human-button");
    }

    private void setupBtnPlayComputerEasy() {
        btnPlayComputerEasy = new Button("Humano-vs-Computador\n\tMODO FÁCIL");
        btnPlayComputerEasy.setTranslateY(275);
        btnPlayComputerEasy.setTranslateX(129);
        btnPlayComputerEasy.setId("easy-button");
    }

    private void setupBtnComputerHard() {
        btnPlayComputerHard = new Button("Humano-vs-Computador\n\tMODO DIFÍCIL");
        btnPlayComputerHard.setTranslateY(415);
        btnPlayComputerHard.setTranslateX(129);
        btnPlayComputerHard.setId("hard-button");
    }

    private void setupTimelines() {
        timeline1 = new Timeline(new KeyFrame(
                Duration.millis(800),
                time1 -> constructHumanGame()));
        timeline2 = new Timeline(new KeyFrame(
                Duration.millis(800),
                time1 -> constructEasyComputerGame()));
        timeline3 = new Timeline(new KeyFrame(
                Duration.millis(800),
                time1 -> constructHardComputerGame()));
    }

    private void setNormalBackground() {
        setStyle("-fx-background-image:url('/Images/hexagon3.jpg');  -fx-opacity: 0.72");
    }

    private void setLoginScene() {
        TabsViewer root = new TabsViewer(1, playersManager);
        Scene scene = new Scene(root, 600, 550);

        ((Stage) getScene().getWindow()).setScene(scene);
    }

    private User checkWhoIsSelectd() {
        if (userSelector.getSelectionModel().isSelected(0)) {
            return playersManager.getFirstUser();
        }
        return playersManager.getSecondUser();
    }

    private void constructHumanGame() {
        ((Stage) getScene().getWindow()).setScene(createGameScene("SimGame", playersManager.getFirstUser(), playersManager.getSecondUser(), null));
    }

    private void constructEasyComputerGame() {
        ((Stage) getScene().getWindow()).setScene(createGameScene("ComputerGame", checkWhoIsSelectd(), playersManager.getMachine(), Difficulty.EASY));
    }

    private void constructHardComputerGame() {
        ((Stage) getScene().getWindow()).setScene(createGameScene("ComputerGame", checkWhoIsSelectd(), playersManager.getMachine(), Difficulty.HARD));
    }

    private void setupHumanBehaviour() {
        btnPlayHuman.setOnAction(e -> {
            if (playersManager.has2UsersLogged()) {
                dissapear();
                timeline1.play();
            } else {
                setEffect(new GaussianBlur(100));
                JOptionPane.showMessageDialog(new JFrame(), "São necessários 2 utilizadores autenticados!");
                setLoginScene();
            }
        });
    }

    private void setupEasyBehaviour() {
        btnPlayComputerEasy.setOnAction(e -> {
            if (playersManager.has2PlayersLogged()) {
                dissapear();
                timeline2.play();
            } else {
                setEffect(new GaussianBlur(100));
                JOptionPane.showMessageDialog(new JFrame(), "É necessário autenticar-se!");
                setLoginScene();
            }
        });
    }

    private void setupHardBehaviour() {
        btnPlayComputerHard.setOnAction(e -> {
            if (playersManager.has2PlayersLogged()) {
                dissapear();
                timeline3.play();
            } else {
                setEffect(new GaussianBlur(100));
                JOptionPane.showMessageDialog(new JFrame(), "É necessário autenticar-se!");
                setLoginScene();
            }
        });
    }

    private void setupBehaviour() {
        setupHumanBehaviour();
        setupEasyBehaviour();
        setupHardBehaviour();
    }

    private void humanEffects() {
        btnPlayHuman.setOnMouseEntered(e -> {
            btnPlayComputerEasy.setVisible(false);
            btnPlayComputerHard.setVisible(false);

        });
        btnPlayHuman.setOnMouseExited(e -> {
            btnPlayComputerEasy.setVisible(true);
            btnPlayComputerHard.setVisible(true);
        });
    }

    private void hardEffects() {
        btnPlayComputerHard.setOnMouseEntered(e -> {
            btnPlayHuman.setVisible(false);
            btnPlayComputerEasy.setVisible(false);
        });
        btnPlayComputerHard.setOnMouseExited(e -> {
            btnPlayHuman.setVisible(true);
            btnPlayComputerEasy.setVisible(true);
        });
    }

    private void easyEffects() {
        btnPlayComputerEasy.setOnMouseEntered(e -> {
            btnPlayHuman.setVisible(false);
            btnPlayComputerHard.setVisible(false);
        });
        btnPlayComputerEasy.setOnMouseExited(e -> {
            btnPlayHuman.setVisible(true);
            btnPlayComputerHard.setVisible(true);
        });
    }

    private void setupEffects() {
        easyEffects();
        hardEffects();
        humanEffects();
    }

    private void dissapear() {
        FadeTransition ft = new FadeTransition(Duration.millis(800), this);
        ft.setFromValue(1.0);
        ft.setToValue(-0.10);
        ft.setInterpolator(Interpolator.LINEAR);
        ft.play();
    }

    private Scene createGameScene(String type, Player p1, Player p2, Difficulty difficulty) {
        Scene scene = null;
        Game model = GameFactory.createGame(type, p1, p2, difficulty);
        Pane view = null;
        GamePresenter c = null;
        if (type.equalsIgnoreCase("SimGame")) {
            view = new SimGameViewer();
            c = new GamePresenter(model, (SimGameViewer) view, playersManager);
        } else {
            view = new GameViewer();
            c = new GamePresenter(model, (GameViewer) view, playersManager);
        }

        scene = new Scene(view, 600, 550);

        return scene;
    }
}
