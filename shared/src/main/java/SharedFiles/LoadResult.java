package SharedFiles;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class LoadResult {
    private String message;
    public LoadResult(String message)
    {
        this.message = message;
    }
    public String getMessage()
    {
        return message;
    }
}
