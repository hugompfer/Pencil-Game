/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Player;
import model.RowData;
import pattern.mvp.presenter.StatisticsPresenter;

/**
 *
 * @author Tiago
 */
public final class StatisticsViewer extends Pane implements StatisticsView {

    private  Label title;
    private final TableView<RowData> table;
    private final ArrayList<TableColumn> tableColumns;
    private StatisticsPopUp sp;

    public StatisticsViewer() {
        getStylesheets().add("CssFiles/StatisticsViewerStyle.css");

        table = new TableView<>();
        tableColumns = new ArrayList<>();
        setupLayout();
        getChildren().addAll(table, title);
        changeCursor();
    }

    private void changeCursor() {
        Platform.runLater(() -> {
            Image image = new Image("/images/mouse1.png"); //pass in the image path
            getScene().setCursor(new ImageCursor(image));
        });
    }

    public void setupLayout() {
        setStyle("-fx-background-image:url('/Images/hexagon5.png');  -fx-opacity: 0.72");
        setupTitle();
        setupTableColumns();
        setupTable();
    }

    private void setupTitle() {
        title = new Label("Dados estatísticos");
        title.setTranslateX(50);
        title.setTranslateY(10);
        title.setId("title-label");
        title.setStyle("-fx-font-size: 27.0pt;\n"
                + "    -fx-font-family:  \"Segoe UI light\";\n"
                + "     -fx-effect: dropshadow(three-pass-box, transparent, 0, 0, 0, 0);");
    }

    private void setupTableColumns() {
        tableColumns.add(new TableColumn<>("ID"));
        tableColumns.add(new TableColumn<>("Nome"));
        tableColumns.add(new TableColumn<>("Numero de jogos ganhos"));

        tableColumns.get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumns.get(0).setPrefWidth(75);
        tableColumns.get(0).setStyle("-fx-alignment: CENTER;");
        tableColumns.get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumns.get(1).setPrefWidth(150);
        tableColumns.get(1).setStyle("-fx-alignment: CENTER;");
        tableColumns.get(2).setCellValueFactory(new PropertyValueFactory<>("totalOfWinGames"));
        tableColumns.get(2).setPrefWidth(200);
        tableColumns.get(2).setStyle("-fx-alignment: CENTER;");
    }

    private void setupTable() {
        table.setPrefSize(480, 380);
        table.setPadding(new Insets(5, 5, 5, 5));
        table.setTranslateX(20);
        table.setTranslateY(70);
        table.getColumns().addAll(tableColumns.get(0), tableColumns.get(1), tableColumns.get(2));
    }

    @Override
    public void setTriggers(StatisticsPresenter presenter) {
        table.setRowFactory(tv -> {
            TableRow<RowData> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
                if ((!row.isEmpty())) {
                    RowData rowData = row.getItem();
                    Player p = presenter.find(rowData.getId());
                    if (p != null) {
                        sp = new StatisticsPopUp(p);
                        sp.show(((Stage) getScene().getWindow()));
                    }
                }
            });

            row.setOnMouseExited(event -> {
                if (sp != null) {
                    sp.hide();
                }
            });

            return row;
        });

        for (Player p : presenter.getList()) {
            table.getItems().add(new RowData(p));
        }

    }

}
