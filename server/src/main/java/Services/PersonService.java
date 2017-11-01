package Services;

import DataAccess.AuthTokenDao;
import DataAccess.PersonDao;
import DataAccess.TransactionManager;
import ServerModel.AuthToken;
import SharedFiles.AllPersonResult;
import SharedFiles.PersonResult;

/**
 * Created by devey on 3/11/17.
 */

public class PersonService {
    TransactionManager manager = new TransactionManager();
    AuthTokenDao auth_token_dao = new AuthTokenDao(manager.getConnection());
    PersonDao person_dao = new PersonDao(manager.getConnection());

    public PersonService(){}
    public PersonResult person(String person_id, String auth_token_string)
    {
        PersonResult person = null;
        AuthToken auth_token = auth_token_dao.queryAuthTokenId(auth_token_string);
        person = person_dao.queryPersonId(person_id);
        if(person == null)
            return new PersonResult("That person does not exist");
        if(auth_token == null || !auth_token.getUserName().equals(person.getDescendant()))
            return new PersonResult("You do not have access to that person");
        else
            return person;
    }
    public AllPersonResult allPeople(String auth_token_string)
    {
        AuthToken auth_token = auth_token_dao.queryAuthTokenId(auth_token_string);
        if(auth_token == null)
            return new AllPersonResult("Authorization token provided was invalid");
        return new AllPersonResult(person_dao.getTable(auth_token.getUserName()));
    }
}
