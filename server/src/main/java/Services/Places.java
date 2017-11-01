package Services;

import java.util.Random;

import ServerModel.Location;

/**
 * Created by devey on 3/9/17.
 */

public class Places {
    private Location[] data;
    public Places()
    {
        data = null;
    }
    public Location getRandomLocation()
    {
        Random rand = new Random();
        int n = rand.nextInt(data.length - 1);
        return data[n];
    }
}
