package uk.ac.reading.csmm16.assignment;

/**
 * Airport Class is for storing filtered data from the Top30_airports_LatLong.csv file
 * in a structured object to facilitate the calculation of the Objectives.
 * This is done in the Map phase and implemented in the MapPassengersAndAirport class.
 */
public class Airport {

    private String name;
    private String code;
    private double latitude;
    private double longitude;

    /**
     * @param name
     * @param code
     * @param latitude
     * @param longitude
     */
    Airport(String name, String code, double latitude, double longitude){
        this.name = name;
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Getters
     * @return String
     */

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    /**
     * Overriding the toString method to get the desired output format by only calling an instance of the class.
     * @return String
     */
    @Override
    public String toString() {
        return getName() + ", " + getCode() + ", " + getLatitude() + ", " + getLongitude();
    }
}
