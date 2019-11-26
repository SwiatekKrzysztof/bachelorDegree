package bachelorDegree.controller;

import bachelorDegree.model.Oscillator;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import lombok.Setter;

@Setter
public class LineChartController {
    @FXML
    public LineChart<Double, Double> lineChart;
    @FXML
    public NumberAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    private XYChart.Series<Double, Double> series = new XYChart.Series<>();

    public void initialize() {
        lineChart.animatedProperty().setValue(true);
        lineChart.setCreateSymbols(false);
        //lineChart.setStyle("-fx-stroke: #00000f;");

    }

    public void addData(Oscillator oscillator) {
        double L = dynamicRange(oscillator.getN());
        for (double i = -L; i < (L); i = i + 0.05) {
            series.getData().add(new XYChart.Data<>(i, Math.pow(oscillator.getValueOfArgument(i), 2)));
        }
        lineChart.getData().addAll(series);
    }
//
//    public void addData(Oscillator oscillator) {
//        double L = dynamicRange(oscillator.getN());
//        for (double i = -L / 2; i < (L / 2); i = i + 0.05) {
//            series.getData().add(new XYChart.Data<>(String.valueOf(i), Math.pow(oscillator.getValueOfArgument(i), 2)));
//        }
//        lineChart.getData().addAll(series);
//    }

    public static double dynamicRange(int n) {
        return (n) / 6.0 + 5.0;
    }
}
