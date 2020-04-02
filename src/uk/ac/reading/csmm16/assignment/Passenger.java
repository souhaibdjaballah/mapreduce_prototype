package uk.ac.reading.csmm16.assignment;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Passenger Class is for storing filtered data from the "AComp_Passenger_data.csv" file
 * in a structured object to facilitate the calculation of the Objectives.
 * This is done in the Map phase and implemented in the MapPassengersAndAirports class.
 *
 * Attributes content:
 * passenger Id,
 * relevant IATA/FAA codes,
 * the departure time,
 * the arrival time (times to be converted to HH:MM:SS format), and
 * the flight times.
 * */
public class Passenger{

    // Using date formater to convert unix epoch time to HH:MM:SS format
    private SimpleDateFormat dateFormater = new SimpleDateFormat("HH:MM:SS");
    private String passengerID;
    private String flightID;
    private String fromAirportCode;
    private String destinationAirportCode;
    private String departureTime;
    private String totalFlightTime;
    private Double lineOfSight;

    /**
     * @param passengerID
     * @param flightID
     * @param fromAirportCode
     * @param destinationAirportCode
     * @param departureTime
     * @param totalFlightTime
     */
    public Passenger(String passengerID, String flightID, String fromAirportCode, String destinationAirportCode, String departureTime, String totalFlightTime) {
        this.passengerID = passengerID;
        this.flightID = flightID;
        this.fromAirportCode = fromAirportCode;
        this.destinationAirportCode = destinationAirportCode;
        this.departureTime = departureTime;
        this.totalFlightTime = totalFlightTime;
    }

    /**
     * Getters for all private attributes
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
     * Calculating the arrival time of each flight by converting 'departureTime' (unix time - seconds) and
     * totalFlightTime (minutes) to milliseconds and change their sum to HH:MM:SS format.
     * @return String
     */
    private String arrivalTime(){
        return dateFormater.format(new Date((Long.parseLong(departureTime) * 1000L) + (Long.parseLong(totalFlightTime) * 60000L)));
    }

    /**
     * Calculating the line-of-sight (nautical) miles for each flight
     * and thus output the passenger having earned the highest air miles.
     *
     * Calculate distance between two points in latitude and longitude.
     * This uses Haversine method as its base.
     * lat1, lon1 Start point lat2, lon2 End point
     *
     * @returns Distance in Miles
     */

    public double distance(double lat1, double lat2, double lon1, double lon2) {

        // Radius of the earth
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // convert to miles
        double distance = R * c * 1000 * 0.000621371192;

        distance = Math.pow(distance, 2);

        this.lineOfSight = Math.sqrt(distance);

        return this.lineOfSight;
    }

    /**
     * Overriding the toString method to get the desired output format by only calling an instance of the class.
     * @return String
     */
    @Override
    public String toString() {
        return  getPassengerID() + "," + getFlightID() + "," + getFromAirportCode()
                + "," + getDestinationAirportCode() + "," + getDepartureTime()
                + "," + arrivalTime() + "," + getTotalFlightTime();
    }


    /**
     * Overriding both the equals and hashcode methods to compare this class instances;
     * This is important to remove duplicate values.
     * @param obj
     * @return
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Passenger) {
            Passenger psg = (Passenger) obj;
            if (passengerID != null && !passengerID.equals(psg.getPassengerID()) && flightID != null && !flightID.equals(psg.getFlightID()))
                return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getPassengerID().hashCode() + getFlightID().hashCode();
    }
}
