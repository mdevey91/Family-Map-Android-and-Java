package Services;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.TransactionManager;
import DataAccess.UserDao;
import SharedFiles.ClearResult;

/**
 * Created by devey on 2/24/17.
 */

public class ClearService {
    AuthTokenDao auth_token_dao = null;
    EventDao event_dao = null;
    PersonDao person_dao = null;
    UserDao user_dao = null;
    TransactionManager my_manager = null;
    public ClearService()
    {
        my_manager = new TransactionManager();
        auth_token_dao = new AuthTokenDao(my_manager.getConnection());
        event_dao = new EventDao(my_manager.getConnection());
        user_dao = new UserDao(my_manager.getConnection());
        person_dao = new PersonDao(my_manager.getConnection());
    }
    public ClearResult clear()
    {

        auth_token_dao.dropTable();
        event_dao.dropTable();
        person_dao.dropTable();
        user_dao.dropTable();

        auth_token_dao.createTable();
        event_dao.createTable();
        person_dao.createTable();
        user_dao.createTable();
        return new ClearResult("Clear succeeded.");
    }
}
