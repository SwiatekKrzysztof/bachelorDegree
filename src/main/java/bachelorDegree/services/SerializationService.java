package bachelorDegree.services;

import bachelorDegree.model.FunctionBasisSet;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;

public class SerializationService {
    public static void serializeBasisSet(RealMatrix basisSet, String basisSetSize, String basisSetSymbol) throws IOException {
        System.out.println("SERIALIZING "+basisSetSize+basisSetSymbol);
        FileOutputStream fos = new FileOutputStream("src/main/resources/basisSets/"+ basisSetSize + basisSetSymbol );
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(basisSet.getData());
    }
    public static void deserializeBasisSet(FunctionBasisSet functionBasisSet, String basisSetSize, String basisSetSymbol) throws IOException, ClassNotFoundException {
        System.out.println("DESERIALIZING");
        FileInputStream fis = new FileInputStream("src/main/resources/basisSets/"+ basisSetSize + basisSetSymbol);
        ObjectInputStream ois = new ObjectInputStream(fis);
        if(basisSetSymbol.equals("C")) {
            functionBasisSet.setC(new BlockRealMatrix((double[][]) ois.readObject()));
        }
        if(basisSetSymbol.equals("E")){
            functionBasisSet.setE(new BlockRealMatrix((double[][]) ois.readObject()));
        }
    }
}
