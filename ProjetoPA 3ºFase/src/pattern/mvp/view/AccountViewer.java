/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.User;
import pattern.mvp.presenter.AccountPresenter;

/**
 *
 * @author tinet
 */
public class AccountViewer extends RegistrationViewer {

    private Label birthDate;
    private Button btnSave;
    private Label username;
    private Label usernameLab;
    private ImageView next;
    private ImageView previous;
    private Button btnNext;
    private Button btnPrevious;

    public AccountViewer(User u) {
        super("Informações da conta");
        getStylesheets().add("CssFiles/accountStyle.css");
        setStyle("-fx-background-image:url('/Images/hexagon4.png');  -fx-opacity: 0.7;");
        setupLayout(u);
        setupMainPanel();
        setUserInfo(u);
    }

    private void setupBtnNext() {
        next = new ImageView("/Images/nextUser.png");
        btnNext = new Button("", next);
        btnNext.setTranslateY(-242);
        btnNext.setTranslateX(253);
        btnNext.setStyle("-fx-background-color: transparent;");
        next.setFitHeight(48);
        next.setFitWidth(48);
    }

    private void setupBtnPrevious() {
        previous = new ImageView("/Images/previousUser.png");
        btnPrevious = new Button("", previous);
        btnPrevious.setTranslateY(-242);
        btnPrevious.setTranslateX(253);
        btnPrevious.setStyle("-fx-background-color: transparent;");
        previous.setFitHeight(48);
        previous.setFitWidth(48);
    }

    private void setupBirthDate(User u) {
        birthDate = new Label("Data de Nascimento:\n" + u.getBithdate().toString());
        birthDate.setTranslateY(155);
        birthDate.setTranslateX(15);
        replace(9, birthDate);
    }

    private void setupBtnSave() {
        btnSave = new Button("GUARDAR ALTERAÇÕES");
        btnSave.setTranslateY(210);
        btnSave.setTranslateX(100);
        replace(10, btnSave);
    }

    private void setupUsernameLab() {
        usernameLab = new Label("Username:");
        replace(1, usernameLab);
        usernameLab.setTranslateX(25);
        usernameLab.setTranslateY(20);
    }

    private void setupLayout(User u) {
        super.remove(10);
        super.remove(11);
        setupBirthDate(u);
        setupBtnNext();
        setupBtnPrevious();
        setupBtnSave();
        setupUsernameLab();
        setUserInfo(u);

        super.getMainPanel().setMaxHeight(484);
        super.getMainPanel().getChildren().addAll(username, btnNext, btnPrevious);

    }

    public void setUserInfo(User u) {
        if (username == null) {
            username = new Label();
        }
        username.setText(u.getIdentification());
        username.setId("username-label");
        username.setTranslateX(30);
        username.setTranslateY(-113);
        setPassword(u.getPassword());
        setCheckPassword(u.getPassword());
        setEmail(u.getEmail());
        
    }

    public String getUsername() {
        return username.getText();
    }

    public void setBirthDate(String birth) {
        birthDate.setText("Data de Nascimento:\n" + birth);
    }

    public void changeBtnPreviousBehaviour(boolean ativate) {
        btnPrevious.setVisible(ativate);
        btnPrevious.setManaged(ativate);
    }

    public void changeBtnNextBehaviour(boolean ativate) {
        btnNext.setVisible(ativate);
        btnNext.setManaged(ativate);
    }

    private void setupMainPanel() {
        getMainPanel().setStyle("-fx-background-color: linear-gradient(#4d004d 0%, #ffb3ff 12%, #ffe866 33%, #fff099 40%,"
                + "#fff4b3 50%, transparent 100%);   -fx-opacity: 0.9;");
    }

    @Override
    public void setTriggers(Object presenter) {
        btnSave.setOnAction(e -> {
            ((AccountPresenter) presenter).updateRegister();
        });
        btnNext.setOnAction(e -> {
            ((AccountPresenter) presenter).nextUser();
        });
        btnPrevious.setOnAction(e -> {
            ((AccountPresenter) presenter).previousUser();
        });
    }

    @Override
    public void resetInput() {
        super.resetInput();
    }
}
