package bachelorDegree.model;

import lombok.Data;
import org.apache.commons.math3.linear.RealMatrix;

@Data
public class AnharmonicOscillator extends Oscillator {
    private double B;
    private double C;

    public AnharmonicOscillator(int nx, double kx, double m, double L, double B, double C) {
        this.B = B;
        this.C = C;
        this.setNx(nx);
        this.setKx(kx);
        this.setM(m);
        this.setL(L);
    }
    public AnharmonicOscillator(int nx,int ny, double kx, double ky, double m, double L,double B, double C) {
        this.B = B;
        this.C = C;
        this.setNx(nx);
        this.setNy(ny);
        this.setKx(kx);
        this.setKy(ky);
        this.setM(m);
        this.setL(L);
    }

    @Override
    public double getValueOfArgument(double argument) {
//        double result = 0;
//        RealMatrix CMatrix = FunctionBasisSet.INSTANCE.getC();
//        for (int i = 0; i < CMatrix.getRowDimension(); i++) {
//            result = result + CMatrix.getColumn(vectorsMap.get(getn))[i]
//                    *Math.pow(2.0/L,1.0/2.0)*Math.sin((i+1.0)*Math.PI*(argument-getL()/2.0)/L); //parameter-L/2 instead parameter for middle part on 0
//        }
//        return result;
        return argument*argument;
    }


}
