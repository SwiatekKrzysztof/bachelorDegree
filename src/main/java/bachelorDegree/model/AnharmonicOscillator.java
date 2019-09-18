package bachelorDegree.model;

import lombok.Data;

@Data
public class AnharmonicOscillator extends Oscillator {
    private double B;
    private double C;

    public AnharmonicOscillator(int nx, double kx, double m, double L) {
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
        return argument*argument;
    }
}
