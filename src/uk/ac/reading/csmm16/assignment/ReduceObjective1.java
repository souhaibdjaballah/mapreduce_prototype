package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.Reducer;

import java.util.*;

/**
 * Objectives:
 *  1 --> Get flights number by airport Code
 *  2 --> Get a list of flights based on the Flight id
 *  3 --> Get the number of passengers on each flight
 *  4 --> the line-of-sight (nautical) miles for each flight, the total travelled by each passenger
 *        and the passenger having earned the highest air miles.
 * */

public class ReduceObjective1 extends Reducer {

    /**
     * @param list
     */
    public ReduceObjective1(Map list) {
        this.inputList = list;
    }

    @Override
    public void run() {
        reduce();
    }

    void reduce(){
        Airport airport;
        Passenger passenger;


        // a list of passengers with flight id as its key
        Map<String,Passenger> flightsList = new HashMap<>();
        // List of airports code
        List<String> airportCodeList =  new LinkedList();
        // Map lists for storing objectives key value pairs
        Map<String, Integer> flightsNumByAirportCode = new HashMap();

        //Converting the Map object to a Set object so that we can traverse it
        Set set = inputList.entrySet();
        Iterator keyValue = set.iterator();

        while(keyValue.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) keyValue.next();
            if(entry.getValue() instanceof Airport) {
                airport = (Airport) entry.getValue();
                airportCodeList.add(airport.getCode());
            }
            if(entry.getValue() instanceof Passenger){
                passenger = (Passenger) entry.getValue();
                flightsList.put(passenger.getFlightID(),passenger);
            }
        }

        for(String airportCode : airportCodeList){
            int flightsCounter = 0;
            set = flightsList.entrySet();
            keyValue = set.iterator();

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

