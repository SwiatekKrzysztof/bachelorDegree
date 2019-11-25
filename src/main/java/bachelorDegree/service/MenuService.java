package bachelorDegree.service;

import bachelorDegree.controller.MenuControllerOld;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MenuService {
    public static boolean checkIfHarmonic(){
        return MenuControllerOld.oscillatorType.equals("Harmonic");
    }

    public static boolean checkIf1D(){
        return MenuControllerOld.dimensions.equals("1D");
    }
    public static boolean basisSetExists(String basisSetSize){
        return Files.exists(Paths.get("src/main/resources/basisSets/" + basisSetSize +"C"));
    }
    public static List<String> chopList(List<String> list){
        for (int i = 0; i < list.size(); i++) {
            list.set(i,StringUtils.chop(list.get(i)));
        }
//        list = list.stream().filter(s -> s.length() != 0)
//                .map(s->s.substring(0,s.length()-3))
//                .collect(Collectors.toList());
        return list;
    }
}
