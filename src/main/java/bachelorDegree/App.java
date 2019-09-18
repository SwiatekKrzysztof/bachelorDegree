package bachelorDegree;

import bachelorDegree.model.HarmonicOscillator;
import bachelorDegree.model.Oscillator;
import bachelorDegree.services.OscillatorMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jzy3d.analysis.AnalysisLauncher;

public class App extends Application
{
    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root =
                FXMLLoader.load(
                        getClass()
                                .getResource("/bachelorView.fxml"));

        primaryStage.setTitle("Visulizer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
