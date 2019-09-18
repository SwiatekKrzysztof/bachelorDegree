package bachelorDegree.services;

import bachelorDegree.controller.MenuController;

public class MenuService {
    public static boolean checkIfHarmonic(){
        return MenuController.oscillatorType.equals("Harmonic");
    }
    public static boolean checkIf1D(){
        return MenuController.dimensions.equals("1D");
    }
}
