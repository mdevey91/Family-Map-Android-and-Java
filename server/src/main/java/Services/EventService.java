package Services;

import DataAccess.AuthTokenDao;
import DataAccess.EventDao;
import DataAccess.TransactionManager;
import ServerModel.AuthToken;
import SharedFiles.AllEventResult;
import SharedFiles.EventResult;

/**
 * Created by devey on 3/11/17.
 */

public class EventService {
    private TransactionManager manager = new TransactionManager();
    private EventDao event_dao = new EventDao(manager.getConnection());
    private AuthTokenDao auth_token_dao = new AuthTokenDao(manager.getConnection());
    public EventResult event(String event_id, String auth_token_string)
    {
        EventResult event = null;
        AuthToken auth_token = auth_token_dao.queryAuthTokenId(auth_token_string);
        event = event_dao.queryEventId(event_id);
        if(event == null)
            return new EventResult("That event does not exist");
        if(auth_token == null || !auth_token.getUserName().equals(event.getDescendant()))
            return new EventResult("You do not have access to that event");
        else
            return event;
    }
    public AllEventResult allEvents(String auth_token_string)
    {
        AuthToken auth_token = auth_token_dao.queryAuthTokenId(auth_token_string);
        if(auth_token == null)
            return new AllEventResult("Authorization token provided was invalid");
        return new AllEventResult(event_dao.getTable(auth_token.getUserName()));
    }
}
