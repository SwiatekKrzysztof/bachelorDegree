package bachelorDegree.model;

import bachelorDegree.controller.MenuController;
import bachelorDegree.services.MenuService;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;


public enum FunctionBase {
    INSTANCE;

    double[][] C;
    double[][] E;

    public void findBase(String baseSize, String dimensions, String baseSymbol) throws IOException, ClassNotFoundException {
        if(MenuService.baseExists(baseSize,dimensions)){
            deserializeBase(baseSize,dimensions,baseSymbol);
        } else {
            createNewBase(baseSize,dimensions,baseSymbol);
            deserializeBase(baseSize,dimensions,baseSymbol);
        }
    }
    public void createNewBase(String baseSize, String dimensions, String baseSymbol) throws IOException, ClassNotFoundException {
        System.out.println("CREATING NEW BASE");
        double[][] testDoubleArray = new double[5][5];
        for (int i = 0; i < testDoubleArray.length; i++) {
            for (int j = 0; j < testDoubleArray.length; j++) {
                testDoubleArray[i][j] = i%(j+1);
            }
        }
        serializeBase(testDoubleArray,baseSize,dimensions,"C");
        serializeBase(testDoubleArray,baseSize,dimensions,"E");

    }
    //todo relocate serialization to another class
    private void serializeBase(double[][] base, String baseSize, String dimensions, String baseSymbol) throws IOException {
        FileOutputStream fos = new FileOutputStream("src/main/resources/bases/"+ baseSize + dimensions+ baseSymbol );
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(base);
    }
    private void deserializeBase(String baseSize, String dimensions, String baseSymbol) throws IOException, ClassNotFoundException {
        System.out.println("DESERIALIZING");
        FileInputStream fis = new FileInputStream("src/main/resources/bases/"+ baseSize +dimensions + baseSymbol);
        ObjectInputStream ois = new ObjectInputStream(fis);
        if(baseSymbol.equals("C")) {
            C = (double[][]) ois.readObject();
        }
        if(baseSymbol.equals("E")){
            E= (double[][]) ois.readObject();
        }
    }

    public void printMatrix(double[][] A,String name){
        System.out.println(name);
        for (int i = 0; i < A.length; i++) {
            for (double[] doubles : A) {
                System.out.printf("%.2f   ", doubles[i]);
            }
            System.out.println();
        }
        System.out.println("---");
    }

}
