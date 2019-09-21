package bachelorDegree.controller;

import bachelorDegree.model.AnharmonicOscillator;
import bachelorDegree.model.FunctionBasisSet;
import bachelorDegree.model.HarmonicOscillator;
import bachelorDegree.model.Oscillator;
import bachelorDegree.services.MenuService;
import bachelorDegree.services.Oscillator1DMapper;
import bachelorDegree.services.Oscillator2DMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.jzy3d.analysis.AnalysisLauncher;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Getter
@Setter
public class MenuController {

    private ObservableList<String> oscillatorChoiceList = FXCollections.observableArrayList("Harmonic", "Anharmonic");
    private ObservableList<String> dimensionChoiceList = FXCollections.observableArrayList("1D", "2D");
    private ObservableList<String> functionBasisSetComboList = FXCollections.observableArrayList();

    public static String oscillatorType; //= "Harmonic";
    public static String dimensions;
    public static String basisSetSize;

    private FunctionBasisSet basisSetX;
    private FunctionBasisSet basisSetY;
    private Oscillator oscillatorX;
    private Oscillator oscillatorY;

    public boolean isOptionQuick;
    @FXML
    public TextArea info;
    @FXML
    public ChoiceBox<String> oscillatorChoiceBox;
    @FXML
    public ChoiceBox<String> dimensionsChoiceBox;
    @FXML
    public ComboBox<String> functionBasisSetComboBox;
    @FXML
    public CheckBox quickCheckBox;
    @FXML
    public CheckBox advancedCheckBox;
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
    public TextField LBox;
    @FXML
    public TextField hBox;
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
    public Label basisSizeLabelQuick;
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

        //Listeners for text fields
        addListeners();

        checkOptions();
    }

    private void checkOptions() {
        //Checking if dimension, oscillator type and calculating option are selected
        //then enabling load and vis buttons
        necessaryOptionsChosen();
        //Checking if dimension, oscillator type are selected
        if (oscillatorChoiceBox.getValue() != null
                && dimensionsChoiceBox.getValue() != null) {
            quickCheckBox.setDisable(false);
            advancedCheckBox.setDisable(false);
        }

        setDisableAdvancedOnlyOptions(!advancedCheckBox.isSelected());
        setDisableQuickOnlyOptions(!quickCheckBox.isSelected());

        if(advancedCheckBox.isSelected()){
            boolean isItNotAnharmonic = !oscillatorChoiceBox.getValue().equals("Anharmonic");
            BBox.setDisable(isItNotAnharmonic);
            CBox.setDisable(isItNotAnharmonic);
            advancedBasisSetTextField.setDisable(isItNotAnharmonic);
            basisSizeLabelAdvanced.setDisable(isItNotAnharmonic);
            kyBox.setDisable(!dimensionsChoiceBox.getValue().equals("2D"));
        } else {
            BBox.setDisable(true);
            CBox.setDisable(true);
            advancedBasisSetTextField.setDisable(true);
            basisSizeLabelAdvanced.setDisable(true);
            kyBox.setDisable(true);
        }
        nyBox.setDisable(!dimensionsChoiceBox.getValue().equals("2D"));

        if(quickCheckBox.isSelected()){
            functionBasisSetComboBox.setDisable(!oscillatorChoiceBox.getValue().equals("Anharmonic"));
            basisSizeLabelQuick.setDisable(!oscillatorChoiceBox.getValue().equals("Anharmonic"));
        } else {
            functionBasisSetComboBox.setDisable(true);
            basisSizeLabelQuick.setDisable(true);
        }

        try {
            //todo sorting functionbasisSetComboList
            createFunctionBasisSetComboList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setDisableAdvancedOnlyOptions(boolean bool) {
        mBox.setDisable(bool);
        kxBox.setDisable(bool);
        hBox.setDisable(bool);
        LBox.setDisable(bool);
    }

    private void setDisableQuickOnlyOptions(boolean bool) {
        info.setDisable(bool);
    }

    @FXML
    public void loadCreateAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        setDefaultValues();
//        if (functionBasisSetComboBox.getSelectionModel().isEmpty()) {
//            functionBasisSetComboBox.setValue("500");
//        }
        if (!MenuService.basisSetExists(basisSetSize)) {
            functionBasisSetComboList.add(basisSetSize);
            System.out.println("DZIAłA");
        }
        //QUICK
        boolean oscillatorIsHarmonic = oscillatorChoiceBox.getValue().equals("Harmonic");
        boolean oscillatorIsAnharmonic = oscillatorChoiceBox.getValue().equals("Anharmonic");
        boolean dimensionIs1D = dimensionsChoiceBox.getValue().equals("1D");
        boolean dimensionIs2D = dimensionsChoiceBox.getValue().equals("2D");;

        if(quickCheckBox.isSelected())
        {
//            String nx = nxBox.getText();
//            String ny = nyBox.getText();
            String m = "1.0";
            String k = "1.0";
            String h = "1.0";
            String L = "70.0";
            String basisSetSize = functionBasisSetComboBox.getValue();

            if(oscillatorIsHarmonic) {
                oscillatorX = new HarmonicOscillator(m,k,h,L);
                if(dimensionIs2D)
                    oscillatorY = new HarmonicOscillator(m,k,h,L);
            } else if(oscillatorIsAnharmonic){
                basisSetX = new FunctionBasisSet();
                basisSetX.findBasisSet(basisSetSize);
                oscillatorX = new AnharmonicOscillator(basisSetX,L);
                if(dimensionIs2D) {
                    //todo maybe redundant
                    basisSetY = new FunctionBasisSet();
                    basisSetY = basisSetX;
                    oscillatorY = new AnharmonicOscillator(basisSetY,L);
                }
            }
        }
        else if(advancedCheckBox.isSelected())
        {
//            String nx = nxBox.getText();
//            String ny = nyBox.getText();
            String m = mBox.getText();
            String kx = kxBox.getText();
            String ky = kyBox.getText();
            String h = hBox.getText();
            String L = LBox.getText();
            String B = BBox.getText();
            String C = CBox.getText();
            String basisSetSize = advancedBasisSetTextField.getText();

            if(oscillatorIsHarmonic) {
                oscillatorX = new HarmonicOscillator(m,kx,h,L);
                if(dimensionIs2D)
                    oscillatorY = new HarmonicOscillator(m,ky,h,L);
            } else if(oscillatorIsAnharmonic){
                basisSetX = new FunctionBasisSet();
                basisSetX.createNewAdvancedBasicSet(m,kx,h,L,B,C,basisSetSize);
                oscillatorX = new AnharmonicOscillator(basisSetX,L);
                if(dimensionIs2D) {
                    basisSetY = new FunctionBasisSet();
                    basisSetY.createNewAdvancedBasicSet(m,ky,h,L,B,C,basisSetSize);
                    oscillatorY = new AnharmonicOscillator(basisSetY,L);
                }

            }
        }

        functionBasisSetComboBox.setItems(functionBasisSetComboList);

        //todo sorting functionbasisSetComboList

        checkOptions();
    }

    @FXML
    public void visualize(ActionEvent actionEvent) throws Exception {
        setDefaultValues();
        int nx = Integer.parseInt(nxBox.getText());
        int ny = Integer.parseInt(nyBox.getText());
//        double m = Double.parseDouble(mBox.getText());
//        double kx = Double.parseDouble(kxBox.getText());
//        double ky = Double.parseDouble(kyBox.getText());

        if (dimensionsChoiceBox.getValue().equals("1D")) {
            oscillatorX.setN(nx);
            Oscillator1DMapper mapper = new Oscillator1DMapper(oscillatorX);
            mapper.loadLineChartView();
        } else if(dimensionsChoiceBox.getValue().equals("2D")){
            oscillatorX.setN(nx);
            oscillatorY.setN(ny);
            AnalysisLauncher.open(new Oscillator2DMapper().setParameters(oscillatorX,oscillatorY));
        }
    }

//    private void loadLineChartView() {
//        Stage stage = new Stage();
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("/lineChartView.fxml"));
//
//        try {
//            fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Parent parent = fxmlLoader.getRoot();
//        stage.setScene(new Scene(parent));
//        stage.show();
//    }

    private void oscillatorChoiceChangeAction(String newValue) {
        if (newValue.equals(oscillatorChoiceList.get(0))) {
            menuChangesOscillator(true);
        } else {
            menuChangesOscillator(false);
        }
    }

    private void createFunctionBasisSetComboList() throws IOException {
        //todo replace with google reflection
        List<String> basisSetList = IOUtils.readLines(
                Objects.requireNonNull
                        (MenuController.class.getClassLoader().getResourceAsStream("basisSets/")), Charsets.UTF_8);
        basisSetList = MenuService.chopList(basisSetList);
        basisSetList = basisSetList.stream().distinct().collect(Collectors.toList());

        System.out.println(basisSetList);
        functionBasisSetComboList.setAll(basisSetList);
        functionBasisSetComboBox.setItems(functionBasisSetComboList);
    }

    private void addListeners() {
        //Listeners for text fields
        oscillatorChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> oscillatorChoiceChangeAction(newValue));
        oscillatorChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(((v, oldValue, newValue) -> oscillatorType = newValue));

        dimensionsChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> dimensionChoiceChangeAction(newValue));
        dimensionsChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> dimensions = newValue);
        functionBasisSetComboBox.getSelectionModel().
                selectedItemProperty()
                .addListener((v, oldValue, newValue) -> basisSetSize = newValue);
        functionBasisSetComboBox.getSelectionModel().
                selectedItemProperty()
                .addListener((v, oldValue, newValue) -> functionBasisSetComboBox.setItems(functionBasisSetComboList));
    }

    private void dimensionChoiceChangeAction(String newValue) {
        if (newValue.equals(dimensionChoiceList.get(0))) {
            menuChangesDimension(true);
        } else {
            menuChangesDimension(false);
        }
    }
    //todo maybe redundant?
    private void menuChangesOscillator(boolean isOscillatorHarmonic) {
        checkOptions();
    }
    //todo maybe redundant?
    private void menuChangesDimension(boolean isOscillatorOneDimensional) {
        checkOptions();
    }

    private void necessaryOptionsChosen() {
        if (oscillatorChoiceBox.getValue() != null
                && dimensionsChoiceBox.getValue() != null
                && (advancedCheckBox.isSelected() || quickCheckBox.isSelected())) {
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
        if (hBox.getText().equals("")) {
            hBox.setText("1.0");
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
            advancedBasisSetTextField.setText("100");
        }
    }

    @FXML
    public void quickCheckBoxAction(ActionEvent actionEvent) {
        advancedCheckBox.setSelected(false);
        checkOptions();
    }

    @FXML
    public void advancedCheckBoxAction(ActionEvent actionEvent) {
        quickCheckBox.setSelected(false);
        checkOptions();
    }

    public void aboutAction(ActionEvent actionEvent) {
        //todo about program and author
    }
}
