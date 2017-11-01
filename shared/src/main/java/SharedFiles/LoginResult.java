package SharedFiles;

import java.util.UUID;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class LoginResult {
    private String username;
    private String authToken;
    private String personID;
    private String message;

    /**
     * Creates a random authorization token and random person id
     * @param r
     */
    public LoginResult(LoginRequest r)
    {
        username = r.getUserName();
        authToken = UUID.randomUUID().toString();
        personID = UUID.randomUUID().toString();
    }
    public LoginResult(String message)
    {
        this.message = message;
    }

    public LoginResult(String personID, String authToken, String username)
    {
        this.personID = personID;
        this.authToken = authToken;
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPersonId() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}
