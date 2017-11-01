package SharedFiles;

import java.util.ArrayList;

/**
 * Created by devey on 3/11/17.
 */

public class AllEventResult {
    private ArrayList<EventResult> data = new ArrayList<>();
    private String message;

    public AllEventResult(ArrayList<EventResult> data) {
        this.data = data;
    }

    public AllEventResult(String message) {
        this.message = message;
    }

    public ArrayList<EventResult> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
