/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import pattern.dao.ConfigurationDAOSerializable;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import model.AccountChecker;
import model.LoginChecker;
import model.PlayersManager;
import model.RegisterChecker;
import model.StatisticsManager;
import model.User;
import pattern.mvp.presenter.AccountPresenter;
import pattern.mvp.presenter.ConfigurationPresenter;
import pattern.mvp.presenter.LoginPresenter;
import pattern.mvp.presenter.RegisterPresenter;
import pattern.mvp.presenter.StatisticsPresenter;

/**
 *
 * @author tinet
 */
public final class TabsViewer extends TabPane {

    private final MyTab register;
    private final MyTab login;
    private final MyTab game;
    private final MyTab account;
    private final MyTab statistics;
    private final MyTab configuration;
    private final boolean acess;
    private final PlayersManager playersManager;

    public TabsViewer(int selectedTab, PlayersManager playersManager) {
        this.playersManager = playersManager;
        acess = false;
        getStylesheets().add("cssFiles/TabPaneStyle.css");
        register = new MyTab("REGISTO", createRegisterNode());
        login = new MyTab("LOGIN", createLoginNode());
        game = new MyTab("JOGAR", new GameSelectionViewer(playersManager));
        account = new MyTab("CONTA", createAccountNode(playersManager.getFirstUser()));
        statistics = new MyTab("ESTATISTICAS", createStatisticsNode());
        configuration = new MyTab("CONFIGURAÇÕES", createConfigurationNode());
        selectTab(selectedTab);

        constructLayout();
        setupBehaviour();
        changeCursor();
        getTabs().addAll(login, register, game, account, statistics, configuration);

    }

    private void changeCursor() {
        Platform.runLater(() -> {
            Image image = new Image("/images/mouse1.png"); //pass in the image path
            getScene().setCursor(new ImageCursor(image));
        });
    }

    private void selectTab(int selectedTab) {
        switch (selectedTab) {
            case 1:
                getSelectionModel().select(login);
                break;
            case 2:
                getSelectionModel().select(register);
                break;
            case 3:
                getSelectionModel().select(game);
                break;
            case 4:
                getSelectionModel().select(account);
                break;
            case 5:
                getSelectionModel().select(statistics);
                break;
            case 6:
                getSelectionModel().select(configuration);
                break;
            default:
                getSelectionModel().select(login);
                break;
        }
    }

    private void constructLayout() {
        setSide(Side.LEFT);
        setTabMinHeight(90);
        setTabMinWidth(68);
    }


    private void setupBehaviour() {

        loginBehaviour();
        registerBehaviour();
        gameBehaviour();
        accountBehaviour();
        statisticsBehaviour();
        configurationBehaviour();

    }

    private void style() {
        login.setStyle("-fx-background-color: grey;");
        login.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, white, 65, 0.9, 0, 0)");
        game.setStyle("-fx-background-color: grey;");
        game.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, white, 65, 0.9, 0, 0)");
        register.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, white, 65, 0.9, 0, 0)");
        register.setStyle("-fx-background-color: grey;");
        account.setStyle("-fx-background-color: grey;");
        account.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, white, 65, 0.9, 0, 0)");
        statistics.setStyle("-fx-background-color: grey;");
        statistics.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, white, 65, 0.9, 0, 0)");
        configuration.setStyle("-fx-background-color: grey;");
        configuration.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, white, 65, 0.9, 0, 0)");
    }

 
    private void loginBehaviour() {
        login.setOnSelectionChanged(e -> {
            style();
            login.setStyle("-fx-background-color: #262626; -fx-opacity: 0.7;");
            login.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, #00b3b3, 65, 0.9, 0, 0)");
        });

        login.getGraphic().setId("login-label");
    }

    private void registerBehaviour() {
        register.setOnSelectionChanged(e -> {
            style();
            register.setStyle("-fx-background-color: #262626; -fx-opacity: 0.7;");
            register.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, #00e6b8, 65, 0.9, 0, 0)");
        });
        register.getGraphic().setId("register-label");
    }

    private void gameBehaviour() {
        game.setOnSelectionChanged(e -> {
            style();
            game.setStyle("-fx-background-color: #262626; -fx-opacity: 0.7;");
            game.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, grey, 65, 0.9, 0, 0)");
        });
        game.getGraphic().setId("game-label");
    }

    private void accountBehaviour() {
        account.setOnSelectionChanged(e -> {
            style();
            account.setStyle("-fx-background-color: #262626; -fx-opacity: 0.7;");
            account.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, #990099, 65, 0.9, 0, 0)");
        });

        account.getGraphic().setId("account-label");
    }

    private void statisticsBehaviour() {
        statistics.setOnSelectionChanged(e -> {
            style();
            statistics.setStyle("-fx-background-color: #262626; -fx-opacity: 0.7;");
            statistics.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box, #33ff33, 65, 0.9, 0, 0)");
        });
        statistics.getGraphic().setId("statistics-label");
    }

    private void configurationBehaviour() {
        configuration.setOnSelectionChanged(e -> {
            style();
            configuration.setStyle("-fx-background-color: #262626; -fx-opacity: 0.7;");
            configuration.getGraphic().setStyle("-fx-effect: dropshadow(three-pass-box,  #ff6666, 65, 0.9, 0, 0)");
        });
        configuration.getGraphic().setId("configuration-label");
    }

    private Node createLoginNode() {
        Node node = null;
        LoginChecker model = new LoginChecker(playersManager);
        LoginViewer view = new LoginViewer();
        LoginPresenter p = new LoginPresenter(model, view);
        node = view;
        return node;
    }

    private Node createRegisterNode() {
        Node node = null;
        RegisterChecker model = new RegisterChecker(playersManager);
        RegistrationViewer view = new RegistrationViewer("Preencha os seus dados");
        RegisterPresenter p = new RegisterPresenter(model, view);
        node = view;
        return node;
    }

    private Node createAccountNode(User u) {
        if (u != null) {
            Node node = null;
            AccountChecker model = new AccountChecker(playersManager);
            AccountViewer view = new AccountViewer(u);
            AccountPresenter p = new AccountPresenter(model, view);
            node = view;
            return node;
        }
        return createLoginNode();
    }

    private Node createStatisticsNode() {
        Node node = null;
        StatisticsManager model = new StatisticsManager(playersManager.getPlayersRegisted());
        StatisticsViewer view = new StatisticsViewer();
        StatisticsPresenter p = new StatisticsPresenter(model, view);
        node = view;
        return node;
    }

    private Node createConfigurationNode() {
        Node node = null;
        ConfigurationDAOSerializable model = new ConfigurationDAOSerializable("");
        ConfigurationViewer view = new ConfigurationViewer();
        ConfigurationPresenter p = new ConfigurationPresenter(model, view);
        node = view;
        return node;
    }
}
