package Services;

import DataAccess.AuthTokenDao;
import DataAccess.TransactionManager;
import DataAccess.UserDao;
import ServerModel.AuthToken;
import ServerModel.User;
import SharedFiles.LoginRequest;
import SharedFiles.LoginResult;

/**
 * Created by devey on 2/24/17.
 */

public class LoginService {
    private TransactionManager manager = new TransactionManager();
    private AuthTokenDao auto_token_dao = new AuthTokenDao(manager.getConnection());
    private UserDao user_dao = new UserDao(manager.getConnection());

    public LoginResult login(LoginRequest request)
    {
        User user = user_dao.queryUserName(request.getUserName());
        if(request.isInvalid())
            return new LoginResult("Not all field were entered.");
        else if(user == null)
            return new LoginResult("Invalid user name");
        else if(!user.getPassword().equals(request.getPassword()))
            return new LoginResult("Invalid password");

        AuthToken auth_token = new AuthToken(request.getUserName());
        auto_token_dao.add(auth_token);
        return new LoginResult(user.getPersonId(), auth_token.getAuthToken(), user.getUserName());

    }
}
