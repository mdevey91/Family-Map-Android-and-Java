package ServerModel;

import java.awt.Event;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates a family tree for the user.
 */

public class FamilyHistoryInfo {
    private Set<Person> people = new HashSet<>();
    private Set<Event> events = new HashSet<>();
    private int generations;

    /**
     * FillService out a family tree for the user with as many generations as they enter in. These family members will have randomly generation details about their lives
     * @param user
     * @param generations
     */
    public FamilyHistoryInfo(Person user, int generations) {
        people.add(user);
        this.generations = generations;
    }

}
