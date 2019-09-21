package bachelorDegree.model;

import lombok.Data;
import org.apache.commons.math3.linear.RealMatrix;

@Data
public class AnharmonicOscillator extends Oscillator {
    private double B;
    private double C;
    private RealMatrix CMatrix;
    //todo add FunctionBasisSet as field
    public AnharmonicOscillator(int n, double k, double m, double L,double B, double C) {
        this.B = B;
        this.C = C;
        this.setN(n);
        this.setK(k);
        this.setM(m);
        this.setL(L);
    }

    @Override
    public double getValueOfArgument(double argument) {
//        double result = 0;
//        //todo
//        RealMatrix CMatrix = FunctionBasisSet.INSTANCE.getC();
//        for (int i = 0; i < CMatrix.getRowDimension(); i++) {
//            result = result + CMatrix.getColumn(vectorsMap.get(getN()))[i]
//                    *Math.pow(2.0/L,1.0/2.0)*Math.sin((i+1.0)*Math.PI*(argument-getL()/2.0)/L); //parameter-L/2 instead parameter for middle part on 0
//        }
//        return result;
        return argument*argument;
    }


}
