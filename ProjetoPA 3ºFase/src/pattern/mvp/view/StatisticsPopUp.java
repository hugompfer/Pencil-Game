/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pattern.mvp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.stage.Popup;
import model.Machine;
import model.Player;
import model.User;

/**
 *
 * @author hugob
 */
public class StatisticsPopUp extends Popup {

    private  PieChart chart;
    private ObservableList<PieChart.Data> pieChartData;
    private final Player player;
    private final int totalValue;

    public StatisticsPopUp(Player p) {
        this.player = p;
        setX(160);
        setY(220);
        setWidth(600);
        setHeight(600);
        this.totalValue = player.getIndividualStatistics().getTotalValue() > 0 ? player.getIndividualStatistics().getTotalValue() : 1;

        setupLayout();
        getContent().add(chart);
    }

    private void setupLayout() {
        int wins = player.getIndividualStatistics().getTotalOfwins();
        int defeats = player.getIndividualStatistics().getTotalOfdefeats();
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(String.format("Jogos ganhos - %d (%d%%)", wins, 100 * wins / totalValue), wins),
                new PieChart.Data(String.format("Jogos perdidos - %d (%d%%)", defeats, 100 * defeats / totalValue), defeats));
        setupChart();
    }

    private void setupChart() {
        chart = new PieChart();
        chart.setTitle("Estatistica individual: " + player.getIdentification());
        chart.setData(pieChartData);
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.BOTTOM);
        chart.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        chart.setPrefSize(600, 470);
        chart.setStartAngle(90);
    }

}
