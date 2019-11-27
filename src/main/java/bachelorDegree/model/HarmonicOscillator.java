package bachelorDegree.model;

public class HarmonicOscillator extends Oscillator {
    private Double factorialOfN;
    public HarmonicOscillator(String mString, String kString, String LString) {
        double m = Double.parseDouble(mString);
        double k = Double.parseDouble(kString);
        double L = Double.parseDouble(LString);
        this.setK(k);
        this.setM(m);
        this.setL(L);
    }

    @Override
    public double getValueOfArgument(double argument) {
        double w = Math.sqrt(getK()/getM());
        createFactorialOfN(getN());
        double denominator = Math.pow(Math.pow(2.0,getN())*factorialOfN,1.0/2.0 );
        //Equation is split for clarity

        double partOne = Math.pow((getM()*w)/(1.0*Math.PI),1.0/4.0);

        double partTwo = Math.pow( Math.E,(-1.0*(getM()*w*(Math.pow(argument,2.0)))/(2.0*1.0) ));

        double hermitian = hermitian(getN(),argument); //todo changed to hermit

        double partThree = Math.pow( ((getM()*w)/1.0),(1.0/2.0) );

        return (1/denominator)*partOne*partTwo*hermitian*partThree;
    }

    private void createFactorialOfN(int n) {
        if(null==factorialOfN){
            factorialOfN = factorial(n);
        }
    }

    private static double factorial(int n) {
        if(n == 0){
            return 1;
        }
        if (n <= 2) {
            return n;
        }
        return n * factorial(n - 1);
    }

    private static double hermitian(int n, double x) {
        if(n ==0) {
            return 1;
        }
        if(n == 1) {
            return 2 * x;
        }
        return 2*x*hermitian(n-1,x)-(2*(n-1)*hermitian(n-2,x));
    }
}
