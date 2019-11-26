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
}
