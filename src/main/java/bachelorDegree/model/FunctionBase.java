package bachelorDegree.model;

import bachelorDegree.controller.MenuController;
import bachelorDegree.services.MenuService;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;


public enum FunctionBase {
    INSTANCE;

    RealMatrix C;
    RealMatrix E;
    public double[][] getBase(String baseSize) throws IOException, ClassNotFoundException {
        if(MenuService.baseExists(baseSize)){
            return deserializeBase(baseSize);
        } else {
            createNewBase(Integer.parseInt(baseSize));
            return deserializeBase(baseSize);
        }
    }
    public void createNewBase(int baseSize) throws IOException, ClassNotFoundException {
        System.out.println("CREATING NEW BASE");
        double[][] testDoubleArray = new double[5][5];
        for (int i = 0; i < testDoubleArray.length; i++) {
            for (int j = 0; j < testDoubleArray.length; j++) {
                testDoubleArray[i][j] = i%(j+1);
            }
        }
        serializeBase(testDoubleArray,MenuController.baseSize);

    }

    private void serializeBase(double[][] base, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream("src/main/resources/bases/"+ fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(base);
    }

    private double[][] deserializeBase(String fileName) throws IOException, ClassNotFoundException {
        System.out.println("DESERIALIZING");
        FileInputStream fis = new FileInputStream("src/main/resources/bases/"+ fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return (double[][]) ois.readObject();
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
