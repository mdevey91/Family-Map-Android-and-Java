package SharedFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by devey on 4/14/17.
 */

public class Family {
    private static Family singleton;

    public static Family getInstance(){
        if(singleton == null)
        {
            singleton = new Family();
        }
        return singleton;
    }

    private RegisterRequest mRegisterRequest;
    private HashMap<String, PersonResult> mPersonMap = new HashMap<>();
    private HashMap<String, EventResult> mEventMap = new HashMap<>();
    private HashMap<String, ArrayList<EventResult>> mPersonToEvent = new HashMap<>();
    private HashMap<String, Float> mEventToColor = new HashMap<>();
    private ArrayList<PersonResult> mAllPersons;
    private ArrayList<EventResult> mAllEvents;
    private ArrayList<Pair> generations = new ArrayList<>();
    private HashMap<EventResult, EventResult> mSpuoseMap;

    private String server_port;
    private String server_host;

    private Family() {}

    public void Load(RegisterRequest registerRequest, ArrayList<PersonResult> allPersons, ArrayList<EventResult> allEvents, String server_port, String server_host){
        this.server_host = server_host;
        this.server_port = server_port;

        mRegisterRequest = registerRequest;
        mAllPersons = allPersons;
        mAllEvents = allEvents;
        for(PersonResult person : mAllPersons)
        {
            mPersonMap.put(person.getPersonID(), person);
        }
        for(EventResult event: mAllEvents)
        {
            mEventMap.put(event.getEventID(), event);
        }
        for(EventResult event: mAllEvents)
        {
            if(!mPersonToEvent.containsKey(event.getPersonID()))
            {
                mPersonToEvent.put(event.getPersonID(), new ArrayList<EventResult>());
            }
            mPersonToEvent.get(event.getPersonID()).add(event);
        }
        int i = 0;
        for(EventResult event: mAllEvents)
        {
            if(!mEventToColor.containsKey(event.getDescription())) //checks if the key is already in the map.
            {
//                Float rand_float = new Random().nextFloat(360) % 360;
                int rand_int = new Random().nextInt(360);
                mEventToColor.put(event.getDescription().toLowerCase(), (float)rand_int);  //If the key doesn't exist in the map this will insert the key into the map
            }
        }
    }

    public RegisterRequest getRegisterRequest() {
        return mRegisterRequest;
    }

    public HashMap<String, PersonResult> getPersonMap() {
        return mPersonMap;
    }

    public ArrayList<PersonResult> getAllPersons() {
        return mAllPersons;
    }

    public ArrayList<EventResult> getAllEvents() {
        return mAllEvents;
    }

    public PersonResult getPersonfromId(String person_id){
        return mPersonMap.get(person_id);
    }

    public HashMap<String, ArrayList<EventResult>> getPersonToEvent() {
        return mPersonToEvent;
    }

    public ArrayList<EventResult> getEventsFromPersonID(String person_id)
    {
        return mPersonToEvent.get(person_id);
    }

    private void createGenerations(String person_id, int generation)
    {
        PersonResult person = getPersonfromId(person_id);
        if(person.getMother() != null)
        {
            PersonResult mother = getPersonfromId(person.getMother());
            String relation;
            if(generation == 0)
                relation = "mother";
            else if(generation == 1)
                relation = "grandmother";
            else
            {
                String greats = null;
                for(int i = 0; i < generation; i++)
                {
                    greats += " great";
                }
                relation = greats + "grandmother";
            }
            generations.add(new Pair(mother, relation));
            createGenerations(mother.getPersonID(), generation + 1);
        }
        if(person.getFather() != null)
        {
            PersonResult father = getPersonfromId(person.getFather());
            String relation;
            if(generation == 0)
                relation = "father";
            else if(generation == 1)
                relation = "grandfather";
            else
            {
                String greats = null;
                for(int i = 0; i < generation - 1; i++)
                {
                    greats += " great";
                }
                relation = greats + "grandfather";
            }
            generations.add(new Pair(father, relation));
            createGenerations(father.getPersonID(), generation + 1);
        }

    }
    public ArrayList<Pair> getFamilyMembers(String person_id)
    {
        generations.clear();
        createGenerations(person_id, 0);
        return generations;
    }

    public EventResult getEventFromId(String event_id){
        return mEventMap.get(event_id);
    }
    public float getColorFromEvent(String event_type){
        return mEventToColor.get(event_type.toLowerCase());
    }
    public void clear(){
        singleton = new Family();
    }

    public EventResult getEarliestEvent(String person_id)
    {
        EventResult earliest_event = null;
        PersonResult person = getPersonfromId(person_id);
        for(EventResult event: mAllEvents)
        {
            if(event.getPersonID().equals(person_id))
            {
                if(earliest_event == null)
                    earliest_event = event;
                else if(Integer.parseInt(earliest_event.getYear()) > Integer.parseInt((event.getYear())))
                {
                    earliest_event = event;
                }
            }
        }
        return earliest_event;
    }

    public String getServerPort() {
        return server_port;
    }

    public String getServerHost() {
        return server_host;
    }

    public boolean isDescendant(String current_person_id, String descendant_id)
    {
        boolean mother_side = false;
        boolean father_side = false;
        if(current_person_id.equals(descendant_id))
            return true;
        PersonResult current_person  = getPersonfromId(current_person_id);
        PersonResult descendant = getPersonfromId(descendant_id);

        if(current_person.getFather() != null || current_person.getMother() != null)
        {
            father_side = isDescendant(current_person.getFather(), descendant_id);
            mother_side = isDescendant(current_person.getMother(), descendant_id);
        }
        if(mother_side || father_side)
        {
            return true;
        }
        else
            return false;
//        if(current_person.getMother() == null && current_person.getFather() == null) {
//            return false;
//        }
    }
}
