package bachelorDegree.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Oscillator {
    private int nx;
    private int ny;
    private double m;
    private double kx;
    private double ky;
    private double L;

    abstract public double getValueOfArgument(double argument);
    public void setParameters(int n,double m, double k){
        this.nx = n;
        this.m = m;
        this.kx = k;
    }

    public double getKy() {
        return ky;
    }

    public void setKy(double ky) {
        this.ky = ky;
    }

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getKx() {
        return kx;
    }

    public void setKx(double kx) {
        this.kx = kx;
    }

    public double getL() {
        return L;
    }

    public void setL(double l) {
        L = l;
    }
}
