/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import enums.InputField;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pattern.mvp.presenter.RegisterPresenter;

/**
 *
 * @author tinet
 */
public class RegistrationViewer extends BorderPane implements View {

    private Label fillInfo;
    private Button btnConfirm;
    private Button btnClear;
    private CustomTextField username;
    private CustomPasswordTextField password;
    private CustomTextField visiblePassword;
    private CustomPasswordTextField checkPassword;
    private CustomTextField visibleCheckPassword;
    private Label showpass;
    private CheckBox show;
    private CustomTextField email;
    private DatePicker birthDate;
    private VBox mainPanel;
    private static final int USERNAMEFIELD = 1;
    private static final int PASSWORDFIELD = 2;
    private static final int VISIBLEPASSWORDFIELD = 3;
    private static final int CHECKPASSWORDFIELD = 4;
    private static final int VISIBLECHECKPASSWORDFIELD = 5;
    private static final int EMAILFIELD = 6;
    private static final int BIRTHDATEFIELD = 7;
    private Label bornDate;
    private ImageView ivExit;
    private Button btnExit;

    public RegistrationViewer(String info) {
        getStylesheets().add("CssFiles/CustomTextFieldStyle.css");
        getStylesheets().add("CssFiles/registrationStyle.css");
        setStyle("-fx-background-image:url('/Images/hexagon2.png');  -fx-opacity: 0.7;");

        setupLayout(info);
        setupMainPanel();
        setupShowPassword();
        changeCursor();
    }

    private void setupBtnExit() {
        ivExit = new ImageView("/Images/exit.png");
        ivExit.setFitHeight(33);
        ivExit.setFitWidth(33);

        btnExit = new Button("", ivExit);
        btnExit.setTranslateX(200);
        btnExit.setTranslateY(200);

        getChildren().add(btnExit);
    }

    private void setupBtnConfirm() {
        btnConfirm = new Button("CONFIRMAR");
        btnConfirm.setTranslateY(291);
        btnConfirm.setTranslateX(100);
        btnConfirm.setId("button");
    }

    private void setupBtnClear() {
        btnClear = new Button("LIMPAR");
        btnClear.setTranslateY(261);
        btnClear.setTranslateX(100);
        btnClear.setId("button");
    }

    private void setupFillInfoLabel(String info) {
        fillInfo = new Label(info);
        fillInfo.setTranslateY(14);
        fillInfo.setTranslateX(20);
        fillInfo.setId("fill-info-label");

    }

    private void setupShow() {
        showpass = new Label("Visualizar password?");
        showpass.setTranslateY(197);
        showpass.setTranslateX(297);
        showpass.setId("visualizar-password-label");

        show = new CheckBox();
        show.setTranslateX(235);
        show.setTranslateY(212);
    }

    private void setupBirthDate() {
        birthDate = new DatePicker();
        birthDate.setTranslateY(226);
        birthDate.setTranslateX(23);

        bornDate = new Label("Data de Nascimento");
        bornDate.setTranslateY(58);
        bornDate.setTranslateX(21);

    }

    private void setupUsername() {
        username = new CustomTextField("Username");
        username.setTranslateY(42);
        username.setTranslateX(20);
        username.setManaged(false);
    }

    private void setupVisiblePassword() {
        visiblePassword = new CustomTextField("Password");
        visiblePassword.setTranslateX(235);
        visiblePassword.setTranslateY(42);
        visiblePassword.setManaged(false);
        visiblePassword.setVisible(false);

        visibleCheckPassword = new CustomTextField("Verifique Password");
        visibleCheckPassword.setTranslateY(132);
        visibleCheckPassword.setTranslateX(235);
        visibleCheckPassword.setManaged(false);
        visibleCheckPassword.setVisible(false);
    }

    private void setupPassword() {
        password = new CustomPasswordTextField("Password");
        password.setTranslateY(42);
        password.setTranslateX(235);
        password.setManaged(false);
        password.textProperty()
                .bindBidirectional(visiblePassword.textProperty());

        checkPassword = new CustomPasswordTextField("Verifique Password");
        checkPassword.setTranslateX(235);
        checkPassword.setTranslateY(132);
        checkPassword.setManaged(false);
        checkPassword.textProperty()
                .bindBidirectional(visibleCheckPassword.textProperty());
    }

    private void setupEmail() {
        email = new CustomTextField("Email");
        email.setTranslateY(132);
        email.setTranslateX(20);
        email.setManaged(false);
    }

    private void changeCursor() {
        Platform.runLater(() -> {
            Image image = new Image("/images/mouse1.png"); //pass in the image path
            getScene().setCursor(new ImageCursor(image));
        });
    }

    public void setUsername(String text) {
        username.setText(text);
    }

    public void setPassword(String text) {
        password.setText(text);
    }

    public void setEmail(String text) {
        email.setText(text);
    }

    public void setCheckPassword(String text) {
        checkPassword.setText(text);
    }

    public void setFillInfo(String info) {
        fillInfo.setText(info);
    }

    private void setupLayout(String info) {
        setupVisiblePassword();
        setupBirthDate();
        setupBtnClear();
        setupBtnConfirm();
        setupBtnExit();
        setupEmail();
        setupFillInfoLabel(info);
        setupPassword();
        setupShow();
        setupShowPassword();
        setupUsername();
    }

    private void setupMainPanel() {
        mainPanel = new VBox();
        setCenter(mainPanel);
        mainPanel.setStyle("-fx-background-color: linear-gradient(#00ffcc 0%, #66ffe0 38%,  #b3fff0 55%,"
                + " #e6fffa 70%, transparent 100%);   -fx-opacity: 0.9;");
        mainPanel.setMaxSize(420, 498);
        mainPanel.getChildren().addAll(fillInfo, username, email, visiblePassword, password,
                checkPassword, visibleCheckPassword, show, showpass, birthDate,
                btnClear, btnConfirm, bornDate);
    }

    public VBox getMainPanel() {
        return mainPanel;
    }

    public void replace(int index, Node node) {
        mainPanel.getChildren().set(index, node);
    }

    public void remove(int index) {
        mainPanel.getChildren().remove(index);
    }

    private void showPassword() {
        if (password.isVisible() && checkPassword.isVisible()) {
            password.setVisible(false);
            checkPassword.setVisible(false);
            visiblePassword.setVisible(true);
            visibleCheckPassword.setVisible(true);
        } else {
            visiblePassword.setVisible(false);
            visibleCheckPassword.setVisible(false);
            password.setVisible(true);
            checkPassword.setVisible(true);
        }
    }

    private void setupShowPassword() {
        show.setOnAction(e -> {
            showPassword();
        });
    }

    public void resetInput() {
        username.clear();
        password.clear();
        visiblePassword.clear();
        checkPassword.clear();
        visibleCheckPassword.clear();
        email.clear();
        birthDate.getEditor().clear();
    }

    @Override
    public String getUserInput(InputField input) {
        switch (input) {
            case USERNAMEFIELD:
                return username.getText();
            case PASSWORDFIELD:
                return password.getText();
            case VISIBLEPASSWORDFIELD:
                return visiblePassword.getText();
            case CHECKPASSWORDFIELD:
                return checkPassword.getText();
            case VISIBLECHECKPASSWORDFIELD:
                return visibleCheckPassword.getText();
            case EMAILFIELD:
                return email.getText();
            case BIRTHDATEFIELD:
                return birthDate.getEditor().getText();
            default:
                return "";
        }
    }

    public LocalDate getBirthDate() {
        return birthDate.getValue();
    }

    @Override
    public void setTriggers(Object presenter) {
        btnClear.setOnAction(e -> {
            ((RegisterPresenter) presenter).resetInput();
        });
        btnConfirm.setOnAction(e -> {
            try {
                ((RegisterPresenter) presenter).register();
            } catch (InterruptedException ex) {
                Logger.getLogger(RegistrationViewer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public boolean isPasswordVisible() {
        return visiblePassword.isVisible();
    }

    @Override
    public void showInfo(String warning) {
        JOptionPane.showMessageDialog(new JFrame(), warning);
    }

    public void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registo");
        alert.setHeaderText("Informação");
        alert.setContentText("Registo efetuado com sucesso!");
        alert.show();
    }

}
