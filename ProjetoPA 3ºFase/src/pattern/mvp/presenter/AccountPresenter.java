/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.presenter;

import enums.InputField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AccountChecker;
import model.CheckerException;
import model.User;
import pattern.mvp.view.AccountViewer;
import pattern.mvp.view.TabsViewer;

/**
 * AccountPresenter representa a classe presenter que faz interação entre o menu
 * de alteração de dados de conta e a persistência dos dados.
 *
 * @author Hugo Ferreira (160221089) e Tiago Neto (160221086)
 * @version 2.0 (08/01/2018)
 */
public class AccountPresenter extends RegisterPresenter {

    public AccountPresenter(AccountChecker m, AccountViewer v) {
        super(m, v);
        getView().setTriggers(this);

        setVisibleButton();
    }

    /**
     * Permite alternar a visualização para o próximo utilizador autenticado.
     */
    public void nextUser() {
        User u = getSecondUser();
        ((AccountViewer) getView()).setUserInfo(u);
        ((AccountViewer) getView()).setBirthDate(u.getBithdate().toString());
        setVisibleButton();

    }

    /**
     * Permite alternar a visualização para o anterior utilizador autenticado.
     */
    public void previousUser() {
        TabsViewer root = new TabsViewer(4, getModel().getManager());
        Scene scene = new Scene(root, 600, 550);
        ((Stage) getView().getScene().getWindow()).setScene(scene);
    }

     @Override
    public boolean checkData() {
        return checkPassword(getView().getUserInput(InputField.PASSWORDFIELD), getView().getUserInput(InputField.CHECKPASSWORDFIELD))
                && checkEmail(getView().getUserInput(InputField.EMAILFIELD));
    }

    /**
     * Permite que um registo seja atualizado.
     */
    public void updateRegister() {
        try {
            if (checkData()) {
                 ((AccountChecker) getModel()).update(((AccountViewer) getView()).getUsername(), getView().getUserInput(InputField.EMAILFIELD),
                        getView().getUserInput(InputField.PASSWORDFIELD), getView().getUserInput(InputField.CHECKPASSWORDFIELD));
                showWarning();
            }
        } catch (CheckerException ce) {
            getView().showInfo(ce.getMessage());
        }

    }

    private void setVisibleButton() {

        String username = ((AccountViewer) getView()).getUsername();

        User secondUser = ((AccountChecker) getModel()).getSecondUser();
        String firstUserUsername = ((AccountChecker) getModel()).getFirstUser().getIdentification();

        if (username.equals(firstUserUsername) && secondUser != null) {
            ((AccountViewer) getView()).changeBtnNextBehaviour(true);
            ((AccountViewer) getView()).changeBtnPreviousBehaviour(false);

        } else if (username.equals(firstUserUsername) && secondUser == null) {
            ((AccountViewer) getView()).changeBtnPreviousBehaviour(false);
            ((AccountViewer) getView()).changeBtnNextBehaviour(false);
        }
        if (secondUser != null) {
            String secondUserUsername = ((AccountChecker) getModel()).getSecondUser().getIdentification();
            if (username.equals(secondUserUsername)) {
                ((AccountViewer) getView()).changeBtnNextBehaviour(false);
                ((AccountViewer) getView()).changeBtnPreviousBehaviour(true);
            }
        }
    }

    private void showWarning() {
        getView().showInfo("Alterações efetuadas com sucesso.");
    }

}
