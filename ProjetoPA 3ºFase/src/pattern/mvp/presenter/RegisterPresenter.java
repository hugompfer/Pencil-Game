/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.presenter;

import enums.InputField;
import pattern.singleton.LoggerException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import model.CheckerException;
import pattern.singleton.Logger;
import pattern.singleton.LoggerConfiguration;
import model.RegisterChecker;
import model.User;
import pattern.mvp.view.RegistrationViewer;
import pattern.mvp.view.TabsViewer;

/**
 * RegisterPresenter representa a classe presenter que faz interação entre o
 * menu de registo e a persistência dos dados.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class RegisterPresenter {

    private RegistrationViewer view;
    private RegisterChecker model;

    public RegisterPresenter(RegisterChecker m, RegistrationViewer v) {
        this.view = v;
        this.model = m;
        view.setTriggers(this);
    }

    public RegistrationViewer getView() {
        return view;
    }

    public RegisterChecker getModel() {
        return model;
    }

    public User getSecondUser() {
        return model.getManager().getSecondUser();
    }

    /**
     * Limpa todos os dados introduzidos.
     */
    public void resetInput() {
        view.resetInput();
    }

    private boolean checkUsername(String username) {
        if (username.isEmpty()) {
            view.showInfo("Campo de username vazio");
            return false;
        }
        return true;
    }

    /**
     * Permite verificar se as passwords introduzidas correspondem.
     *
     * @param password
     * @param checkPassword
     * @return Valor true ou false.
     */
    public boolean checkPassword(String password, String checkPassword) {
        if (password.isEmpty() || checkPassword.isEmpty()) {
            view.showInfo("Campo de password vazio");
            return false;
        }
        if (!password.equals(checkPassword)) {
            view.showInfo("Confirme a sua password novamente");
            return false;
        }

        return true;
    }

    /**
     * Permite verificar se o email introduzido está corretamente preenchido.
     *
     * @return Valor true ou false.
     */
    public boolean checkEmail(String email) {
        if (email.isEmpty()) {
            view.showInfo("Campo de email vazio");
            return false;
        }

        return true;
    }

    private boolean checkBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            view.showInfo("Campo de data de nascimento vazio");
            return false;
        }
        return true;
    }

    public boolean checkData() {
        return (checkUsername(view.getUserInput(InputField.USERNAMEFIELD))
                && checkPassword(view.getUserInput(InputField.PASSWORDFIELD), view.getUserInput(InputField.CHECKPASSWORDFIELD))
                && checkEmail(view.getUserInput(InputField.EMAILFIELD)) && checkBirthDate(view.getBirthDate()));
    }

    /**
     * Permite que um utilizador se registe.
     *
     * @throws InterruptedException
     */
    public void register() throws InterruptedException {
        if (checkData()) {
            try {
                User u = model.register(view.getUserInput(InputField.USERNAMEFIELD), view.getUserInput(InputField.PASSWORDFIELD),
                        view.getUserInput(InputField.EMAILFIELD), view.getBirthDate());
                writeToLog(u);
            } catch (CheckerException ce) {
                view.showInfo(ce.getMessage());
            }
            view.setEffect(new GaussianBlur(100));
            view.showInfo();

            TabsViewer root = new TabsViewer(1, model.getManager());
            Scene scene = new Scene(root, 600, 550);
            ((Stage) view.getScene().getWindow()).setScene(scene);
        }
    }

    private void writeToLog(User u) {
        String info = "Registo de utilizador: " + u.toString();
        try {
            Logger.getInstance().writeToLog(info, LoggerConfiguration.Options.AUTENTICATION);
        } catch (LoggerException ex) {
            //continua mesmo assim
        }
    }
}
