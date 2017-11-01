package DataAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ServerModel.User;

/**
 * Created by devey on 3/14/17.
 */
public class UserDaoTest {
    private TransactionManager manager = new TransactionManager();
    private UserDao user_dao = new UserDao(manager.getConnection());
    @Before
    public void setUp() throws Exception {
//        manager.initialize();
        user_dao.dropTable();
        user_dao.createTable();
    }

    @After
    public void tearDown() throws Exception {
        manager.closeConnection();
    }

    @org.junit.Test
    public void queryUserName() throws Exception {
        User user = new User("mdevey91", "password1234", "mdevey91@gmail.com", "Matthew", "Devey", "m", "4lj25nlg");
        user_dao.add(user);
        Assert.assertNotNull(user_dao.queryUserName("mdevey91"));
        Assert.assertNull(user_dao.queryUserName("fljslf"));
    }

    @Test
    public void add() throws Exception {
        User user = new User("mdevey3", "password", "mdevey3@gmail.com", "Matt", "D", "m", "8lk9hs");
        Assert.assertTrue(user_dao.add(user));
    }

    @Test
    public void dropTable() throws Exception {
        Assert.assertTrue(user_dao.dropTable());
//        Assert.assertFalse(user_dao.dropTable()); //tests dropping a table that doesn't exist
    }

    @Test
    public void createTable() throws Exception {
        user_dao.dropTable();
        Assert.assertTrue(user_dao.createTable());
    }

}