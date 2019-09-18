package bachelorDegree.controller;

import bachelorDegree.services.MenuService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import static bachelorDegree.services.MenuService.checkIf1D;
import static bachelorDegree.services.MenuService.checkIfHarmonic;

public class LineChartController {
    @FXML
    public LineChart<?,?> lineChart;
    @FXML
    public CategoryAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public Button createImageButton;

    public void createImageAction(ActionEvent actionEvent) {

    }
    public void initialize(){
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>("1",23.0));
        series.getData().add(new XYChart.Data<>("5",29.0));
        lineChart.getData().addAll(series);
        lineChart.setCreateSymbols(false);

        System.out.println(checkIfHarmonic());
        System.out.println(checkIf1D());
        System.out.println(MenuController.baseSize);
    }
    private void map1D(){

    }
    private void map2D(){

    }
}
