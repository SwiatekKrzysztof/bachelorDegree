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

    }

    public void addData(Oscillator oscillator) {
        double L = dynamicRange(oscillator.getN());
        for (double i = -L; i < (L); i = i + 0.05) {
            series.getData().add(new XYChart.Data<>(i, Math.pow(oscillator.getValueOfArgument(i), 2)));
        }
        lineChart.getData().addAll(series);
    }

    private static double dynamicRange(int n) {
        return (n) / 6.0 + 5.0;
    }
}
