package bachelorDegree.model;

import lombok.Data;
import org.apache.commons.math3.linear.RealMatrix;

@Data
public class AnharmonicOscillator extends Oscillator {
    private RealMatrix CMatrix;
    private FunctionBasisSet basisSet;

    public AnharmonicOscillator(FunctionBasisSet basisSet, String LString) {
        this.basisSet = basisSet;
        double L = Double.parseDouble(LString);
        this.setL(L);
    }

    @Override
    public double getValueOfArgument(double argument) {
        double result = 0;
        RealMatrix C = basisSet.getCMatrix();
        for (int i = 0; i < C.getRowDimension(); i++) {
            result = result + C.getColumn(basisSet.getVectorsMap().get(getN()))[i]
                    * Math.pow(2.0 / getL(), 1.0 / 2.0) * Math.sin((i + 1.0)
                    * Math.PI * (argument - getL() / 2.0) / getL());
            //parameter-L/2 instead parameter for middle part on 0
        }
        return result;
    }
}
