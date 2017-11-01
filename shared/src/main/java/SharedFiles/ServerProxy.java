package SharedFiles;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is the class that interacts with the server. It is on the client side.
 */

public class ServerProxy {
    private String server_host;
    private String port;
    private Gson gson = new Gson();

    public ServerProxy() {
    }

    public ServerProxy(String server_host, String port)
    {
        this.server_host = server_host;
        this.port = port;
    }
    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
     * @param r
     * @return RegisterResult
     */
    public RegisterResult register(RegisterRequest r)
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/user/register");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            String reqData = gson.toJson(r);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                RegisterResult data = gson.fromJson (respData, RegisterResult.class);
                System.out.println("line 61");
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Logs in the user and returns and auth token.
     * @param r
     * @return LoginResult
     */
    public LoginResult login(LoginRequest r)
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/user/login");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

//          This fills the request body
            String reqData = gson.toJson(r);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                LoginResult data = gson.fromJson(respData, LoginResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
     * @return ClearResult
     */
    public ClearResult clear()
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/clear");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

//            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                ClearResult data = gson.fromJson(respData, ClearResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Populates the server's database with generated data for the specified user name. The required "username" parameter must be a user already registered with the server. If there is
     * any data in the database already associated with the given user name, it is deleted. The optional “generations” parameter lets the caller specify the number of generations of ancestors
     * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
     * @param r
     * @return FillResult
     */
    public FillResult fill(FillRequest r)
    {
        URL url = null;
        try {
            if(r.getGenerations() == 4)
                url = new URL("http://" + server_host + ":" + port + "/fill/" + r.getUserName());
            else
                url = new URL("http://" + server_host + ":" + port + "/fill/" + r.getUserName() + "/" + r.getGenerations());

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                FillResult data = gson.fromJson(respData, FillResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
     * @param r
     * @return String
     */
    public LoadResult load(LoadRequest r)
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/load");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body

            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            String reqData = gson.toJson(r);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                LoadResult data = gson.fromJson(respData, LoadResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the single Person object with the specified ID.
     * @param ID
     * @return PersonResult
     */
    public PersonResult person(String ID, String authToken)
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/person/" + ID);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                PersonResult data = gson.fromJson(respData, PersonResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns ALL family members of the current user. The current user is determined from the provided auth token.
     * @return PersonResult[]
     */
    public AllPersonResult getAllPersons(String authToken)
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/person/");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                AllPersonResult data = gson.fromJson(respData, AllPersonResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ​Returns the single Event object with the specified ID.
     * @param ID
     * @return EventResult
     */
    public EventResult event(String ID, String authToken)
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/event/" + ID);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                EventResult data = gson.fromJson(respData, EventResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ​ Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
     * @return EventResult[]
     */
    public AllEventResult getAllEvents(String authToken)
    {
        try {

            URL url = new URL("http://" + server_host + ":" + port + "/event/");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);	// There is a request body

            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Content-Type", "application/json");

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                AllEventResult data = gson.fromJson(respData, AllEventResult.class);
                return data;
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    public void setServer_host(String server_host) {
        this.server_host = server_host;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
