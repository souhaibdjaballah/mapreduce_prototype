package uk.ac.reading.csmm16.assignment;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * passenger Id,
 * relevant IATA/FAA codes,
 * the departure time,
 * the arrival time (times to be converted to HH:MM:SS format), and
 * the flight times.
 * */
public class ACompPassenger {

    private SimpleDateFormat dateFormater = new SimpleDateFormat("HH:MM:SS");
    private String passengerID;
    private String flightID;
    private String fromAirportCode;
    private String destinationAirportCode;
    private String departureTime;
    private String totalFlightTime;

    public ACompPassenger(String passengerID, String flightID, String fromAirportCode, String destinationAirportCode, String departureTime, String totalFlightTime) {
        this.passengerID = passengerID;
        this.flightID = flightID;
        this.fromAirportCode = fromAirportCode;
        this.destinationAirportCode = destinationAirportCode;
        this.departureTime = departureTime;
        this.totalFlightTime = totalFlightTime;
    }

    /**
     * Getters all the private attributes
     * @return String
     */

    public String getPassengerID() {
        return passengerID;
    }

    public String getFlightID() {
        return flightID;
    }

    public String getFromAirportCode() {
        return fromAirportCode;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public String getDepartureTime() {
        return dateFormater.format(new Date(Long.parseLong(departureTime) * 1000L));
    }

    public String getTotalFlightTime() {
        return totalFlightTime;
    }

    /**
     * Converting times to HH:MM:SS format
     * @return String
     */
    private String arrivalTime(){
        return dateFormater.format(new Date((Long.parseLong(departureTime) * 1000L) + (Long.parseLong(totalFlightTime) * 60000L)));
    }

    /**
     *  Calculate
     * the line-of-sight (nautical) miles for each flight and
     * the total travelled by each passenger
     * and thus output the passenger having earned the highest air miles.
     * */



    /**
     * Overriding the toString method to get the desired output format
     * @return String
     */
    @Override
    public String toString() {
        return "\nPassenger id: " + getPassengerID() + "\nFlight id: " + getFlightID() + "\nFrom airport IATA/FAA code: " + getFromAirportCode()
                + "\nDestination airport IATA/FAA code: " + getDestinationAirportCode()
                + "\nDeparture time (GMT): " + getDepartureTime() + "\nArrival time: "
                + arrivalTime() + "\nTotal flight time (mins): " + getTotalFlightTime();
    }
}
