package ServerModel;

/**
 * Created by devey on 2/24/17.
 */

public class Location {
    private String latitude;
    private String longitude;
    private String country;
    private String city;

    public Location(String latitude, String longitude, String country, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
