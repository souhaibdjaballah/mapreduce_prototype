package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.KeyValueObject;
import uk.ac.reading.csmm16.assignment.core.Reducer;

import java.util.*;

/**
 * Objectives:
 *  3 --> Get the number of passengers on each flight
 * */

public class ReduceObjective3 extends Reducer {

    /**
     * The constructor receives an input list of type Map from the shuffle phase.
     *
     * @param inputList
     */
    public ReduceObjective3(Iterator inputList) {
        super(inputList);
    }



    /**
     * This is the custom code for handling data from the MapPassengersAndAirports class and
     * output the objective number 3.
     * @return void
     */
    public void reduce(){
        Passenger passenger;
        Set<KeyValueObject> partitionSet;

        // Map lists for storing objectives key value pairs
        Map<String, Integer> passengerFlightCountByFlightID = new HashMap();
        // Passenger list to facilitate the look-up between it the airport list to achieve the 4th objective
        List<Passenger> passengerList = new LinkedList();

        while(inputList.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) inputList.next();
            partitionSet = (Set<KeyValueObject>) entry.getValue();
            for (KeyValueObject obj : partitionSet) {
                if (obj.getValue() instanceof Passenger) {
                    passenger = (Passenger) obj.getValue();
                    passengerList.add(passenger);

                    // 3 --> Get the number of passengers on each flight
                    if (!passengerFlightCountByFlightID.containsKey(passenger.getFlightID())) {
                        passengerFlightCountByFlightID.put(passenger.getFlightID(), 1);
                    } else {
                        passengerFlightCountByFlightID.put(passenger.getFlightID(), passengerFlightCountByFlightID.get(passenger.getFlightID()) + 1);
                    }
                }
            }
        }


        // adding the final output to write in a file
        this.addToReducerOutputList(passengerFlightCountByFlightID);
    }
}

