package SharedFiles;

/**
 * Created by devey on 4/15/17.
 */

public class Pair {

    private PersonResult person;
    private String relation;

    public Pair(PersonResult person, String relation) {
        this.person = person;
        this.relation = relation;
    }

    public PersonResult getPerson() {
        return person;
    }

    public String getRelation() {
        return relation;
    }
}
