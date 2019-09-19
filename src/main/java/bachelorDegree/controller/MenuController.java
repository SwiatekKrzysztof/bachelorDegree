package bachelorDegree.controller;

import bachelorDegree.services.MenuService;
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

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static bachelorDegree.model.FunctionBasisSet.*;

@Getter
@Setter
public class MenuController {

    private ObservableList<String> oscillatorChoiceList = FXCollections.observableArrayList("Harmonic", "Anharmonic");
    private ObservableList<String> dimensionChoiceList = FXCollections.observableArrayList("1D", "2D");
    private ObservableList<String> functionBasisSetComboList = FXCollections.observableArrayList();

    public static String oscillatorType;
    public static String dimensions;
    public static String basisSetSize;
    @FXML
    public TextArea info;
    @FXML
    public ChoiceBox<String> oscillatorChoiceBox;
    @FXML
    public ChoiceBox<String> dimensionsChoiceBox;
    @FXML
    public ComboBox<String> functionBasisSetComboBox;
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
    public void initialize() {
        oscillatorChoiceBox.setItems(oscillatorChoiceList);
        dimensionsChoiceBox.setItems(dimensionChoiceList);
        try {
            createFunctionBasisSetComboList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //functionbasisSetComboBox.setItems(functionbasisSetComboList);

        functionBasisSetComboBox.setDisable(true);
        loadCreateButton.setDisable(true);
        info.setDisable(true);
        visualizeButton.setDisable(true);
        functionBasisSetComboBox.setDisable(true);
        kyBox.setDisable(true);
        nyBox.setDisable(true);

        //Listeners for text fields
        addListeners();
    }

    @FXML
    public void loadCreateAction(ActionEvent actionEvent) throws IOException {
        if (functionBasisSetComboBox.getSelectionModel().isEmpty()) {
            functionBasisSetComboBox.setValue("500");
        }
        if(!MenuService.basisSetExists(basisSetSize)) {
            functionBasisSetComboList.add(basisSetSize);
        }
        try {
            INSTANCE.findBasisSet(basisSetSize,"C");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        functionBasisSetComboBox.setItems(functionBasisSetComboList);
        //todo sorting functionbasisSetComboList
        //createFunctionbasisSetList();
       // functionbasisSetComboList.setAll(functionbasisSetComboList.stream()
        //        .sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
    }

    @FXML
    public void visualize(ActionEvent actionEvent) throws Exception {
        setDefaultValues();
        int nx = Integer.parseInt(nxBox.getText());
        double m = Double.parseDouble(mBox.getText());
        double kx = Double.parseDouble(kxBox.getText());
        int ny = Integer.parseInt(nyBox.getText());
        double ky = Double.parseDouble(kyBox.getText());

        if(dimensionsChoiceBox.getValue().equals("1D")){
            loadLineChartView();
        }
//        if(oscillatorChoiceBox.getValue().equals("Harmonic")){
//
//        } else {
//
//        }
//        Oscillator oscillatorX = new HarmonicOscillator(5,1.0,1.0,2.0);
//        Oscillator oscillatorY = new HarmonicOscillator(5,1.0,1.0,2.0);
//        AnalysisLauncher.open(new OscillatorMapper().setParameters(oscillatorX,oscillatorY));


    }

    private void loadLineChartView(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/lineChartView.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent parent = fxmlLoader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    private void oscillatorChoiceChangeAction(String newValue) {
        if (newValue.equals(oscillatorChoiceList.get(0))) {
            menuChangesOscillator(true);
        } else {
            menuChangesOscillator(false);
        }
    }

    private void createFunctionBasisSetComboList() throws IOException {
        //functionbasisSetComboBox.getItems().removeAll();
        //todo replace with google reflection
        List<String> basisSetList = IOUtils.readLines(
                Objects.requireNonNull
                        (MenuController.class.getClassLoader().getResourceAsStream("basisSets/")), Charsets.UTF_8);
        //functionbasisSetComboList.forEach(a->a=null);
        basisSetList = MenuService.chopList(basisSetList);
        basisSetList = basisSetList.stream().distinct().collect(Collectors.toList());
//        Pattern pattern = Pattern.compile("\\d*"+dimensionsChoiceBox.getValue());
//        System.out.println(pattern);
//        basisSetList = basisSetList.stream()
//                .filter(pattern.asPredicate())
//                .collect(Collectors.toList());
        System.out.println(basisSetList);
        functionBasisSetComboList.setAll(basisSetList);
        functionBasisSetComboBox.setItems(functionBasisSetComboList);
    }

    private void addListeners(){
        //Listeners for text fields
        oscillatorChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> oscillatorChoiceChangeAction(newValue));
        oscillatorChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(((v,oldValue,newValue)->oscillatorType = newValue));

        dimensionsChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> dimensionChoiceChangeAction(newValue));
        dimensionsChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((v, oldValue, newValue) -> dimensions = newValue);
        functionBasisSetComboBox.getSelectionModel().
                selectedItemProperty()
                .addListener((v,oldValue,newValue)-> basisSetSize = newValue);
        functionBasisSetComboBox.getSelectionModel().
                selectedItemProperty()
                .addListener((v,oldValue,newValue)-> functionBasisSetComboBox.setItems(functionBasisSetComboList));
    }

    private void dimensionChoiceChangeAction(String newValue) {
        if (newValue.equals(dimensionChoiceList.get(0))) {
            menuChangesDimension(true);
        } else {
            menuChangesDimension(false);
        }
    }

    private void menuChangesOscillator(boolean isOscillatorHarmonic) {
        bothOptionsChosen();
        functionBasisSetComboBox.setDisable(isOscillatorHarmonic);
        loadCreateButton.setDisable(isOscillatorHarmonic);
        info.setDisable(isOscillatorHarmonic);
    }

    private void menuChangesDimension(boolean isOscillatorOneDimensional) {
        bothOptionsChosen();
        kyBox.setDisable(isOscillatorOneDimensional);
        nyBox.setDisable(isOscillatorOneDimensional);
    }

    private void bothOptionsChosen(){
        if (oscillatorChoiceBox.getValue() != null && dimensionsChoiceBox.getValue() != null) {
            visualizeButton.setDisable(false);
            try {
                createFunctionBasisSetComboList();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

//        if (!nyBox.isDisabled()) {
            if (nyBox.getText().equals("")) {
                nyBox.setText("0");
            }
            if (kyBox.getText().equals("")) {
                kyBox.setText("1.0");
            }
//        }
    }

}
