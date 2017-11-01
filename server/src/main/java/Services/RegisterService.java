package Services;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.TransactionManager;
import DataAccess.UserDao;
import ServerModel.AuthToken;
import ServerModel.User;
import SharedFiles.FillRequest;
import SharedFiles.RegisterRequest;
import SharedFiles.RegisterResult;

/**
 * Created by devey on 2/24/17.
 */
//needs to create an instance of TransactionManager, establish a connection and initialize. The connection is then passed to the Dao's.
public class RegisterService {
    TransactionManager my_manager = null;
    EventDao event_dao = null;
    PersonDao person_dao = null;
    AuthTokenDao auth_token_dao = null;
    UserDao user_dao = null;
    FillService fs;
    public RegisterService()
    {
        my_manager = new TransactionManager();
        event_dao = new EventDao(my_manager.getConnection());
        person_dao = new PersonDao(my_manager.getConnection());
        auth_token_dao = new AuthTokenDao(my_manager.getConnection());
        user_dao = new UserDao(my_manager.getConnection());
    }
    public RegisterResult register(RegisterRequest r)
    {
        User user = user_dao.queryUserName(r.getUserName());
        if(user != null)
            return new RegisterResult("That username is already being used");
        user = new User(r.getUserName(), r.getPassword(), r.getEmail(), r.getFirstname(), r.getLastname(), r.getGender());
        user_dao.add(user);

        //have to use the fill service in order to populate the database.
        FillRequest fr = new FillRequest(user.getUserName());
        fs = new FillService();
        fs.fill(fr);

        //logs the user into the database
        AuthToken auto_token = new AuthToken(user.getUserName());
        auth_token_dao.add(auto_token);

        return new RegisterResult(auto_token.getAuthToken(), user.getUserName(), user.getPersonId());

    }
}
