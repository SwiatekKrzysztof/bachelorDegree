package bachelorDegree.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Oscillator {
    private int n;
    private double m;
    private double k;
    private double h;
    private double L;

    abstract public double getValueOfArgument(double argument);

    public void setParameters(int n, double m, double k) {
        this.n = n;
        this.m = m;
        this.k = k;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getL() {
        return L;
    }

    public void setL(double l) {
        L = l;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}
