package bachelorDegree.service;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MenuService {
    public static boolean basisSetExists(String basisSetSize){
        return Files.exists(Paths.get("src/main/resources/basisSets/" + basisSetSize +"C"));
    }
    public static List<String> chopList(List<String> list){
        for (int i = 0; i < list.size(); i++) {
            list.set(i,StringUtils.chop(list.get(i)));
        }
        return list;
    }
}
