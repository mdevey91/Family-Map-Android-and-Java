package ServerModel;

import java.util.UUID;

/**
 * An object that represents an user.
 */

public class User {
    private String user_name;
    private String password;
    private String email;
    private String first_name;
    private String last_name;
    private String gender;
    private String person_id;
    public User(String user_name, String password, String email, String first_name, String last_name, String gender, String person_id)
    {
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.person_id = person_id;
    }

    public User(String user_name, String password, String email, String first_name, String last_name, String gender) {
        this.user_name = user_name;
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        person_id = UUID.randomUUID().toString();
    }

    public String getUserName() {
        return user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public String getPersonId() {
        return person_id;
    }
}
