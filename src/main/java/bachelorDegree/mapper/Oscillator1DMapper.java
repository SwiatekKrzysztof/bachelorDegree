package bachelorDegree.mapper;

import bachelorDegree.controller.LineChartController;
import bachelorDegree.model.Oscillator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
@Data
public class Oscillator1DMapper {
    Oscillator oscillator;
    public Oscillator1DMapper(Oscillator oscillator) {
        this.oscillator = oscillator;
    }

    public void loadLineChartView() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lineChartView.fxml"));
        Parent parent = fxmlLoader.load();

        LineChartController controller = (LineChartController) fxmlLoader.getController();
        controller.addData(oscillator);
        stage.setTitle(oscillator.getN()+" state visualization");
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
