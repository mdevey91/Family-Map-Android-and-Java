package SharedFiles;

/**
 * Used to hold data needed for the server proxy and the services
 */

public class PersonResult {
    private String firstname;
    private String lastname;
    private String gender;
    private String personID;
    private String descendant; //the descendant is the user
    private String father; //optional
    private String mother; //optional
    private String spouse; //optional
    private String message;

    public PersonResult(String personID, String descendant, String first_name, String last_name, String gender, String father, String mother, String spouse)
    {
        this.descendant = descendant;
        this.personID = personID;
        this.firstname = first_name;
        this.lastname = last_name;
        this.father = father;
        this.gender = gender;
        this.mother = mother;
        this.spouse = spouse;
        message = null;
    }

    public String getMessage() {
        return message;
    }

    public PersonResult(String message)
    {
        this.message = message;
    }

    public PersonResult(String descendant, String personID, String last_name, String first_name, String gender)
    {
        this.descendant = descendant;
        this.personID = personID;
        this.lastname = last_name;
        this.firstname = first_name;
        this.gender = gender;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFather() {
        return father;
    }

    public String getGender() {
        return gender;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }
    /**
     * Error is set to null in the constructor. If there is a problem with any of the fields the user inputted, error will be set to the appopiate error message. Later it will be checked to see if error is null or not.
     * @return String
     */
    public void print()
    {
        System.out.println("descendant = " + descendant);
        System.out.println("person id = " + personID);
        System.out.println("first_name = " + firstname);
        System.out.println("last_name = " + lastname);
        System.out.println("gender = " + gender);
        System.out.println("father = " + father);
        System.out.println("mother = " + mother);
        System.out.println("spouse = " + spouse);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
