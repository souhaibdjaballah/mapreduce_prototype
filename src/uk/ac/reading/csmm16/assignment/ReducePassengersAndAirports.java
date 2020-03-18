package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.KeyValueObject;
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

public class ReducePassengersAndAirports extends Reducer {

    /**
     * @param list
     */
    public ReducePassengersAndAirports(Map list) {
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

        // Map lists for storing objectives key value pairs
        Map<String, Integer> flightsNumByAirportCode = new HashMap();
        Map<String, String> listOfFlightsByTheirID = new HashMap();
        Map<String, Integer> passengerFlightCountByFlightID = new HashMap();
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

                // 1 --> Get flights number by airport Code
                if (!flightsNumByAirportCode.containsKey(passenger.getFromAirportCode())) {
                    flightsNumByAirportCode.put(passenger.getFromAirportCode(), 1);
                } else {
                    flightsNumByAirportCode.put(passenger.getFromAirportCode(),
                            flightsNumByAirportCode.get(passenger.getFromAirportCode()).intValue() + 1);
                }

                // 2 --> Get a list of flights based on the Flight id
                if (!listOfFlightsByTheirID.containsKey(passenger.getFlightID())) {
                    listOfFlightsByTheirID.put(passenger.getFlightID(), " <===== List of flight by its ID\n"
                            + passenger.toString());
                } else {
                    listOfFlightsByTheirID.put(passenger.getFlightID(),
                            listOfFlightsByTheirID.get(passenger.getFlightID()) +"\n" + passenger.toString());
                }
                // 3 --> Get the number of passengers on each flight
                if (!passengerFlightCountByFlightID.containsKey(passenger.getFlightID())) {
                    passengerFlightCountByFlightID.put(passenger.getFlightID(), 1);
                } else {
                    passengerFlightCountByFlightID.put(passenger.getFlightID(), passengerFlightCountByFlightID.get(passenger.getFlightID()).intValue() + 1);
                }
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

        // Objective 1 ------------------------------
        this.addToReducerOutputList(flightsNumByAirportCode);
        // Objective 2 ------------------------------
        this.addToReducerOutputList(listOfFlightsByTheirID);
        // Objective 3 ------------------------------
        this.addToReducerOutputList(passengerFlightCountByFlightID);
        // Objective 4 ------------------------------
        this.addToReducerOutputList(airportList);
        // Writing the passenger with the highest air miles - key value objects
        this.addToReducerOutputList(passengerHighestAirMilesObject);
    }
}
