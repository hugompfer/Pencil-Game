/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import enums.InputField;
import java.util.Optional;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pattern.mvp.presenter.LoginPresenter;

/**
 *
 * @author tinet
 */
public class LoginViewer extends BorderPane implements View {

    private Button btnEnter;
    private Label usernameLabel;
    private Label passwordLabel;
    private Label wellcome;
    private TextField username;
    private PasswordField password;
    private TextField visiblePassword;
    private CheckBox show;
    private VBox box;
    private Label showpass;
    private Line line;
    private TranslateTransition moveUsername;
    private TranslateTransition moveUsernameBackwords;
    private TranslateTransition movePassword;
    private TranslateTransition movePasswordBackwords;
    private ImageView userIcon;
    private ImageView passIcon;
    private ImageView ivExit;
    private Button btnExit;
    private ImageView logout;
    private ImageView ivLogout1;
    private ImageView ivLogout2;
    private Button btnLogoutUser1;
    private Button btnLogoutUser2;
    private Label logoutLabel;
    private Label exitLabel;
    private static final int USERNAMEFIELD = 1;
    private static final int PASSWORDFIELD = 2;
    private static final int VISIBLEPASSWORDFIELD = 3;

    public LoginViewer() {
        getStylesheets().add("CssFiles/loginStyle.css");
        setStyle("-fx-background-image:url('/Images/hexagon1.jpg');  -fx-opacity: 0.7;");
        setupLayout();
        setupMainPanel();
        setupShowPassword();
        setupTranslations();
        setupEffects();
        changeCursor();
    }

    private void setupUsername() {
        username = new TextField();
        username.setFocusTraversable(false);
        username.setTranslateY(20);
        username.setTranslateX(15.5);
        username.setEditable(false);

        usernameLabel = new Label("Username");
        usernameLabel.setId("credential-label");
        usernameLabel.setTranslateX(-69);
        usernameLabel.setTranslateY(-29);
        usernameLabel.setFocusTraversable(false);
    }

    private void setupUserIcon() {
        userIcon = new ImageView("/Images/usernameicon.png");
        userIcon.setFitHeight(35);
        userIcon.setFitWidth(40);
        userIcon.setManaged(false);
        userIcon.setTranslateX(10);
        userIcon.setTranslateY(88.6);
    }

    private void setupVisiblePassword() {
        visiblePassword = new TextField();
        visiblePassword.setTranslateY(-10);
        visiblePassword.setTranslateX(15.5);
        visiblePassword.setVisible(false);
        visiblePassword.setManaged(false);
        visiblePassword.setFocusTraversable(true);
    }

    private void setupPassword() {
        passwordLabel = new Label("Password");
        passwordLabel.setFocusTraversable(false);
        passwordLabel.setId("credential-label");
        passwordLabel.setTranslateX(-69);
        passwordLabel.setTranslateY(-59);

        password = new PasswordField();
        password.setTranslateY(-10);
        password.setTranslateX(15.5);
        password.setEditable(false);
        password.setFocusTraversable(false);
        password.textProperty()
                .bindBidirectional(visiblePassword.textProperty());
    }

    private void setupPasswordIcon() {
        passIcon = new ImageView("/Images/passwordicon1.png");
        passIcon.setFitHeight(35);
        passIcon.setFitWidth(40);
        passIcon.setManaged(false);
        passIcon.setTranslateX(10);
        passIcon.setTranslateY(155);
    }

    private void setupWellcomeLabel() {
        wellcome = new Label("Bem-Vindo");
        wellcome.setTranslateY(15);
    }

    private void setupShow() {
        showpass = new Label("Visualizar password?");
        showpass.setId("label-show-pass");
        showpass.setTranslateX(-2);
        showpass.setTranslateY(-84);

        show = new CheckBox();
        show.setTranslateX(-97);
        show.setTranslateY(-51);
    }

    private void setupLine() {
        line = new Line();
        line.setStartX(0);
        line.setStartY(10);
        line.setEndX(250);
        line.setEndY(10);
        line.setTranslateY(-78);
    }

    private void setupTranslations() {
        moveUsername = new TranslateTransition(Duration.millis(500), usernameLabel);
        moveUsername.setToY(-55);
        moveUsername.setCycleCount(1);

        movePassword = new TranslateTransition(Duration.millis(500), passwordLabel);
        movePassword.setToY(-85);
        movePassword.setCycleCount(1);

        moveUsernameBackwords = new TranslateTransition(Duration.millis(500), usernameLabel);
        moveUsernameBackwords.setToY(-28);
        moveUsernameBackwords.setCycleCount(1);

        movePasswordBackwords = new TranslateTransition(Duration.millis(500), passwordLabel);
        movePasswordBackwords.setToY(-60);
        movePasswordBackwords.setCycleCount(1);
    }

   

    private void setupLogoutButton() {
        logoutLabel = new Label("Terminar Sessão");
        logoutLabel.setTranslateX(-196);
        logoutLabel.setTranslateY(96);
        logoutLabel.setId("credential-label");

        logout = new ImageView("/Images/logout.png");
        logout.setFitHeight(30);
        logout.setFitWidth(30);
        logout.setTranslateX(38);
        logout.setTranslateY(486);
        logout.setId("logout-view");
    }

    private void setupLogoutUser1() {
        ivLogout1 = new ImageView("/Images/logout1.png");
        ivLogout1.setFitHeight(50);
        ivLogout1.setFitWidth(50);

        btnLogoutUser1 = new Button("", ivLogout1);
        btnLogoutUser1.setTranslateX(100);
        btnLogoutUser1.setTranslateY(490);
        btnLogoutUser1.setVisible(false);
        btnLogoutUser1.setId("logout-button");

    }

   

    private void changeCursor() {
        Platform.runLater(() -> {
            Image image = new Image("/images/mouse1.png"); //pass in the image path
            getScene().setCursor(new ImageCursor(image));
        });
    }
    
     private void setupBtnEnter() {
        btnEnter = new Button("ENTRAR");
        btnEnter.setTranslateY(-65);
    }

    private void setupMainPanel() {
        box = new VBox(18);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(0, 20, 10, 20));
        box.setMaxSize(320, 200);
        box.setStyle("-fx-background-color: linear-gradient(cyan 0%, lightcyan 80%, transparent 100%);   -fx-opacity: 0.7;");
        //box.setStyle("-fx-background-color:  linear-gradient(to top, lightcyan, cyan);");
        box.getChildren().addAll(wellcome, username, usernameLabel, password, visiblePassword,
                passwordLabel, show, showpass, line, btnEnter, userIcon, passIcon);
        setCenter(box);
        box.setTranslateY(-10);
    }
    
     private void setupLogoutUser2() {
        ivLogout2 = new ImageView("/Images/logout2.png");
        ivLogout2.setFitHeight(51);
        ivLogout2.setFitWidth(50);

        btnLogoutUser2 = new Button("", ivLogout2);
        btnLogoutUser2.setTranslateX(144);
        btnLogoutUser2.setTranslateY(490);
        btnLogoutUser2.setVisible(false);
        btnLogoutUser2.setId("logout-button");
    }

    private void setupExit() {
        ivExit = new ImageView("/Images/exit.png");
        ivExit.setFitHeight(33);
        ivExit.setFitWidth(33);

        btnExit = new Button("", ivExit);
        btnExit.setTranslateX(463);
        btnExit.setTranslateY(502);

        exitLabel = new Label("Sair");
        exitLabel.setTranslateX(210);
        exitLabel.setTranslateY(52);
        exitLabel.setId("credential-label");
    }

    private void setupLayout() {
        setupBtnEnter();
        setupExit();
        setupLine();
        setupLogoutButton();
        setupLogoutUser1();
        setupLogoutUser2();
        setupVisiblePassword();
        setupPassword();
        setupShow();
        setupTranslations();
        setupUserIcon();
        setupUsername();
        setupWellcomeLabel();
        setupPasswordIcon();
        getChildren().addAll(btnExit, logout, btnLogoutUser1, btnLogoutUser2, logoutLabel, exitLabel);
    }

    private void showPassword() {
        if (password.isVisible()) {
            password.setVisible(false);
            password.setManaged(false);

            visiblePassword.setVisible(true);
            visiblePassword.setManaged(true);
        } else {
            visiblePassword.setVisible(false);
            visiblePassword.setManaged(false);

            password.setVisible(true);
            password.setManaged(true);
        }
    }

    private void closeLock() {
        passIcon.setImage(new Image("/Images/passwordicon1.png"));
    }

    private void openLock() {
        passIcon.setImage(new Image("/Images/passwordicon2.png"));
    }

    private void setupShowPassword() {
        show.setOnAction(e -> {
            showPassword();
        });
    }

    private void open() {
        if (username.isFocused()) {
            moveUsername.play();
        }
        if (password.isFocused()) {
            movePassword.play();
        }
        if (visiblePassword.isFocused()) {
            movePassword.play();
        }
    }

    private void usernameOpenEffect() {
        username.setOnKeyPressed(e -> {
            open();
        });
        username.setOnMouseEntered(e -> {
            moveUsername.play();
            username.setEditable(true);
            username.requestFocus();
        });
        username.setOnMouseClicked(e -> {
            moveUsername.play();
            username.setEditable(true);
            username.requestFocus();
        });

        usernameLabel.setOnMouseEntered(e -> {
            moveUsername.play();
            username.setEditable(true);
            username.requestFocus();
        });

        usernameLabel.setOnMouseClicked(e -> {
            moveUsername.play();
            username.setEditable(true);
            username.requestFocus();
        });
    }

    private void usernameLockEffect() {
        username.setOnMouseExited(e -> {
            if (username.getText().isEmpty()) {
                moveUsernameBackwords.play();
                username.setEditable(false);
            }
        });
        usernameLabel.setOnMouseExited(e -> {
            if (username.getText().isEmpty()) {
                moveUsernameBackwords.play();
                username.setEditable(false);
            }
        });
    }

    private void passwordOpenEffect() {
        passwordLabel.setOnMouseEntered(e -> {
            movePassword.play();
            if (visiblePassword.isVisible()) {
                visiblePassword.setEditable(true);
                visiblePassword.requestFocus();
            }
            password.setEditable(true);
            password.requestFocus();
        });
        passwordLabel.setOnMouseClicked(e -> {
            movePassword.play();
            if (visiblePassword.isVisible()) {
                visiblePassword.setEditable(true);
                visiblePassword.requestFocus();
            }
            password.setEditable(true);
            password.requestFocus();
        });
        password.setOnKeyPressed(e -> {
            open();
            openLock();
        });
        password.setOnMouseEntered(e -> {
            movePassword.play();
            password.setEditable(true);
            password.requestFocus();
        });
        password.setOnMouseClicked(e -> {
            movePassword.play();
            password.setEditable(true);
            password.requestFocus();
        });
    }

    private void passwordLockEffect() {
        passwordLabel.setOnMouseExited(e -> {
            if (password.getText().isEmpty() && visiblePassword.getText().isEmpty()) {
                movePasswordBackwords.play();
                closeLock();
                if (visiblePassword.isVisible()) {
                    visiblePassword.setEditable(false);
                }
                password.setEditable(false);
            }
        });
        password.setOnMouseExited(e -> {
            if (password.getText().isEmpty() && visiblePassword.getText().isEmpty()) {
                movePasswordBackwords.play();
                closeLock();
                password.setEditable(false);
            }
        });
    }

    private void visiblePasswordOpenEffect() {
        visiblePassword.setOnKeyPressed(e -> {
            open();
            openLock();
        });
        visiblePassword.setOnMouseEntered(e -> {
            movePassword.play();
            visiblePassword.setEditable(true);
            visiblePassword.requestFocus();
        });

        visiblePassword.setOnMouseClicked(e -> {
            movePassword.play();
            visiblePassword.setEditable(true);
            visiblePassword.requestFocus();
        });
    }

    private void visiblePasswordLockEffect() {
        visiblePassword.setOnMouseExited(e -> {
            if (password.getText().isEmpty() && visiblePassword.getText().isEmpty()) {
                movePasswordBackwords.play();
                closeLock();
                visiblePassword.setEditable(false);
            }
        });
    }

    private void setupEffects() {
        usernameOpenEffect();
        usernameLockEffect();
        passwordOpenEffect();
        visiblePasswordOpenEffect();
        passwordLockEffect();
        visiblePasswordLockEffect();
    }

    public boolean isPasswordVisible() {
        return visiblePassword.isVisible();
    }

    public void showLogout1() {
        btnLogoutUser1.setVisible(true);
        btnLogoutUser1.setOnMouseExited(e -> {
            btnLogoutUser1.setVisible(false);
        });
    }

    public void showLogout2() {
        showLogout1();
        btnLogoutUser2.setVisible(true);
        btnLogoutUser2.setOnMouseExited(e -> {
            btnLogoutUser2.setVisible(false);
        });
    }

    @Override
    public void setTriggers(Object presenter) {
        btnEnter.setOnAction(e -> {
            ((LoginPresenter) presenter).enter();
        });
        this.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btnEnter.fire();
            }
        });
        btnExit.setOnAction(e -> {
            ((LoginPresenter) presenter).exit();
        });
        //so mostro estes dois se passar pelo logout em que vê se ha users logados
        btnLogoutUser1.setOnAction(e -> {
            ((LoginPresenter) presenter).logoutUser(1);
        });
        btnLogoutUser2.setOnAction(e -> {
            ((LoginPresenter) presenter).logoutUser(2);
        });
        logout.setOnMouseEntered(e -> {
            ((LoginPresenter) presenter).showPossibleLogouts();
        });
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
            default:
                return "";
        }
    }

    @Override
    public void showInfo(String warning
    ) {
        JOptionPane.showMessageDialog(new JFrame(), warning);
    }

    public Optional<ButtonType> showQuestion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Terminar sessão");
        alert.setHeaderText("Confirmação");
        alert.setContentText("Tem a certeza que pretende terminar sessão?");
        return alert.showAndWait();
    }
}
