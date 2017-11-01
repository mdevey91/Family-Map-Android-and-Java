package Services;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Random;

import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.TransactionManager;
import DataAccess.UserDao;
import ServerModel.Event;
import ServerModel.Person;
import ServerModel.User;
import SharedFiles.FillRequest;
import SharedFiles.FillResult;
import SharedFiles.Names;
import SharedFiles.PersonResult;

/**
 * Created by devey on 2/24/17.
 */

public class FillService {
    private Gson gson = new Gson();
    private Names male_names;
    private Names female_names;
    private Names last_names;
    private Places locations;

    TransactionManager manager;
    PersonDao person_dao;
    UserDao user_dao;
    EventDao event_dao;

    User user;

    public static final String MALE = "m";
    public static final String FEMALE = "f";
    private int number_of_people_generated = 0;
    private int number_of_events_generated = 0;
    public FillService()
    {
        male_names = getNames("mnames.json");
        female_names = getNames("fnames.json");
        last_names = getNames("snames.json");
        locations = getLocations("locations.json");
        manager = new TransactionManager();
        person_dao = new PersonDao(manager.getConnection());
        user_dao = new UserDao(manager.getConnection());
        event_dao = new EventDao(manager.getConnection());
        System.out.println("called fill service");
    }
    public Names getNames(String extension)
    {
        Names names = null;
        File file = new File("json/" + extension);
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(file));
            names = gson.fromJson(isr, Names.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return names;
    }
    public Places getLocations(String extension)
    {
        Places places = null;
        File file = new File("json/" + extension);
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(file));
            places = gson.fromJson(isr, Places.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return places;
    }
    public FillResult fill(FillRequest request)
    {
        number_of_events_generated = 0;
        number_of_people_generated = 0;
        System.out.println("called fill");
        user = user_dao.queryUserName(request.getUserName());
//        Person person = new Person(user.getPersonID(), user, user.getFirstname(), user.getLastname(), user.getGender());
        if(user == null)
        {
            return new FillResult("User name is not in database");
        }

        //removes all the info from the database associated with
        person_dao.removeByUser(user.getUserName());
        event_dao.removeByUser(user.getUserName());

        //creates the person associated with the user
        Person person = new Person(user.getPersonId(), user, user.getFirstName(), user.getLastName(), user.getGender());
        number_of_people_generated++;

        Random rand = new Random();
        int user_birth = 2107 - (rand.nextInt(130) + 20);
        Event birth = new Event(user, user.getPersonId(), "Birth", locations.getRandomLocation(), Integer.toString(user_birth));
        person.setBirth(birth);

//        creates the mother and father for the user.
        person.setFather(CreateDescendants(new Person(), MALE, user_birth, request.getGenerations() - 1, person.getLastName()));
        person.setMother(CreateDescendants(new Person(), FEMALE, user_birth, request.getGenerations() - 1, person.getLastName()));
        person.getMother().setSpouse(person.getFather());
        person.getFather().setSpouse(person.getMother());
        populateTable(person);
        String success = "Successfully added " + number_of_people_generated + " persons and " + number_of_events_generated + " events to the database";
        return new FillResult(success);

    }
    private Person CreateDescendants(Person person, String gender, int child_birth_year, int generations_remaining, String last_name)
    {
        number_of_people_generated++;
        person = this.generatePerson(gender, child_birth_year, last_name);
        if(generations_remaining <= 0)
            return person;


        person.setFather(this.CreateDescendants(person.getFather(), MALE, Integer.parseInt(person.getBirth().getYear()), generations_remaining -1, person.getLastName()));
//        person.setLastName(person.getFather().getLastname());
        person.setMother(this.CreateDescendants(person.getMother(), FEMALE, Integer.parseInt(person.getBirth().getYear()), generations_remaining -1, person.getLastName()));
        //We are now setting the mother and father to be each other's spouses
        person.getFather().setSpouse(person.getMother());
        person.getMother().setSpouse(person.getFather());
//        if(person.getFather() != null)
//            person.getFather().setLastName(person.getLastname());

        return person;
    }
    private Person generatePerson(String gender, int child_birth_year, String last_name)
    {
        Person person = null;
        Random rand = new Random();
        int birth_year = child_birth_year - (rand.nextInt(10) + 20);
        int death_year = child_birth_year + (rand.nextInt(20) + 30);
        if(gender.equals(MALE))
        {
            person = new Person(user, male_names.getRandomName(), last_name, MALE);
        }
        else if(gender.equals(FEMALE))
        {
            person = new Person(user, female_names.getRandomName(), last_names.getRandomName(), FEMALE);
        }
        Event birth = new Event(user, person.getPersonId(), "Birth", locations.getRandomLocation(), Integer.toString(birth_year));
        Event death = new Event(user, person.getPersonId(), "Death", locations.getRandomLocation(), Integer.toString(death_year));
        person.setBirth(birth);
        person.setDeath(death);
        return person;
    }

    /**
     * Takes in a person and adds that person and all of his/her descendants to the personTable.
     * @param person
     */
    private void populateTable(Person person)
    {
        String father_id;
        String mother_id;
        String spouse_id;
        if(person.getFather() == null)
            father_id = null;
        else
            father_id = person.getFather().getPersonId();
        if(person.getMother() == null)
            mother_id = null;
        else
            mother_id = person.getMother().getPersonId();
        if(person.getSpouse() == null)
            spouse_id = null;
        else
            spouse_id = person.getSpouse().getPersonId();
        person_dao.add(new PersonResult(person.getPersonId(), user.getUserName(), person.getFirstName(), person.getLastName(), person.getGender(), father_id, mother_id, spouse_id));
        Event birth = person.getBirth();
        Event death = person.getDeath();
        if(birth != null) {
            event_dao.add(birth);
            number_of_events_generated++;
        }
        if(death != null) {
            event_dao.add(death);
            number_of_events_generated++;
        }
//        event_dao.add(birth.getEventID(), birth.getDescendant().getPersonID(), birth.getPersonID(), birth.getLocation().getLatitude(), birth.getLocation().getLongitude(), birth.getLocation().getCountry(), birth.getLocation().getCity(), birth.getDescription(), birth.getYear());
        if(person.getFather() != null)
        {
            populateTable(person.getFather());
        }
        if(person.getMother() != null)
        {
            populateTable(person.getMother());
        }
    }

}
