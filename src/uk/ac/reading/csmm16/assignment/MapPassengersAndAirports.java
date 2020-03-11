package uk.ac.reading.csmm16.assignment;


import java.util.LinkedList;
import java.util.Map;

/** 3-1- Get the number of flights for each airport from ALL the blocks using the its Code
 * {include a list of any airports not used}
 * Map(<String> airportCode, <LinkedList> numFlightsList) ---> Reduce(<String> airportCode, <Integer> numFlights)
 * */
public class MapPassengersAndAirports extends Mapper {

    public MapPassengersAndAirports(LinkedList input, Map output){
        this.block = input;
        this.mapperOutput = output;
    }

    public void run() {
        String [] splitedLine = null;
        //_unit = (LinkedList<String>) blocksList.get(j); // testing
        if (!block.isEmpty()) {
            //System.out.println("_unit is not empty | size =" + _unit.size()); // testing
            int unitSize = block.size();
            String s = "";
            if (splitedLine[0].matches("[\\w]{3,20}") && splitedLine[1].matches("[\\w]{3}") && splitedLine[2].matches("[\\d]{1,3}\\.[\\d]{3,13}") && splitedLine[3].matches("[\\d]{1,3}\\.[\\d]{3,13}")) {
                System.out.println(splitedLine[0]);//if it matches an airport entry, create new Airport obj using airport code as key
            }
            if (splitedLine[0].matches("[\\w]{3}[\\d]{4}[\\w]{2}[\\d]") && splitedLine[1].matches("[\\w]{3}[\\d]{4}[\\w]") && splitedLine[2].matches("[\\w]{3}") && splitedLine[3].matches("[\\w]{3}") && splitedLine[4].matches("[\\d]{10}") && splitedLine[5].matches("[\\d]{1,4}")) {
                System.out.println("Top airports match ---> " + splitedLine[0]);
            }
        }
    }
}
