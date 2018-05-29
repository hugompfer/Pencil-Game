/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.presenter;

import enums.InputField;
import pattern.singleton.LoggerException;
import java.util.Optional;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import pattern.singleton.Logger;
import pattern.singleton.LoggerConfiguration;
import model.User;
import model.LoginChecker;
import model.CheckerException;
import pattern.mvp.view.LoginViewer;
import pattern.mvp.view.TabsViewer;

/**
 * LoginPresenter representa a classe presenter que faz interação entre o menu
 * de login e a persistência dos dados.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class LoginPresenter {

    private LoginViewer view;
    private LoginChecker model;

    public LoginPresenter(LoginChecker m, LoginViewer v) {
        this.view = v;
        this.model = m;
        view.setTriggers(this);

    }

    private boolean checkUser(String username) {
        if (username.isEmpty()) {
            view.showInfo("Campo de username vazio");
            return false;
        }
        return true;
    }

    private boolean checkPassword(String password) {
        if (password.isEmpty()) {
            view.showInfo("Campo de password vazio");
            return false;
        }
        return true;
    }

    private boolean checkData() {
          return checkUser(view.getUserInput(InputField.USERNAMEFIELD)) && checkPassword(view.getUserInput(InputField.PASSWORDFIELD));
    }

    /**
     * Faz com que o programa termine a execução.
     */
    public void exit() {
        try {
            Logger.getInstance().closeLog();
        } catch (LoggerException ex) {
            java.util.logging.Logger.getLogger(LoginPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Platform.exit();
    }

    /**
     * Permite terminar sessão de determinado utilizador.
     *
     * @param user utilizador a fazer logout.
     */
    public void logoutUser(int user) {
        //alerta
        view.setEffect(new GaussianBlur(100));
        if (view.showQuestion().get() == ButtonType.OK) {
            switch (user) {
                case 1:
                    model.removeFirstLoggedPlayer();
                case 2:
                    model.removeSecondLoggedPlayer();
            }

            TabsViewer root = new TabsViewer(1, model.getManager());
            Scene scene = new Scene(root, 600, 550);
            ((Stage) view.getScene().getWindow()).setScene(scene);
        }

    }

    /**
     * Permite verificar e mostrar as hipótesses possiveis de logout.
     */
    public void showPossibleLogouts() {
        if (model.has1UserLogged()) {
            view.showLogout1();
        }
        if (model.has2UsersLogged()) {
            view.showLogout2();
        }
    }

    /**
     * Permite a autenticação de um utilizador.
     */
    public void enter() {
        User user = (User) model.findUser(view.getUserInput(InputField.USERNAMEFIELD));
        if (checkData()) {
            try {
                model.log(view.getUserInput(InputField.USERNAMEFIELD), view.getUserInput(InputField.PASSWORDFIELD));
                writeToLog(user);
                TabsViewer root = new TabsViewer(3, model.getManager());
                Scene scene = new Scene(root, 600, 550);
                ((Stage) view.getScene().getWindow()).setScene(scene);
            } catch (CheckerException le) {
                view.showInfo(le.getMessage());
            }
        }
    }

    private void writeToLog(User u) {
        String info = "Inicio de sessão do utilizador: " + u.toString();
        try {
            Logger.getInstance().writeToLog(info, LoggerConfiguration.Options.AUTENTICATION);
        } catch (LoggerException ex) {
            //continua mesmo assim
        }
    }
}
