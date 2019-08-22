package bachelorDegree.model;

import lombok.Data;

@Data
abstract public class Oscillator {
    private int n;
    private double m;
    private double k;
    private int range;

    abstract public double getValueOfArgument(double argument);
    public void setParameters(int n,double m, double k){
        this.n = n;
        this.m = m;
        this.k = k;
    }
}
