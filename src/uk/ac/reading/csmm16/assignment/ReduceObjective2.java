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

public class ReduceObjective2 extends Reducer {

    /**
     * @param list
     */
    public ReduceObjective2(Map list) {
        this.inputList = list;
    }

    @Override
    public void run() {
        reduce();
    }

    void reduce(){
        Passenger passenger;

        // Map lists for storing objectives key value pairs
        Map<String, String> listOfFlightsByTheirID = new HashMap();
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
            if(entry.getValue() instanceof Passenger){
                passenger = (Passenger) entry.getValue();
                passengerList.add(passenger);

                // 2 --> Get a list of flights based on the Flight id
                if (!listOfFlightsByTheirID.containsKey(passenger.getFlightID())) {
                    listOfFlightsByTheirID.put(passenger.getFlightID(), " <===== List of flight by its ID\n"
                            + passenger.toString());
                } else {
                    listOfFlightsByTheirID.put(passenger.getFlightID(),
                            listOfFlightsByTheirID.get(passenger.getFlightID()) +"\n" + passenger.toString());
                }
            }
        }


        // adding the final output to write in a file
        this.addToReducerOutputList(listOfFlightsByTheirID);
    }
}

