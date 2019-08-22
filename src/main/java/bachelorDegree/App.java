package bachelorDegree;

import bachelorDegree.model.HarmonicOscillator;
import bachelorDegree.model.Oscillator;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{
    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Oscillator harmonicOscillator = new HarmonicOscillator();
        harmonicOscillator.setK(5.5);
        System.out.println(harmonicOscillator.getK());
    }
}
