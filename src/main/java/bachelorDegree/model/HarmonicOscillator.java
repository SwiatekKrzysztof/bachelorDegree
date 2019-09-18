package bachelorDegree.model;

public class HarmonicOscillator extends Oscillator {
    public HarmonicOscillator(int nx, double kx, double m, double L) {
        this.setNx(nx);
        this.setKx(kx);
        this.setM(m);
        this.setL(L);
    }

    public HarmonicOscillator(int nx, int ny, double kx, double ky, double m, double L){
        this.setNx(nx);
        this.setNy(ny);
        this.setKx(kx);
        this.setKy(ky);
        this.setM(m);
        this.setL(L);
    }

    @Override
    public double getValueOfArgument(double argument) {
        return argument;
    }
}
