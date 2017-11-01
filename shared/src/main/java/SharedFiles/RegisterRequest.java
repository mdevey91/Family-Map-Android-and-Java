package SharedFiles;

import java.util.UUID;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String gender;
    private String personID;
    public RegisterRequest(String user_name, String password, String email, String last_name, String first_name, String gender) {
        this.username = user_name;
        this.password = password;
        this.email = email;
        this.lastname = last_name;
        this.firstname = first_name;
        this.gender = gender;
        personID = UUID.randomUUID().toString();
    }

    public RegisterRequest(String username, String password, String email, String firstname, String lastname, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.personID = personID;
    }

    /**
     * Error is set to null in the constructor. If there is a problem with any of the fields the user inputted, error will be set to the appopiate error message. Later it will be checked to see if error is null or not.
     * @return String
     */
    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getPersonID() {
        return personID;
    }

    public String getUsername() {
        return username;
    }
}
