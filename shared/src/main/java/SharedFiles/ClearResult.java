package SharedFiles;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class ClearResult {
    private String message;

    public ClearResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
