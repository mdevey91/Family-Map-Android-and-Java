package DataAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ServerModel.Event;
import ServerModel.User;
import SharedFiles.EventResult;

/**
 * Created by devey on 3/14/17.
 */
public class EventDaoTest {
    TransactionManager manager = new TransactionManager();
    EventDao event_dao = new EventDao(manager.getConnection());
    @Before
    public void setUp() throws Exception {
        event_dao.dropTable();
        event_dao.createTable();
    }

    @After
    public void tearDown() throws Exception {
        manager.closeConnection();
    }

    @org.junit.Test
    public void queryEventId() throws Exception {
        EventResult event = new EventResult("Blaze Devey", "rgh40bj4", "53flj5l", "45.4.77", "66.66.66", "United States", "Houston", "Birth", "2018");
        event_dao.addResult(event);
        Assert.assertNotNull(event_dao.queryEventId("rgh40bj4"));
    }

    @Test
    public void add() throws Exception {
        User user = new User("mdevey91", "password1234", "mdevey91@gmail.com", "Matthew", "Devey", "m", "4lj25nlg");
        Event event = new Event("fljsdlf3", user, "fsd53g", "Death", "1991", "22.4.55.2", "44.5.322", "United States", "Dearborn");
        Assert.assertTrue(event_dao.add(event));
    }

    @Test
    public void addResult() throws Exception {
        EventResult event = new EventResult("Blaze Devey", "rgh40bj4", "53flj5l", "45.4.77", "66.66.66", "United States", "Houston", "Birth", "2018");
        Assert.assertTrue(event_dao.addResult(event));
    }

    @Test
    public void removeByUser() throws Exception {
        EventResult event = new EventResult("Blaze Devey", "rgh40bj4", "53flj5l", "45.4.77", "66.66.66", "United States", "Houston", "Birth", "2018");
        EventResult event2 = new EventResult("Blaze Devey", "42kh52", "k25lj6", "45.4.44", "66.66.44", "United States", "Detroid", "Birth", "2018");
        event_dao.addResult(event);
        event_dao.addResult(event2);
        Assert.assertTrue(event_dao.removeByUser("Blaze Devey"));
//        Assert.assertFalse(event_dao.removeByUser("chicken"));
    }

    @Test
    public void dropTable() throws Exception {
        Assert.assertTrue(event_dao.dropTable());
    }

    @Test
    public void createTable() throws Exception {
        event_dao.dropTable();
        Assert.assertTrue(event_dao.createTable());
    }

    @Test
    public void getTable() throws Exception {
        EventResult event = new EventResult("Blaze Devey", "rgh40bj4", "53flj5l", "45.4.77", "66.66.66", "United States", "Houston", "Birth", "2018");
        EventResult event2 = new EventResult("Blaze Devey", "42kh52", "k25lj6", "45.4.44", "66.66.44", "United States", "Detroid", "Birth", "2018");
        event_dao.addResult(event);
        event_dao.addResult(event2);
        Assert.assertNotNull(event_dao.getTable("Blaze Devey"));
    }

}