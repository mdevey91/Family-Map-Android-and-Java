package SharedFiles;

import java.util.ArrayList;

/**
 * Created by devey on 3/11/17.
 */

public class AllPersonResult {
    private ArrayList<PersonResult> data = new ArrayList<>();
    private String message;
    public AllPersonResult(ArrayList<PersonResult> data)
    {
        this.data = data;
    }

    public AllPersonResult() {}

    public AllPersonResult(String message)
    {
        this.message = message;
    }

    public ArrayList<PersonResult> getData() {
        return data;
    }
    public void addPerson(PersonResult person)
    {
        data.add(person);
    }

}
