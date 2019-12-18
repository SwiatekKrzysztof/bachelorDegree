package bachelorDegree.service;

import bachelorDegree.model.FunctionBasisSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;

public class FileService {

    public static void createCSVFile(List<Double> list, String name) throws IOException {
        FileWriter out = new FileWriter(name +"_"+ System.currentTimeMillis() +".csv");
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
