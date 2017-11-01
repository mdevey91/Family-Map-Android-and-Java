package ServerModel;

import java.util.UUID;

/**
 * An object that represents a person.
 */

public class Person {
    private String person_id;
    private User descendant; //The user name
    private String first_name;
    private String last_name;
    private String gender;
    private Person mother;
    private Person father;
    private Person spouse;
    private Event birth;
    private Event death;

    /**
     * The constructor for creating a specific person
     * @param spouse
     * @param father
     * @param mother
     * @param gender
     * @param last_name
     * @param first_name
     * @param descendant
     * @param person_id
     */
//    public Person(Person spouse, Person father, Person mother, String gender, String last_name, String first_name, User descendant, String person_id)
//    {
//        this.spouse = spouse;
//        this.father = father;
//        this.mother = mother;
//        this.gender = gender;
//        this.last_name = last_name;
//        this.first_name = first_name;
//        this.descendant = descendant;
//        this.person_id = person_id;
//    }


    public Person(String person_id, User descendant, String first_name, String last_name, Person mother, String gender, Person father, Person spouse, Event birth, Event death) {
        this.person_id = person_id;
        this.descendant = descendant;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mother = mother;
        this.gender = gender;
        this.father = father;
        this.spouse = spouse;
        this.birth = birth;
        this.death = death;
    }

    public Person(User descendant, String first_name, String last_name, String gender) {
        person_id = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    public Person(String person_id, User descendant, String first_name, String last_name, String gender) {
        this.person_id = person_id;
        this.descendant = descendant;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
    }

    /**
     * creates a random person
     */
    public Person() {}
    public String getPersonId() {
        return person_id;
    }

    public User getDescendant() {
        return descendant;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public Person getMother() {
        return mother;
    }

    public Person getFather() {
        return father;
    }

    public Person getSpouse() {
        return spouse;
    }

    public Event getBirth() {
        return birth;
    }

    public Event getDeath() {
        return death;
    }

    public void setPersonId(String person_id) {
        this.person_id = person_id;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setBirth(Event birth) {
        this.birth = birth;
    }

    public void setDeath(Event death) {
        this.death = death;
    }
}
