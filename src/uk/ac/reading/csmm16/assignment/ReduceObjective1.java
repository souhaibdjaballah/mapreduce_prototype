package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.KeyValueObject;
import uk.ac.reading.csmm16.assignment.core.Reducer;

import java.util.*;

/**
 * Objectives:
 *  1 --> Get flights number by airport Code
 *        including any airports not used.
 * */

public class ReduceObjective1 extends Reducer {


    /**
     * The constructor receives an input list of type Map from the shuffle phase.
     *
     * @param inputList
     */
    public ReduceObjective1(Iterator inputList) {
        super(inputList);
    }


    /**
     * This is the custom code for handling data from the MapPassengersAndAirports class and
     * output the objective number 1.
     * @return void
     */
    public void reduce(){
        Airport airport;
        Passenger passenger;
        Set<KeyValueObject> partitionSet;


        // a list of passengers with flight id as its key
        Map<String,Passenger> flightsList = new HashMap<>();
        // List of airports code
        List<String> airportCodeList =  new LinkedList();
        // Map lists for storing objectives key value pairs
        Map<String, Integer> flightsNumByAirportCode = new HashMap();



        while(inputList.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) inputList.next();
            partitionSet = (Set<KeyValueObject>) entry.getValue();
            for(KeyValueObject obj : partitionSet) {
                if (obj.getValue() instanceof Airport) {
                    airport = (Airport) obj.getValue();
                    airportCodeList.add(airport.getCode());
                }
                if (obj.getValue() instanceof Passenger) {
                    passenger = (Passenger) obj.getValue();
                    flightsList.put(passenger.getFlightID(), passenger);
                }
            }
        }

        for(String airportCode : airportCodeList){
            Set set = flightsList.entrySet();
            Iterator keyValue = set.iterator();

            while(keyValue.hasNext()) {
                //Converting to Map.Entry so that we can get key and value separately
                Map.Entry entry = (Map.Entry) keyValue.next();
                passenger = (Passenger) entry.getValue();

                // 1 --> Get flights number by airport Code
                if (airportCode.equals(passenger.getFromAirportCode())) {
                    if (!flightsNumByAirportCode.containsKey(airportCode)){
                        flightsNumByAirportCode.put(airportCode, 1);
                    }else {
                        flightsNumByAirportCode.put(airportCode,
                                flightsNumByAirportCode.get(airportCode) + 1);
                    }
                } else {
                    if (!flightsNumByAirportCode.containsKey(airportCode)){
                        flightsNumByAirportCode.put(airportCode, 0);
                    }else {
                        flightsNumByAirportCode.put(airportCode,
                                flightsNumByAirportCode.get(airportCode) + 0);
                    }
                }
            }
        }

        // adding the final output to write in a file
        this.addToReducerOutputList(flightsNumByAirportCode);
    }
}

