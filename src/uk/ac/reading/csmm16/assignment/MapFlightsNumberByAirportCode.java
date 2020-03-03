package uk.ac.reading.csmm16.assignment;

import java.util.LinkedList;
import java.util.Map;

public class MapFlightsNumberByAirportCode extends Mapper<String, Integer> implements Runnable {


    public MapFlightsNumberByAirportCode(LinkedList input, Map output){
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
            for (int i = 0; i < unitSize; i++) {
                splitedLine = block.get(i).split(",");

                if (splitedLine[2].matches("\\w{3}")) {
                    if (!mapperOutput.containsKey(splitedLine[2])) {
                        mapperOutput.put(splitedLine[2], 1);
                    } else {
                        mapperOutput.put(splitedLine[2], (mapperOutput.get(splitedLine[2]).intValue() + 1));
                    }
                    System.out.println("---> Match: " + i + " " + splitedLine[2]);
                }else {
                    System.out.println("!--- Not matched: " + splitedLine[2]);
                }
            }
        }
    }

}
