package bachelorDegree.controller;

import bachelorDegree.model.Oscillator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import lombok.Setter;

@Setter
public class LineChartController{
    //private Oscillator1DMapper mapper;
    //private Oscillator oscillator;
    @FXML
    public LineChart<String,Double> lineChart;
    @FXML
    public CategoryAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public Button createImageButton;
    private XYChart.Series<String, Double> series = new XYChart.Series<>();

    public void createImageAction(ActionEvent actionEvent) {

    }
    public void initialize(){
        lineChart.animatedProperty().setValue(true);
        lineChart.setCreateSymbols(false);
        //lineChart.setLegendVisible(false);


    }
    public void addData(Oscillator oscillator){
          double L = dynamicRange(oscillator.getN());
        for (double i = -L/2; i < (L/2); i= i+0.05) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i),Math.pow(oscillator.getValueOfArgument(i),2)));
        }
        lineChart.getData().addAll(series);
    }
    public static double dynamicRange(int n){
        return (n)/6.0 + 5.0;
    }
}
