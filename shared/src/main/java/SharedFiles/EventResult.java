package SharedFiles;

import java.util.UUID;

/**
 * Created by devey on 2/18/17.
 */

public class EventResult {
    private String description;
    private String personID;
    private String city;
    private String country;
    private String latitude;
    private String longitude;
    private String year;
    private String descendant; //is the user's id
    private String eventID;
    private String message;

    public EventResult(String descendant, String event_id, String personID, String latitude, String longitude, String country, String city, String description, String year) {
        this.descendant = descendant;
        this.eventID = event_id;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.description = description;
        this.city = city;
        this.year = year;
    }

    public void setEventID() {
        eventID = UUID.randomUUID().toString();
    }

    public EventResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
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

    public String getDescription() {
        return description;
    }

    public String getYear() {
        return year;
    }
    /**
     * Error is set to null in the constructor. If there is a problem with any of the fields the user inputted, error will be set to the appopiate error message. Later it will be checked to see if error is null or not.
     * @return String
     */
    public void print()
    {
        System.out.println("descendant = " + descendant);
        System.out.println("event id = " + eventID);
        System.out.println("person id = " + personID);
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);
        System.out.println("country = " + country);
        System.out.println("city = " + city);
        System.out.println("event type = " + description);
        System.out.println("year = " + year);
    }
}
