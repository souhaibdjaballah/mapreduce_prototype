package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.KeyValueObject;
import uk.ac.reading.csmm16.assignment.core.Mapper;

import java.util.LinkedList;
import java.util.List;


public class MapPassengersAndAirports extends Mapper<String, Airport> {

    public MapPassengersAndAirports(LinkedList input){
        this.block = input;
        this.mapperOutputList = new LinkedList<>();
    }

    @Override
    public void run() {
        map();
    }

    void map(){
        String [] splitedLine;
        if (!block.isEmpty()) {
            int unitSize = block.size();
            String s = "";
            Airport airport;
            Passenger passenger;
            List airportsList = new LinkedList();
            for (int i = 0; i < unitSize; i++) {
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