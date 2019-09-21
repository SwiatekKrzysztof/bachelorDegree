package bachelorDegree.model;

public class HarmonicOscillator extends Oscillator {
    public HarmonicOscillator( String mString, String kString, String hString, String LString) {
//        int n = Integer.parseInt(nString);
        double m = Double.parseDouble(mString);
        double k = Double.parseDouble(kString);
        double h = Double.parseDouble(hString);
        double L = Double.parseDouble(LString);
//        this.setN(n);
        this.setK(k);
        this.setM(m);
        this.setL(L);
    }

    @Override
    public double getValueOfArgument(double argument) {
        return argument*argument;
    }
}
