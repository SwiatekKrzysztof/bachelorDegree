package bachelorDegree.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.linear.RealMatrix;


public enum FunctionBase {
    INSTANCE;

    RealMatrix C;
    RealMatrix E;
    public RealMatrix getBase(){
        return null;
    }
    private void createNewBase(int baseSize){

    }
    private void serializeBase(){

    }
    private void deserializeBase(){

    }


}
