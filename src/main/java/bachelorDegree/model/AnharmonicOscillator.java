package bachelorDegree.model;

import lombok.Data;

@Data
public class AnharmonicOscillator extends Oscillator {
    private double B;
    private double C;

    public AnharmonicOscillator(int n, double k, double m, double L,double B, double C) {
        this.B = B;
        this.C = C;
        this.setL(L);
        this.setN(n);
        this.setK(k);
        this.setM(m);
    }

    @Override
    public double getValueOfArgument(double argument) {
        return argument*argument;
    }
}
