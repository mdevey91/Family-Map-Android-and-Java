package SharedFiles;



/**
 * Used to hold data needed for the server proxy and the services
 */

public class LoadRequest {
    private RegisterRequest[] users;
    private PersonResult[] persons;
    private EventResult[] events;

    public LoadRequest(RegisterRequest[] users, PersonResult[] people, EventResult[] events) {
        this.users = users;
        this.persons = people;
        this.events = events;
    }
    public PersonResult[] getPersons() {
        return persons;
    }

    public RegisterRequest[] getUsers() {
        return users;
    }

    public EventResult[] getEvents() {
        return events;
    }
    /**
     * Error is set to null in the constructor. If there is a problem with any of the fields the user inputted, error will be set to the appopiate error message. Later it will be checked to see if error is null or not.
     * @return String
     */
}