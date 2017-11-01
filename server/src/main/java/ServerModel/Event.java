package ServerModel;

import java.util.UUID;

/**
 * Created by devey on 2/17/17.
 */

//I should create a location class. Every event should have a location and a date.
public class Event {
    private String event_id;
    private User descendant;
    private String person_id;
    private String event_type;
    private String year;
    private Location location = null;

    /**
     * The constructor for creating a specific event.
     * @param event_id
     * @param descendant
     * @param person_id
     * @param event_type
     * @param year
     */
    public Event(String event_id, User descendant, String person_id, String event_type, String year, String latitude, String longitude, String country, String city)
    {
        location = new Location(latitude, longitude, country, city);
        this.event_id = event_id;
        this.descendant = descendant;
        this.person_id = person_id;
        this.event_type = event_type;
        this.year = year;
    }

    public Event(User descendant, String person_id, String event_type, Location location, String year) {
        event_id = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.person_id = person_id;
        this.event_type = event_type;
        this.location = location;
        this.year = year;
    }

    /**
     * creates a random event
     */

    public String getEventId() {
        return event_id;
    }

    public User getDescendant() {
        return descendant;
    }

    public String getPersonId() {
        return person_id;
    }

    public String getEventType() {
        return event_type;
    }

    public String getYear() {
        return year;
    }

    public Location getLocation()
    {
        return location;
    }

    public void print()
    {
        System.out.println("event id = " + event_id);
        System.out.println("descendant = " + descendant);
        System.out.println("person = " + person_id);
        System.out.println("event type = " + event_type);
        System.out.println("year = " + year);
        System.out.println("longtitude = " + location.getLongitude());
        System.out.println("latitude = " + location.getLatitude());
        System.out.println("country = " + location.getCountry());
        System.out.println("city = " + location.getCity());
    }
}
