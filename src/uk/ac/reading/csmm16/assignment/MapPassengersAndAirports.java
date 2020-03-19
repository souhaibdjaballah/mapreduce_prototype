package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.KeyValueObject;
import uk.ac.reading.csmm16.assignment.core.Mapper;
import java.util.LinkedList;

/**
 * This is the only custom Mapper class for filtering and sending the required data to
 * achieve the four objectives of the coursework.
 * This class extends the parent Mapper base class which has all the functionality
 * for the mapping phase.
 */
public class MapPassengersAndAirports extends Mapper<String, Airport> {

    /**
     * Constructor receives a block of data for each created mapper and
     * instantiate a new list of outputs for it.
     * @param input
     */
    public MapPassengersAndAirports(LinkedList input){
        this.block = input;
        this.mapperOutputList = new LinkedList<>();
    }

    /**
     * This is where the a thread look for code to run for each mapper instance.
     * @return void
     */
    @Override
    public void run() {
        map();
    }

    /**
     * This is the custom code for filtering and sending the needed data to the Reducers.
     * To achieve the 4 objectives this code is enough for each objective.
     * For each filtered line an object KeyValueObject containing a unique key in the first param of type
     * String and a value of type Object in the second param. This object is then added to the mapperOutputList list attribute.
     * @return void
     */
    private void map(){
        // a list of String to store the splitted values per line.
        String [] splitedLine;
        if (!block.isEmpty()) {
            int unitSize = block.size();
            Airport airport;
            Passenger passenger;
            for (int i = 0; i < unitSize; i++) {
                // Split the retrieved line by comma
                splitedLine = block.get(i).split(",");

                if (splitedLine[0].matches("[A-Z\\/ ]{3,20}")  // find any word of length 3 t0 20 containing all letters or "/" or " " characters
                        && splitedLine[1].matches("[A-Z]{3}")
                        && splitedLine[2].matches("[\\-]?[\\d]{1,3}\\.[\\d]{3,13}")
                        && splitedLine[3].matches("[\\-]?[\\d]{1,3}\\.[\\d]{0,13}")) { // Check if the line belongs to the airport data

                    airport = new Airport(splitedLine[0], splitedLine[1], Double.parseDouble(splitedLine[2]), Double.parseDouble(splitedLine[3]));
                    mapperOutputList.add(new KeyValueObject(airport.getCode(), airport)); // adding each airport object by airportCode value as Key

                    // Check if the line belongs to the airport data
                }else if (splitedLine[0].matches("[A-Z]{3}[\\d]{4}[A-Z]{2}[\\d]") && splitedLine[1].matches("[A-Z]{3}[\\d]{4}[A-Z]")
                        && splitedLine[2].matches("[A-Z]{3}") && splitedLine[3].matches("[A-Z]{3}")
                        && splitedLine[4].matches("[\\d]{10}") && splitedLine[5].matches("[\\d]{1,4}")) { // Check if the line belongs to the Passenger data

                    passenger = new Passenger(splitedLine[0], splitedLine[1], splitedLine[2], splitedLine[3], splitedLine[4], splitedLine[5]);
                    mapperOutputList.add(
                            // adding each passenger object by concatenating the pair of {passengerID-flightID} value as a unique String key
                            new KeyValueObject(passenger.getPassengerID()+ "-" + passenger.getFlightID(),
                                    passenger));
                }else {
                    System.err.println("Error in Line : " + block.get(i));
                }
            }
        }
    }

}