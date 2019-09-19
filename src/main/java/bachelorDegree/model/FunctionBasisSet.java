package bachelorDegree.model;

import bachelorDegree.services.MenuService;

import java.io.*;

import static bachelorDegree.services.SerializationService.deserializeBasisSet;
import static bachelorDegree.services.SerializationService.serializeBasisSet;


public enum FunctionBasisSet {
    INSTANCE;

    double[][] C;
    double[][] E;

    public void findBasisSet(String basisSetSize, String basisSetSymbol) throws IOException, ClassNotFoundException {
        if(MenuService.basisSetExists(basisSetSize)){
            deserializeBasisSet(basisSetSize,basisSetSymbol);
        } else {
            createNewBasisSet(basisSetSize,basisSetSymbol);
            deserializeBasisSet(basisSetSize,basisSetSymbol);
        }
    }
    public void createNewBasisSet(String basisSetSize, String basisSetSymbol) throws IOException, ClassNotFoundException {
        System.out.println("CREATING NEW "+basisSetSize);
        double[][] testDoubleArray = new double[5][5];
        for (int i = 0; i < testDoubleArray.length; i++) {
            for (int j = 0; j < testDoubleArray.length; j++) {
                testDoubleArray[i][j] = i%(j+1);
            }
        }
        serializeBasisSet(testDoubleArray,basisSetSize,"C");
        serializeBasisSet(testDoubleArray,basisSetSize,"E");

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

    public double[][] getC() {
        return C;
    }

    public void setC(double[][] c) {
        C = c;
    }

    public double[][] getE() {
        return E;
    }

    public void setE(double[][] e) {
        E = e;
    }
}
