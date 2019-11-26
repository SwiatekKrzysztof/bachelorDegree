package bachelorDegree.controller;

import bachelorDegree.model.AnharmonicOscillator;
import bachelorDegree.model.FunctionBasisSet;
import bachelorDegree.model.HarmonicOscillator;
import bachelorDegree.model.Oscillator;
import bachelorDegree.service.Oscillator1DMapper;
import bachelorDegree.service.Oscillator2DMapper;
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
public class MenuController {

    private ObservableList<String> oscillatorChoiceList = FXCollections.observableArrayList("Harmonic", "Anharmonic");
    private ObservableList<String> dimensionChoiceList = FXCollections.observableArrayList("1D", "2D");

    private FunctionBasisSet basisSetX;
    private FunctionBasisSet basisSetY;
    private Oscillator oscillatorX;
    private Oscillator oscillatorY;

    public boolean isOptionQuick;
    @FXML
    public ChoiceBox<String> oscillatorChoiceBox;
    @FXML
    public ChoiceBox<String> dimensionsChoiceBox;
    @FXML
    public Button loadCreateButton;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Label successMessage;
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
    public TextField LBox;
    @FXML
    public TextField BBox;
    @FXML
    public TextField CBox;
    @FXML
    public Button visualizeButton;
    @FXML
    public Label BLabel;
    @FXML
    public Label CLabel;
    @FXML
    public Label basisSizeLabelAdvanced;
    @FXML
    public Button aboutButton;
    @FXML
    public TextField advancedBasisSetTextField;

    public void initialize() {
        oscillatorChoiceBox.setItems(oscillatorChoiceList);
        dimensionsChoiceBox.setItems(dimensionChoiceList);

        oscillatorChoiceBox.setValue("Harmonic");
        dimensionsChoiceBox.setValue("1D");

        addListeners();

        checkOptions();
    }

    private void checkOptions() {
        //Checking if dimension, oscillator type and calculating option are selected
        //then enabling load and vis buttons
        necessaryOptionsChosen();
        //Checking if dimension, oscillator type are selected

        boolean isItNotAnharmonic = !oscillatorChoiceBox.getValue().equals("Anharmonic");
        BBox.setDisable(isItNotAnharmonic);
        CBox.setDisable(isItNotAnharmonic);
        advancedBasisSetTextField.setDisable(isItNotAnharmonic);
        basisSizeLabelAdvanced.setDisable(isItNotAnharmonic);
        kyBox.setDisable(!dimensionsChoiceBox.getValue().equals("2D"));
        nyBox.setDisable(!dimensionsChoiceBox.getValue().equals("2D"));
        visualizeButton.setDisable(!(progressBar.getProgress() == 1.0));
    }

    @FXML
    public void loadCreateAction(ActionEvent actionEvent) {
        setDefaultValues();
        progressBar.setVisible(true);

        boolean oscillatorIsHarmonic = oscillatorChoiceBox.getValue().equals("Harmonic");
        boolean oscillatorIsAnharmonic = oscillatorChoiceBox.getValue().equals("Anharmonic");

        boolean dimensionIs2D = dimensionsChoiceBox.getValue().equals("2D");

        String m = mBox.getText();
        String kx = kxBox.getText();
        String ky = kyBox.getText();
        String L = LBox.getText();
        String B = BBox.getText();
        String C = CBox.getText();
        String basisSetSize = advancedBasisSetTextField.getText();

        progressBar.setProgress(0.2);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (oscillatorIsHarmonic) {
            oscillatorX = new HarmonicOscillator(m, kx, L);

            progressBar.setProgress(0.5);
            if (dimensionIs2D)
                oscillatorY = new HarmonicOscillator(m, ky, L);
        } else if (oscillatorIsAnharmonic) {
            basisSetX = new FunctionBasisSet();
            basisSetX.createNewAdvancedBasicSet(m, kx, L, B, C, basisSetSize);
            oscillatorX = new AnharmonicOscillator(basisSetX, L);

            progressBar.setProgress(0.5);
            if (dimensionIs2D) {
                basisSetY = new FunctionBasisSet();
                basisSetY.createNewAdvancedBasicSet(m, ky, L, B, C, basisSetSize);
                oscillatorY = new AnharmonicOscillator(basisSetY, L);
            }

        }
        progressBar.setProgress(1.0);
        successMessage.setVisible(true);
        checkOptions();
    }

    @FXML
    public void visualize() throws Exception {
        setDefaultValues();
        int nx = Integer.parseInt(nxBox.getText());
        int ny = Integer.parseInt(nyBox.getText());

        if (dimensionsChoiceBox.getValue().equals("1D")) {
            oscillatorX.setN(nx);
            Oscillator1DMapper mapper = new Oscillator1DMapper(oscillatorX);
            mapper.loadLineChartView();
        } else if (dimensionsChoiceBox.getValue().equals("2D")) {
            oscillatorX.setN(nx);
            oscillatorY.setN(ny);
            AnalysisLauncher.open(new Oscillator2DMapper().setParameters(oscillatorX, oscillatorY));
        }
    }

    private void addListeners() {
        //Listeners for text fields
        oscillatorChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        dimensionsChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        advancedBasisSetTextField.textProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        kxBox.textProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        kyBox.textProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        mBox.textProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        LBox.textProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        BBox.textProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());

        CBox.textProperty()
                .addListener((v, oldValue, newValue) -> menuChoiceChangeAction());
    }

    private void menuChoiceChangeAction() {
        progressBar.setProgress(0.0);
        successMessage.setVisible(false);
        checkOptions();
    }

    private void necessaryOptionsChosen() {
        if (oscillatorChoiceBox.getValue() != null
                && dimensionsChoiceBox.getValue() != null) {
            visualizeButton.setDisable(false);
            loadCreateButton.setDisable(false);
        } else {
            visualizeButton.setDisable(true);
            loadCreateButton.setDisable(true);
        }
    }

    private void setDefaultValues() {
        if (nxBox.getText().equals("")) {
            nxBox.setText("0");
        }

        if (kxBox.getText().equals("")) {
            kxBox.setText("1.0");
        }

        if (mBox.getText().equals("")) {
            mBox.setText("1.0");
        }

        if (nyBox.getText().equals("")) {
            nyBox.setText("0");
        }

        if (kyBox.getText().equals("")) {
            kyBox.setText("1.0");
        }

        if (BBox.getText().equals("")) {
            BBox.setText("1.0");
        }

        if (CBox.getText().equals("")) {
            CBox.setText("1.0");
        }

        if (LBox.getText().equals("")) {
            LBox.setText("70.0");
        }

        if (advancedBasisSetTextField.getText().equals("")) {
            advancedBasisSetTextField.setText("300");
        }
    }

    public void aboutAction(ActionEvent actionEvent) {
        //todo about program and author
    }
}
