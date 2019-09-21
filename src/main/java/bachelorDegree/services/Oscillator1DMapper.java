package bachelorDegree.services;

import bachelorDegree.controller.LineChartController;
import bachelorDegree.model.Oscillator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
@Getter
@Setter
public class Oscillator1DMapper {
    Oscillator oscillator;
    public Oscillator1DMapper(Oscillator oscillator) {
        this.oscillator = oscillator;
    }

    public void loadLineChartView() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/lineChartView.fxml"));

        LineChartController controller = new LineChartController(this,oscillator);
        fxmlLoader.setController(controller);

        fxmlLoader.load();
        Parent root = fxmlLoader.getRoot();
        stage.setScene(new Scene(root));
        stage.show();
//        LineChartController controller = fxmlLoader.<LineChartController>getController();
//        controller.setMapper(this);
//        controller.setOscillator(oscillator);
//
//        Parent parent = fxmlLoader.getRoot();
//        stage.setScene(new Scene(parent));
//        stage.show();
    }

    public Oscillator getOscillator() {
        return oscillator;
    }

    public void setOscillator(Oscillator oscillator) {
        this.oscillator = oscillator;
    }
}
