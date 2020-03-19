package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.Reducer;

import java.util.*;

/**
 * Objectives:
 *  3 --> Get the number of passengers on each flight
 * */

public class ReduceObjective3 extends Reducer {

    /**
     * The constructor receives an input list of type Map from the shuffle and sort phase.
     *
     * @param list
     */
    public ReduceObjective3(Map list) {
        this.inputList = list;
    }


    /**
     * This is where the a thread look for code to run for each reducer instance.
     * @return void
     */
    @Override
    public void run() {
        reduce();
    }


    /**
     * This is the custom code for handling data from the MapPassengersAndAirports class and
     * output the objective number 3.
     * @return void
     */
    private void reduce(){
        Passenger passenger;

        // Map lists for storing objectives key value pairs
        Map<String, Integer> passengerFlightCountByFlightID = new HashMap();
        // Passenger list to facilitate the look-up between it the airport list to achieve the 4th objective
        List<Passenger> passengerList = new LinkedList();

        //Converting the Map object to a Set object so that we can traverse it
        Set set = inputList.entrySet();
        Iterator keyValue = set.iterator();

        while(keyValue.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) keyValue.next();
            if(entry.getValue() instanceof Passenger){
                passenger = (Passenger) entry.getValue();
                passengerList.add(passenger);

                // 3 --> Get the number of passengers on each flight
                if (!passengerFlightCountByFlightID.containsKey(passenger.getFlightID())) {
                    passengerFlightCountByFlightID.put(passenger.getFlightID(), 1);
                } else {
                    passengerFlightCountByFlightID.put(passenger.getFlightID(), passengerFlightCountByFlightID.get(passenger.getFlightID()) + 1);
                }
            }
        }


        // adding the final output to write in a file
        this.addToReducerOutputList(passengerFlightCountByFlightID);
    }
}

