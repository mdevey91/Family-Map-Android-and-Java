package SharedFiles;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isInvalid()
    {
        return username == null || password == null;
    }
}
