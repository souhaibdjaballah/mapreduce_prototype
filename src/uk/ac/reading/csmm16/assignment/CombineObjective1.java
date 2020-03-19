package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.Combiner;

import java.util.*;

public class CombineObjective1 extends Combiner {


    public CombineObjective1(List mapperOutputList) {
        super(mapperOutputList);
    }

    @Override
    public void run() {
        combine();
    }

    void combine(){
        Airport airport;
        Passenger passenger;


        // a list of passengers with flight id as its key
        Map<String,Passenger> flightsList = new HashMap<>();
        // List of airports code
        List<String> airportCodeList =  new LinkedList();
        // Map lists for storing objectives key value pairs
        Map<String, Integer> flightsNumByAirportCode = new HashMap();

        //Converting the Map object to a Set object so that we can traverse it
//        Set set = inputList.entrySet();
//        Iterator keyValue = set.iterator();
//
//        while(keyValue.hasNext()) {
//            //Converting to Map.Entry so that we can get key and value separately
//            Map.Entry entry = (Map.Entry) keyValue.next();
//            if(entry.getValue() instanceof Airport) {
//                airport = (Airport) entry.getValue();
//                airportCodeList.add(airport.getCode());
//            }
//            if(entry.getValue() instanceof Passenger){
//                passenger = (Passenger) entry.getValue();
//                flightsList.put(passenger.getFlightID(),passenger);
//            }
//        }
//
//        for(String airportCode : airportCodeList){
//            int flightsCounter = 0;
//            set = flightsList.entrySet();
//            keyValue = set.iterator();
//
//            while(keyValue.hasNext()) {
//                //Converting to Map.Entry so that we can get key and value separately
//                Map.Entry entry = (Map.Entry) keyValue.next();
//                passenger = (Passenger) entry.getValue();
//
//                // 1 --> Get flights number by airport Code
//                if (airportCode.equals(passenger.getFromAirportCode())) {
//                    if (!flightsNumByAirportCode.containsKey(airportCode)){
//                        flightsNumByAirportCode.put(airportCode, 1);
//                    }else {
//                        flightsNumByAirportCode.put(airportCode,
//                                flightsNumByAirportCode.get(airportCode) + 1);
//                    }
//                } else {
//                    if (!flightsNumByAirportCode.containsKey(airportCode)){
//                        flightsNumByAirportCode.put(airportCode, 0);
//                    }else {
//                        flightsNumByAirportCode.put(airportCode,
//                                flightsNumByAirportCode.get(airportCode) + 0);
//                    }
//                }
//            }
//        }
//
//        // adding the final output to write in a file
//        this.addToReducerOutputList(flightsNumByAirportCode);
    }
}