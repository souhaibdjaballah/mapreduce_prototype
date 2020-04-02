package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.Combiner;
import uk.ac.reading.csmm16.assignment.core.KeyValueObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The Combiner is an optional class used to reduce the workload on the Reducers.
 * The Combiner receives a Mapper output as its input. The output of the combiner matches the input ot the
 * default Partitioner.
 *
 * This exmaple is identical to the ReducerObjective1 algorithm except the input and output data structure.
 */
public class CombineObjective1 extends Combiner {


    /**
     * The constructor receives an input list of type Map from the shuffle phase.
     *
     * @param mapperOutputList
     */
    public CombineObjective1(List mapperOutputList) {
        super(mapperOutputList);
    }

    /**
     * This is the custom code for handling data from the MapPassengersAndAirports class and
     * output the objective number 1.
     * @return void
     */
    public void combine(){
        Airport airport;
        Passenger passenger;
        Set<KeyValueObject> partitionSet;


        // a list of passengers with flight id as its key
        Map<String,Passenger> flightsList = new HashMap<>();
        // List of airports code
        List<String> airportCodeList =  new LinkedList();
        // Map lists for storing objectives key value pairs
        Map<String, Integer> flightsNumByAirportCode = new HashMap();



        for (Object object : mapperOutputList){
            KeyValueObject keyValueObj = (KeyValueObject) object;
            if (keyValueObj.getValue() instanceof Airport) {
                airport = (Airport) keyValueObj.getValue();
                airportCodeList.add(airport.getCode());
            }
            if (keyValueObj.getValue() instanceof Passenger) {
                passenger = (Passenger) keyValueObj.getValue();
                flightsList.put(passenger.getFlightID(), passenger);
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
        this.addCombinerOutputList(flightsNumByAirportCode);
    }
}
