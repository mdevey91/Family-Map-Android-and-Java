package ServerModel;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Used to access the auth token table
 */

public class AuthToken {
    private String auth_token;
    private String user_name;
    private Timestamp time = null;
//    String time =
    /**
     * Constructor creates random id
     */
    public AuthToken(String user_name)
    {
        this.user_name = user_name;
        auth_token = UUID.randomUUID().toString();
        time = new Timestamp(System.currentTimeMillis());
    }
    public AuthToken(String user_name, String auth_token, Timestamp time)
    {
        this.user_name = user_name;
        this.auth_token = auth_token;
        this.time = time;
    }

    public AuthToken(String user_name, String auth_token) {
        this.auth_token = auth_token;
        this.user_name = user_name;
        time = new Timestamp(System.currentTimeMillis());
    }

    public String getUserName(){
        return user_name;
    }
    public String getAuthToken(){
        return auth_token;
    }
    public Timestamp getTime()
    {
        return time;
    }
}
