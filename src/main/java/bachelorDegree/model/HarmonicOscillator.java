package bachelorDegree.model;

public class HarmonicOscillator extends Oscillator {
    public HarmonicOscillator(int n, double k, double m, double L){
        this.setN(n);
        this.setK(k);
        this.setM(m);
        this.setL(L);
    }
    @Override
    public double getValueOfArgument(double argument) {
        return argument;
    }
}
