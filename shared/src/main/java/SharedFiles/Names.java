package SharedFiles;

import java.util.Random;

/**
 * Created by devey on 3/4/17.
 */

public class Names {
    String[] data;
    public Names(){
        data = null;
    }
    public String[] getData()
    {
        return data;
    }
    public String getRandomName()
    {
        Random rand = new Random();
        int n = rand.nextInt(data.length - 1);
        return data[n];
    }
}
