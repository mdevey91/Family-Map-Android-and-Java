package SharedFiles;

/**
 * Created by devey on 2/18/17.
 */

public class FillRequest {
    private String username;
    private int generations;


    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    /**
     * default constructor if a number of generations has not been specified.
     * @param username
     */
    public FillRequest(String username) {
        this.username = username;
        generations = 4;
    }

    public int getGenerations() {
        return generations;
    }

    public String getUserName() {
        return username;
    }
}
