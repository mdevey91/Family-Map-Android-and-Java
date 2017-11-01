package DataAccess;

import java.sql.SQLException;

import ServerModel.Event;
import ServerModel.User;
import SharedFiles.EventResult;
import SharedFiles.PersonResult;

/**
 * Created by devey on 2/27/17.
 */

public class Test {
    public static void main(String[] args)
    {

        TransactionManager manager = new TransactionManager();
        manager.initialize();
        EventDao event_dao = new EventDao(manager.getConnection());
        PersonDao person_dao = new PersonDao(manager.getConnection());
        UserDao user_dao = new UserDao(manager.getConnection());



        EventResult event = new EventResult("Blaze Devey", "rgh40bj4", "53flj5l", "45.4.77", "66.66.66", "United States", "Houston", "Birth", "2018");
        EventResult event2 = new EventResult("levi Devey", "f4ndh4", "f43k67d", "77.33.53", "34.97.46", "United States", "Houston", "Birth", "1993");
        PersonResult person = new PersonResult("lj34lhs", "ljf3jl", "Matt", "Devey", "m", "Daniel", "Denise", "Abbie");
        User user = new User("mdevey91", "password1234", "mdevey91@gmail.com", "Matthew", "Devey", "m", "4lj25nlg");
        Event event3 = new Event("fljsdlf3", user, "fsd53g", "Death", "1991", "22.4.55.2", "44.5.322", "United States", "Dearborn");

        person_dao.dropTable();
        person_dao.createTable();
//        user_dao.dropTable();
//        user_dao.createTable();

//        FillRequest fr = new FillRequest(user.getUserName());
//        FillService fs = new FillService();

//        AuthTokenDao auth_token_dao = new AuthTokenDao((manager.getConnection()));
//        AuthToken auth_token = new AuthToken("mdevey3");

        user_dao.add(user);
//        fs.fill(fr);
//        event_dao.dropTable();
//        event_dao.createTable();
//        event_dao.add(event2);
//        person_dao.add(person);
//        auth_token_dao.add(auth_token);
//        if(event_dao.queryEventId("rgh40bj4") != null)
//        {
//            event_dao.queryEventId("rgh40bj4").print();
//        }
//        else
//        {
//            System.out.println("query not found!");
//        }
//        ArrayList<PersonResult> people = person_dao.getTable();
//        if(people != null)
//        {
//            for(int i = 0; i < people.size(); i++)
//            {
//                people.get(i).print();
//            }
//        }
//        event_dao.printTable();
        try {
            if(manager.getConnection() != null)
                manager.getConnection().close();
        } catch(SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
    }
}
