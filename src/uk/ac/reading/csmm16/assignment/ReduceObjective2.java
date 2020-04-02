package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.KeyValueObject;
import uk.ac.reading.csmm16.assignment.core.Reducer;

import java.util.*;

/**
 * Objectives:
 *  2 --> Get a list of flights based on the Flight id
 * */

public class ReduceObjective2 extends Reducer {

    /**
     * The constructor receives an input list of type Map from the shuffle phase.
     *
     * @param inputList
     */
    public ReduceObjective2(Iterator inputList) {
        super(inputList);
    }


    /**
     * This is the custom code for handling data from the MapPassengersAndAirports class and
     * output the objective number 2.
     * @return void
     */
    public void reduce(){
        Passenger passenger;
        Set<KeyValueObject> partitionSet;

        // Map lists for storing objectives key value pairs
        Map<String, String> listOfFlightsByTheirID = new HashMap();
        // Passenger list to facilitate the look-up between it the airport list to achieve the 4th objective
        List<Passenger> passengerList = new LinkedList();
        // Key-value list of each passenger and his/her total traveled air miles
//        Map<String, Double> passengerTotalTraveled = new HashMap<>();

        while(inputList.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) inputList.next();
            partitionSet = (Set<KeyValueObject>) entry.getValue();
            for (KeyValueObject obj : partitionSet) {
                if (obj.getValue() instanceof Passenger) {
                    passenger = (Passenger) obj.getValue();
                    passengerList.add(passenger);

                    // 2 --> Get a list of flights based on the Flight id
                    if (!listOfFlightsByTheirID.containsKey(passenger.getFlightID())) {
                        listOfFlightsByTheirID.put(passenger.getFlightID(), " <===== List of flight by its ID\n"
                                + passenger.toString());
                    } else {
                        listOfFlightsByTheirID.put(passenger.getFlightID(),
                                listOfFlightsByTheirID.get(passenger.getFlightID()) + "\n" + passenger.toString());
                    }
                }
            }
        }


        // adding the final output to write in a file
        this.addToReducerOutputList(listOfFlightsByTheirID);
    }
}

