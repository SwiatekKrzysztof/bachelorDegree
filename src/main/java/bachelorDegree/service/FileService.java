package bachelorDegree.service;

import bachelorDegree.model.FunctionBasisSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;
import java.util.List;

public class FileService {
    public static void serializeBasisSet(RealMatrix basisSet, String basisSetSize, String basisSetSymbol) throws IOException {
        System.out.println("SERIALIZING "+basisSetSize+basisSetSymbol);
        FileOutputStream fos = new FileOutputStream("src/main/resources/basisSets/"+ basisSetSize + basisSetSymbol );
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        double[][] temp = basisSet.getData();
        oos.writeObject(temp);
        System.out.println("SERIALIZATION COMPLETE");
    }
    public static void deserializeBasisSet(FunctionBasisSet functionBasisSet, String basisSetSize, String basisSetSymbol)
            throws IOException, ClassNotFoundException {
        System.out.println("DESERIALIZING");
        FileInputStream fis = new FileInputStream("src/main/resources/basisSets/"+ basisSetSize + basisSetSymbol);
        ObjectInputStream ois = new ObjectInputStream(fis);
        if(basisSetSymbol.equals("C")) {
            double[][] tempArray = (double[][]) ois.readObject();
            functionBasisSet.setCMatrix(new BlockRealMatrix(tempArray));
        }
        if(basisSetSymbol.equals("E")){
            double[][] tempArray = (double[][]) ois.readObject();
            functionBasisSet.setEMatrix(new BlockRealMatrix(tempArray));
        }
        System.out.println("DESERIALIZING COMPLETE");
    }

    public static void createCSVFile(List<Double> list, String name) throws IOException {
        FileWriter out = new FileWriter(name +".csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)) {
            list.forEach(d -> {
                try {
                    printer.printRecord(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
