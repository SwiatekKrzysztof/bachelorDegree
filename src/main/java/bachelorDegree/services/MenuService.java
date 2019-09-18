package bachelorDegree.services;

import bachelorDegree.controller.MenuController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MenuService {
    public static boolean checkIfHarmonic(){
        return MenuController.oscillatorType.equals("Harmonic");
    }
    public static boolean checkIf1D(){
        return MenuController.dimensions.equals("1D");
    }
    public static boolean baseExists(String baseSize){
        return Files.exists(Paths.get("src/main/resources/bases/" + baseSize));
    }
}
