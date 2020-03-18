package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.KeyValueObject;
import uk.ac.reading.csmm16.assignment.core.Reducer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Objectives:
 *  1 --> Get flights number by airport Code
 *  2 --> Get a list of flights based on the Flight id
 *  3 --> Get the number of passengers on each flight
 *  4 --> the line-of-sight (nautical) miles for each flight, the total travelled by each passenger
 *        and the passenger having earned the highest air miles.
 * */

public class ReduceObjective4 extends Reducer {

    /**
     * @param list
     */
    public ReduceObjective4(Map list) {
        this.inputList = list;
    }

    @Override
    public void run() {
        reduce();
    }

    void reduce(){
        Airport airport;
        Passenger passenger;
        double distance = 0;
//        KeyValueObject<String, Double> passengerHighestAirMilesObject = null;
        KeyValueObject<String, Double> passengerHighestAirMilesObject = new KeyValueObject<>();

        // Map list for storing objectives key value pairs
        // List of airports with a key of AirportCode
        Map<String, Airport> airportList =  new HashMap<>();
        // Passenger list to facilitate the look-up between it the airport list to achieve the 4th objective
        List<Passenger> passengerList = new LinkedList();
        // Key-value list of each passenger and his/her total traveled air miles
        Map<String, Double> passengerTotalTraveled = new HashMap<>();

        //Converting the Map object to a Set object so that we can traverse it
        Set set = inputList.entrySet();
        Iterator keyValue = set.iterator();

        while(keyValue.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) keyValue.next();
            if(entry.getValue() instanceof Airport) {
                airport = (Airport) entry.getValue();
                airportList.put(airport.getCode(), airport);
            }else if(entry.getValue() instanceof Passenger){
                passenger = (Passenger) entry.getValue();
                passengerList.add(passenger);
            }
        }

        // 4 --> the line-of-sight (nautical) miles for each flight,
        //   --> the total travelled by each passenger
        //   --> and the passenger having earned the highest air miles.

        for (Passenger p : passengerList){
            airport = airportList.get(p.getFromAirportCode());
            Airport airportDestination = airportList.get(p.getDestinationAirportCode());
            if (airport != null && airportDestination != null){
                distance = p.distance(airport.getLatitude(), airportDestination.getLatitude() ,airport.getLongitude(), airportDestination.getLongitude());
                if(!passengerTotalTraveled.containsKey(p.getPassengerID())){
                    passengerTotalTraveled.put(p.getPassengerID(), distance);
                }else {
                    passengerTotalTraveled.put(p.getPassengerID(), passengerTotalTraveled.get(p.getPassengerID()) + distance);
                }
            }
        }

        set = passengerTotalTraveled.entrySet();
        keyValue = set.iterator();
        distance = 0;

        while(keyValue.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) keyValue.next();
            double temp = (Double) entry.getValue();
            if(temp > distance){
                passengerHighestAirMilesObject.setKeyValue((String) entry.getKey(), temp);
            }
        }

        // adding the final output to write in a file
        this.addToReducerOutputList(airportList);

        // Writing the passenger with the highest air miles - key value objects
        this.addToReducerOutputList(passengerHighestAirMilesObject);
    }
}

