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
        double denominator = Math.pow( (Math.pow(2.0,getN())*factorialOfN) , 1.0/2.0 );
        //Equation is split for clarity

        double partOne = Math.pow((getM()*w)/(1.0*Math.PI),1.0/4.0);

        double partTwo = Math.pow( Math.E,(-1.0*(getM()*w*(Math.pow(argument,2.0)))/(2.0*1.0) ));

        double hermitePolynomial = hermite(getN(),argument); //todo changed to hermit

        double partThree = Math.pow( ((getM()*w)/1.0),(1.0/2.0) );

        return (1/denominator)*partOne*partTwo*hermitePolynomial*partThree;
    }

    private void createFactorialOfN(int n) {
        if(null==factorialOfN){
            factorialOfN = factorial(n);
        }
    }

    private double factorial(int n) {
        if(n == 0){
            return 1;
        }
        if (n <= 2) {
            return n;
        }
        return n * factorial(n - 1);
    }

    private double hermitePolynomialRecursion(int n, double argument) {
        if(n ==0) {
            return 1;
        }
        if(n == 1) {
            return 2 * argument;
        }
        return 2*argument* hermitePolynomialRecursion(n-1,argument)-(2*(n-1)* hermitePolynomialRecursion(n-2,argument));
    }

    private static double hermite(int n, double x){
        double[] herm = new double[n+1];
        herm[0] = 1;
        if(n>=1) {
            herm[1] = 2 * x;
        }
        for(int i=2;i<=n;i++) {
            herm[i]=(2* x *herm[i-1])-(2*(i-1)*herm[i-2]);
        }
        return herm[herm.length-1];
    }
}
