package uk.ac.reading.csmm16.assignment;

import java.util.LinkedList;
import java.util.Map;

public class MapFlightsListByID extends Mapper<String, Integer> implements Runnable {


    public MapFlightsListByID(LinkedList input, Map output){
        this.block = input;
        this.mapperOutput = output;
    }

    @Override
    public void run() {
        String [] splitedLine = null;
        //_unit = (LinkedList<String>) blocksList.get(j); // testing
        if (!block.isEmpty()) {
            //System.out.println("_unit is not empty | size =" + _unit.size()); // testing
            int unitSize = block.size();
            String s = "";
            for (int i = 0; i < unitSize; i++) {
                splitedLine = block.get(i).split(",");

                if (splitedLine[1].matches("\\w{3}\\d{4}\\w{1}")) {
                    if (!mapperOutput.containsKey(splitedLine[1])) {
//                        s += splitedLine[0] + " " + splitedLine[3] + " | ";
//                        mapperOutput.put(splitedLine[1], s);
                        mapperOutput.put(splitedLine[1], 1);
                    } else {
                        mapperOutput.put(splitedLine[1], (mapperOutput.get(splitedLine[1]).intValue() + 1));
                    }
                    System.out.println("---> Match: " + i + " " + splitedLine[1]);
                }else {
                    System.out.println("!--- Not matched: " + splitedLine[1]);
                }
            }
        }
    }

}