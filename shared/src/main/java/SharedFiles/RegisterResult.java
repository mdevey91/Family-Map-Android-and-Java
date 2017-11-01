package SharedFiles;

import java.util.UUID;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class RegisterResult {
    private String authToken;
    private String username;
    private String personID;
    private String message = null;

    public RegisterResult(RegisterRequest r) {
        authToken = UUID.randomUUID().toString();
        username = r.getUserName();
        personID = UUID.randomUUID().toString();
    }
    public RegisterResult(String authToken, String username, String personID)
    {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
    }
    public RegisterResult(String message)
    {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}
