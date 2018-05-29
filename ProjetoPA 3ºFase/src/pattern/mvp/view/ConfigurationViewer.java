/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.ImageCursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import pattern.singleton.LoggerConfiguration.Options;
import pattern.mvp.presenter.ConfigurationPresenter;

/**
 *
 * @author hugob
 */
public class ConfigurationViewer extends Pane implements ConfigurationInteraction {

    private Label lblInfo;
    private Label lblOptions;
    private Button btnAtivation;
    private Button btnDeactivation;
    private ObservableList<Options> options;
    private ComboBox comboBox;

    public ConfigurationViewer() {
        getStylesheets().add("cssFiles/ConfigurationsViewerStyle.css");
        changeCursor();
        setupLayout();
        getChildren().addAll(btnAtivation, btnDeactivation, comboBox, lblInfo, lblOptions);
    }

    private void changeCursor() {
        Platform.runLater(() -> {
            Image image = new Image("/images/mouse1.png"); //pass in the image path
            getScene().setCursor(new ImageCursor(image));
        });
    }

    private void setupLayout() {
        setStyle("-fx-background-image:url('/Images/hexagon6.png');  -fx-opacity: 0.72");
        setupButtonAtivation();
        setupButtonDeactivation();
        setupComboBox();
        setupLblInfo();
        setupLblOptions();
    }

    private void setupLblInfo() {
        lblInfo = new Label("Configurações de Sistema");
        lblInfo.setTranslateX(50);
        lblInfo.setTranslateY(15);
        lblInfo.setId("title-label");
    }

    private void setupLblOptions() {
        lblOptions = new Label("Opções:");
        lblOptions.setTranslateX(120);
        lblOptions.setTranslateY(195);
    }

    private void setupComboBox() {
        comboBox = new ComboBox();
        comboBox.setTranslateX(205);
        comboBox.setTranslateY(200);
    }

    private void setupButtonAtivation() {
        btnAtivation = new Button("Ativar");
        btnAtivation.setPrefSize(180, 50);
        btnAtivation.setTranslateX(70);
        btnAtivation.setTranslateY(330);
        btnAtivation.setId("ativation-button");
    }

    private void setupButtonDeactivation() {
        btnDeactivation = new Button("Desativar");
        btnDeactivation.setPrefSize(180, 50);
        btnDeactivation.setTranslateX(270);
        btnDeactivation.setTranslateY(330);
        btnDeactivation.setId("deactivation-button");
    }

    @Override
    public void setTriggers(ConfigurationPresenter presenter) {
        options = FXCollections.observableArrayList(presenter.getValues());
        comboBox.setItems(options);

        btnAtivation.setOnAction(e -> {
            if (comboBox.getValue() != null) {
                presenter.ativateOption((Options) comboBox.getValue());
                comboBox.getSelectionModel().select(0);
            }
        });

        btnDeactivation.setOnAction(e -> {
            if (comboBox.getValue() != null) {
                presenter.deactivationOption((Options) comboBox.getValue());
            }
        });

        comboBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener() {
            public void changed(ObservableValue ov,
                    Object old_val, Object new_val) {
                if (new_val != null) {
                    presenter.checkOption((Options) new_val);
                }
            }
        });
    }

    @Override
    public void putEnable(boolean ativation) {
        if (ativation) {
            btnAtivation.setDisable(false);
            btnDeactivation.setDisable(true);
        } else {
            btnAtivation.setDisable(true);
            btnDeactivation.setDisable(false);
        }
    }

    @Override
    public void showInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText("Estado");
        alert.setContentText(info);
        alert.showAndWait();
    }

    @Override
    public void clear() {
        comboBox.getSelectionModel().clearSelection();
    }

}
