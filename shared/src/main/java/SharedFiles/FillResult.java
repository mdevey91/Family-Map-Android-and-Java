package SharedFiles;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class FillResult {
    private String message;
    public FillResult(String new_result)
    {
        message = new_result;
    }
    public String getMessage() {
        return message;
    }
}
