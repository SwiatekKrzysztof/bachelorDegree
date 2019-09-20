package bachelorDegree.model;

import bachelorDegree.services.MenuService;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;
import java.util.ArrayList;

import static bachelorDegree.services.SerializationService.deserializeBasisSet;
import static bachelorDegree.services.SerializationService.serializeBasisSet;

//todo change to class for every AnharmonicOscillator have to have it's own base (k can be different for each)
//todo add field to AnharmonicOscillator
public enum FunctionBasisSet {
    INSTANCE;

    private RealMatrix C;
    private RealMatrix E;
    private ArrayList<Double> ESorted;
    private ArrayList<Integer> vectorsMap;

    public void findBasisSet(String basisSetSize, String basisSetSymbol) throws IOException, ClassNotFoundException {
        if (MenuService.basisSetExists(basisSetSize)) {
            deserializeBasisSet(basisSetSize, basisSetSymbol);
        } else {
            createNewBasisSet(basisSetSize, basisSetSymbol);
            deserializeBasisSet(basisSetSize, basisSetSymbol);
            printMatrix(C.getData(),"C");
        }
    }

    public void createNewBasisSet(String basisSetSize, String basisSetSymbol) throws IOException, ClassNotFoundException {
        System.out.println("CREATING NEW " + basisSetSize);
        double[][] testDoubleArray = new double[5][5];
        for (int i = 0; i < testDoubleArray.length; i++) {
            for (int j = 0; j < testDoubleArray.length; j++) {
                testDoubleArray[i][j] = i % (j + 1);
            }
        }
        serializeBasisSet(new BlockRealMatrix(testDoubleArray), basisSetSize, "C");
        serializeBasisSet(new BlockRealMatrix(testDoubleArray), basisSetSize, "E");
    }



    //REFACTOR

    private void createMatrices(int basisSetSize, double L, double k, double m){
//        this.k = k;
//        this.L = L;
        RealMatrix A = new BlockRealMatrix(basisSetSize,basisSetSize);
        int sum;
        for (int i = 1; i <= basisSetSize; i++) {
            for (int j = 1; j <= basisSetSize; j++) {
                sum = j+i;
                if(j==i){
                    A.addToEntry(j-1,i-1,0);//L/2.0
                }else if((sum)%2!=0){
                    double increment = (-8.0*L*j*i)/(Math.PI*Math.PI*Math.pow((j*j-i*i),2.0));
                    A.addToEntry(j-1,i-1,increment);
                }
            }
        }
        //todo why k is h?
        RealMatrix H = createHMatrix(A,k,basisSetSize,L,m,k);
        EigenDecomposition decomposition = new EigenDecomposition(H);
        this.C = decomposition.getV();
        
        RealMatrix EDiag = decomposition.getD();

//        printMatrix(A,"A");
//        printMatrix(this.C,"C");
//        printMatrix(this.EDiag,"E Diagonala");
        getMapOfDiagonalMatrixAndVector(EDiag);
    }
    private void getMapOfDiagonalMatrixAndVector(RealMatrix diagonal){
        ArrayList<Integer> vectorsMap = new ArrayList<>();
        ArrayList<Double> diagonalArray = new ArrayList<>();
        RealMatrix diagonalTemp = diagonal.copy();

        int minValueIndex = -1;
        for (int i = 0; i < diagonal.getRowDimension(); i++) {
            double minValue = Double.MAX_VALUE-1;
            for (int j = 0; j < diagonal.getRowDimension(); j++) {
                if(minValue>diagonalTemp.getEntry(j,j)){
                    minValue = diagonalTemp.getEntry(j,j);
                    minValueIndex = j;
                }
            }
            diagonalTemp.setEntry(minValueIndex,minValueIndex,Double.MAX_VALUE);

            vectorsMap.add(minValueIndex);
            diagonalArray.add(minValue);
        }

        diagonalArray.forEach(System.out::println);
        System.out.println("---");
        vectorsMap.forEach(System.out::println);

        this.ESorted = diagonalArray;

        this.vectorsMap = vectorsMap;
    }
    private RealMatrix createFunctionBase(int basisSetSize, double k){
        double[] doubleBase = new double[basisSetSize];
        for (int i = 0; i < doubleBase.length; i++) {
            doubleBase[i] = (k)/2.0;
        }
        return new DiagonalMatrix(doubleBase,false);
    }

    private RealMatrix createHMatrix(RealMatrix A,double k,int basisSetSize, double L, double m, double h){
        RealMatrix base = createFunctionBase(basisSetSize,k);

        EigenDecomposition decomposition = new EigenDecomposition(A);
        RealMatrix T = decomposition.getV();
        RealMatrix Ttrans = decomposition.getVT();
        RealMatrix D = decomposition.getD();
        RealMatrix Vdiag = createVDiagMatrix(D,base);

        RealMatrix V = T.multiply(Vdiag).multiply(Ttrans).copy(); // Potential Energy
        RealMatrix K = createKMatrix(basisSetSize,L,m,h); //Kinetic Energy
        RealMatrix H = V.add(K);

//        printMatrix(T,"T");
//        printMatrix(Ttrans,"T Transponowana");
//        printMatrix(D,"D");
//        printMatrix(Vdiag,"V Diagonalna");
//        printMatrix(V,"V");
//        printMatrix(K,"K");
//        printMatrix(H,"H");
        return H;
    }
        //todo isn't h=1?
    private  RealMatrix createKMatrix(int basisSetSize,double L, double m,double h) { //Kinetic Energy
        double[] doubleK = new double[basisSetSize];
        double multiplier = ( (h*h)/(2.0*m) ) * Math.pow( (Math.PI/L) ,2.0);
        for (int i = 0; i < doubleK.length; i++) {
            doubleK[i] = (i+1)*(i+1)*multiplier;
        }
        return new DiagonalMatrix(doubleK,false);
    }

    private RealMatrix createVDiagMatrix(RealMatrix D, RealMatrix base){
        RealMatrix temp = D.power(2).multiply(base);
                //TODO work on that code, it puts wrong energy values
//                .add(D.power(4).scalarMultiply(3))
//                .add(D.power(3).scalarMultiply(3));
        printMatrix(temp.getData(),"Baza funkcji");
        return temp;

    }


    //REFACTOR



    public void printMatrix(double[][] A, String name) {
        System.out.println(name);
        for (int i = 0; i < A.length; i++) {
            for (double[] doubles : A) {
                System.out.printf("%.2f   ", doubles[i]);
            }
            System.out.println();
        }
        System.out.println("---");
    }

    public RealMatrix getC() {
        return C;
    }

    public void setC(RealMatrix c) {
        C = c;
    }

    public RealMatrix getE() {
        return E;
    }

    public void setE(RealMatrix e) {
        E = e;
    }
}
