package uk.ac.reading.csmm16.assignment;

public class Top30Airport {

    private String name;
    private String code;
    private String latitude;
    private String longitude;

    Top30Airport(String name, String code, String latitude, String longitude){
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

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }


    @Override
    public String toString() {
        return "\nAirport name: " + getName() + "\nAirport IATA/FAA code: " + getCode() + "\nLatitude: " + getLatitude() + "\nLongitude: " + getLongitude() + "\n";
    }
}
