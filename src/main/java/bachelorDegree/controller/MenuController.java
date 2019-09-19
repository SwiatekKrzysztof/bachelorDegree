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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static bachelorDegree.model.FunctionBase.*;

@Getter
@Setter
public class MenuController {

    private ObservableList<String> oscillatorChoiceList = FXCollections.observableArrayList("Harmonic", "Anharmonic");
    private ObservableList<String> dimensionChoiceList = FXCollections.observableArrayList("1D", "2D");
    private ObservableList<String> functionBaseComboList = FXCollections.observableArrayList();

    public static String oscillatorType;
    public static String dimensions;
    public static String baseSize;
    @FXML
    public TextArea info;
    @FXML
    public ChoiceBox<String> oscillatorChoiceBox;
    @FXML
    public ChoiceBox<String> dimensionsChoiceBox;
    @FXML
    public ComboBox<String> functionBaseComboBox;
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
            createFunctionBaseComboList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //functionBaseComboBox.setItems(functionBaseComboList);

        functionBaseComboBox.setDisable(true);
        loadCreateButton.setDisable(true);
        info.setDisable(true);
        visualizeButton.setDisable(true);
        functionBaseComboBox.setDisable(true);
        kyBox.setDisable(true);
        nyBox.setDisable(true);

        //Listeners for text fields
        addListeners();
    }

    @FXML
    public void loadCreateAction(ActionEvent actionEvent) throws IOException {
        if (functionBaseComboBox.getSelectionModel().isEmpty()) {
            functionBaseComboBox.setValue("500");
        }
        if(!MenuService.baseExists(baseSize,dimensions)) {
            functionBaseComboList.add(baseSize);
        }
        try {
            INSTANCE.findBase(baseSize, dimensions,"C");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        functionBaseComboBox.setItems(functionBaseComboList);
        //todo sorting functionBaseComboList
        //createFunctionBaseList();
       // functionBaseComboList.setAll(functionBaseComboList.stream()
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

    private void createFunctionBaseComboList() throws IOException {
        //functionBaseComboBox.getItems().removeAll();
        //todo replace with google reflection
        List<String> baseList = IOUtils.readLines(
                Objects.requireNonNull
                        (MenuController.class.getClassLoader().getResourceAsStream("bases/")), Charsets.UTF_8);
        //functionBaseComboList.forEach(a->a=null);
        baseList = MenuService.chopList(baseList);
        baseList = baseList.stream().distinct().collect(Collectors.toList());
        Pattern pattern = Pattern.compile("\\d*"+dimensionsChoiceBox.getValue());
        System.out.println(pattern);
        baseList = baseList.stream()
                .filter(pattern.asPredicate())
                .collect(Collectors.toList());
        System.out.println(baseList);
        functionBaseComboList.setAll(baseList);
        functionBaseComboBox.setItems(functionBaseComboList);
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
        functionBaseComboBox.getSelectionModel().
                selectedItemProperty()
                .addListener((v,oldValue,newValue)-> baseSize = newValue);
        functionBaseComboBox.getSelectionModel().
                selectedItemProperty()
                .addListener((v,oldValue,newValue)-> functionBaseComboBox.setItems(functionBaseComboList));
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
        functionBaseComboBox.setDisable(isOscillatorHarmonic);
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
                createFunctionBaseComboList();
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
