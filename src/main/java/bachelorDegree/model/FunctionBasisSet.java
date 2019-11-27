package bachelorDegree.model;

import bachelorDegree.service.FileService;
import lombok.Data;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@Data
public class FunctionBasisSet {

    private RealMatrix CMatrix;
    private RealMatrix EMatrix;
    private ArrayList<Double> ESorted;
    private ArrayList<Integer> vectorsMap;

    public void createNewAdvancedBasicSet(String mString, String kString,
                                          String LString, String BString, String CString,
                                          String basisSetSizeString) {

        double m = Double.parseDouble(mString);
        double k = Double.parseDouble(kString);
        double L = Double.parseDouble(LString);
        double B = Double.parseDouble(BString);
        double C = Double.parseDouble(CString);
        int basisSetSize = Integer.parseInt(basisSetSizeString);

        createMatrices(basisSetSize,L,k,m,B,C);
    }

    private void createMatrices(int basisSetSize, double L, double k, double m,double B, double C){
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
        RealMatrix H = createHMatrix(A,k,basisSetSize,L,m,B,C);
        EigenDecomposition decomposition = new EigenDecomposition(H);
        this.CMatrix = decomposition.getV();
        
        RealMatrix EDiag = decomposition.getD();
        getMapOfDiagonalMatrixAndVector(EDiag);
    }
    private void getMapOfDiagonalMatrixAndVector(RealMatrix diagonal){
        vectorsMap = new ArrayList<>();
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
        try {
            FileService.createCSVFile(diagonalArray,String.valueOf(diagonalArray.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.ESorted = diagonalArray;
        }
    private RealMatrix createFunctionBase(int basisSetSize, double k){
        double[] doubleBase = new double[basisSetSize];
        //TODO CHANGED LINE< TESTING >
        //Arrays.fill(doubleBase, (k) /2.0);
        Arrays.fill(doubleBase, k);
        return new DiagonalMatrix(doubleBase,false);
    }

    private RealMatrix createHMatrix(RealMatrix A, double k, int basisSetSize, double L, double m, double B, double C){
        RealMatrix base = createFunctionBase(basisSetSize,k);

        EigenDecomposition decomposition = new EigenDecomposition(A);
        RealMatrix T = decomposition.getV();
        RealMatrix TTrans = decomposition.getVT();
        RealMatrix D = decomposition.getD();
        RealMatrix VDiag = createVDiagMatrix(D,base,B,C);

        // Potential Energy
        RealMatrix V = T.multiply(VDiag).multiply(TTrans).copy();

        //Kinetic Energy
        RealMatrix K = createKMatrix(basisSetSize,L,m);
        RealMatrix H = V.add(K);
        return H;
    }
    private  RealMatrix createKMatrix(int basisSetSize,double L, double m) { //Kinetic Energy
        double[] doubleK = new double[basisSetSize];
        double h = 1.0;
        double multiplier = ( (h*h)/(2.0*m) ) * Math.pow( (Math.PI/L) ,2.0);
        for (int i = 0; i < doubleK.length; i++) {
            doubleK[i] = (i+1)*(i+1)*multiplier;
        }
        return new DiagonalMatrix(doubleK,false);
    }

    private RealMatrix createVDiagMatrix(RealMatrix D, RealMatrix base, double B, double C){
        double[][] aDiag = new double[D.getColumn(0).length][D.getColumn(0).length];
        for (int i = 0; i < D.getColumn(0).length; i++) {
                aDiag[i][i] = (-0.17472109);
        }
        return D.power(2).multiply(base).scalarMultiply(0.5)
                .add(D.power(3).scalarMultiply(B))
                .add(D.power(4).scalarMultiply(C))
                .add(new BlockRealMatrix(aDiag));
    }

    private void printMatrix(double[][] A, String name) {
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
