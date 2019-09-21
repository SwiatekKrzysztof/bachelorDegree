package bachelorDegree.controller;

import bachelorDegree.model.HarmonicOscillator;
import bachelorDegree.model.Oscillator;
import bachelorDegree.services.Oscillator1DMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import lombok.Setter;

import static bachelorDegree.services.MenuService.checkIf1D;
import static bachelorDegree.services.MenuService.checkIfHarmonic;
@Setter
public class LineChartController {
    private Oscillator1DMapper mapper;
    private Oscillator oscillator;
    @FXML
    public LineChart<?,?> lineChart;
    @FXML
    public CategoryAxis xAxis;
    @FXML
    public NumberAxis yAxis;
    @FXML
    public Button createImageButton;

    public LineChartController(Oscillator1DMapper mapper, Oscillator oscillator) {
        this.mapper = mapper;
        this.oscillator = oscillator;
    }

    public void createImageAction(ActionEvent actionEvent) {

    }
    public void initialize(){
        XYChart.Series series = new XYChart.Series<>();
        for (int i = 0; i < 10; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i),getOscillator().getValueOfArgument(i)));
           // series.getData().add(new XYChart.Data<>(String.valueOf(i),getMapper.getOscillator().getValueOfArgument(i)));
        }
//        series.getData().add(new XYChart.Data<>("1",23.0));
//        series.getData().add(new XYChart.Data<>("5",29.0));
        lineChart.getData().addAll(series);
        lineChart.setCreateSymbols(false);

        System.out.println(checkIfHarmonic());
        System.out.println(checkIf1D());
        System.out.println(MenuControllerOld.basisSetSize);
    }

    public void setMapper(Oscillator1DMapper mapper) {
        this.mapper = mapper;
    }

    public Oscillator1DMapper getMapper() {
        return mapper;
    }

    public Oscillator getOscillator() {
        return oscillator;
    }

    public void setOscillator(Oscillator oscillator) {
        this.oscillator = oscillator;
    }
}
