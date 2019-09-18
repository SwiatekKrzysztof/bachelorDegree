package bachelorDegree.controller;

import bachelorDegree.model.HarmonicOscillator;
import bachelorDegree.model.Oscillator;
import bachelorDegree.services.OscillatorMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;
import org.jzy3d.analysis.AnalysisLauncher;
@Getter
@Setter
public class menuController {

    private ObservableList<String> oscillatorChoiceList = FXCollections.observableArrayList("Harmonic","Anharmonic");
    private ObservableList<String> dimensionChoiceList = FXCollections.observableArrayList("1D","2D");

    @FXML
    public TextArea info;
    @FXML
    public ChoiceBox<String> oscillatorChoiceBox;
    @FXML
    public ChoiceBox<String> dimensionsChoiceBox;
    @FXML
    public ComboBox functionBaseComboBox;
    @FXML
    public Button loadCreateButton;
    @FXML
    public TextField nxBox;
    @FXML
    public TextField nyBox;
    @FXML
    public TextField kyBox;
    @FXML
    public TextField kxBox;
    @FXML
    public TextField mBox;
    @FXML
    public Button visualizeButton;

    @FXML
    public void initialize(){
        oscillatorChoiceBox.setItems(oscillatorChoiceList);
        dimensionsChoiceBox.setItems(dimensionChoiceList);
        functionBaseComboBox.setDisable(true);
        loadCreateButton.setDisable(true);
        info.setDisable(true);
        visualizeButton.setDisable(true);
        functionBaseComboBox.setDisable(true);
        kyBox.setDisable(true);
        nyBox.setDisable(true);


        oscillatorChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v,oldValue,newValue)-> oscillatorChoiceChangeAction(newValue));
        dimensionsChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v,oldValue,newValue)-> dimensionChoiceChangeAction(newValue));

    }
    public void oscillatorChoiceChangeAction(String newValue){
        if(newValue.equals(oscillatorChoiceList.get(0))){
            menuChangesOscillator(true);
        } else {
            menuChangesOscillator(false);
        }
    }
    public void dimensionChoiceChangeAction(String newValue){
        if(newValue.equals(dimensionChoiceList.get(0))){
            menuChangesDimension(true);
        } else {
            menuChangesDimension(false);
        }
    }


    @FXML
    public void loadCreateAction(ActionEvent actionEvent) {

    }

    @FXML
    public void visualize(ActionEvent actionEvent) throws Exception {
        int nx = Integer.parseInt(nxBox.getText());
        if(!nyBox.isDisabled()) {
            int ny = Integer.parseInt(nyBox.getText());
        }
        int m = Integer.parseInt(mBox.getText());
        int kx = Integer.parseInt(kxBox.getText());
        if(!kyBox.isDisabled()) {
            int ky = Integer.parseInt(kyBox.getText());
        }
//        Oscillator oscillatorX = new HarmonicOscillator(5,1.0,1.0,2.0);
//        Oscillator oscillatorY = new HarmonicOscillator(5,1.0,1.0,2.0);
//        AnalysisLauncher.open(new OscillatorMapper().setParameters(oscillatorX,oscillatorY));
        System.out.println("nx= "+nx+" m="+m+"kx="+kx);

    }
    public void menuChangesOscillator(boolean isOscillatorHarmonic){
        bothOptionsChosen();
        functionBaseComboBox.setDisable(isOscillatorHarmonic);
        loadCreateButton.setDisable(isOscillatorHarmonic);
        info.setDisable(isOscillatorHarmonic);
    }
    public void menuChangesDimension(boolean isOscillatorOneDimensional){
        bothOptionsChosen();
        kyBox.setDisable(isOscillatorOneDimensional);
        nyBox.setDisable(isOscillatorOneDimensional);
    }
    private void bothOptionsChosen(){
        if(oscillatorChoiceBox.getValue()!=null&&dimensionsChoiceBox.getValue()!=null){
            System.out.println("Succes");
            visualizeButton.setDisable(false);
        }
    }
}
