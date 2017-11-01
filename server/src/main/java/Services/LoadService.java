package Services;

import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.TransactionManager;
import DataAccess.UserDao;
import SharedFiles.LoadRequest;
import SharedFiles.LoadResult;

/**
 * Created by devey on 2/24/17.
 */

public class LoadService {
    private TransactionManager manager = new TransactionManager();
    private PersonDao person_dao = new PersonDao(manager.getConnection());
    private EventDao event_dao = new EventDao(manager.getConnection());
    private UserDao user_dao = new UserDao(manager.getConnection());
    private ClearService clear_service = new ClearService();
    public LoadResult load(LoadRequest request)
    {
        clear_service.clear(); //clears the database of all values;
        for(int i = 0; i < request.getEvents().length; i++)
        {
            request.getEvents()[i].setEventID();
            event_dao.addResult(request.getEvents()[i]);
        }
        for(int i = 0; i < request.getPersons().length; i++)
        {
            person_dao.add(request.getPersons()[i]);
        }
        for(int i = 0; i < request.getUsers().length; i++)
        {
            user_dao.addRequest(request.getUsers()[i]);
        }
        String result = "Successfully added " + request.getUsers().length + " users, " + request.getPersons().length + " persons, and " + request.getEvents().length + " events to the database.";
        return new LoadResult(result);
    }

}
