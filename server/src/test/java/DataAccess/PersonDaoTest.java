package DataAccess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SharedFiles.PersonResult;

/**
 * Created by devey on 3/14/17.
 */
public class PersonDaoTest {
    TransactionManager manager = new TransactionManager();
    PersonDao person_dao = new PersonDao(manager.getConnection());
    @Before
    public void setUp() throws Exception {
        person_dao.createTable();
    }

    @After
    public void tearDown() throws Exception {
        person_dao.dropTable();
    }

    @org.junit.Test
    public void queryPersonId() throws Exception {
        PersonResult person = new PersonResult("lj34lhs", "ljf3jl", "Matt", "Devey", "m", "Daniel", "Denise", "Abbie");
        person_dao.add(person);
        Assert.assertEquals(person_dao.queryPersonId("lj34lhs").getFirstname(), "Matt");
    }

    @Test
    public void add() throws Exception {
        PersonResult person = new PersonResult("lj34lhs", "ljf3jl", "Matt", "Devey", "m", "Daniel", "Denise", "Abbie");
        Assert.assertTrue(person_dao.add(person));
    }

    @Test
    public void removeByUser() throws Exception {
        PersonResult person = new PersonResult("12345", "mdevey91", "Matt", "Devey", "m", "Daniel", "Denise", "Abbie");
        PersonResult person2 = new PersonResult("lj34lhs", "mdevey3", "Matt", "Devey", "m", "Daniel", "Denise", "Abbie");
        person_dao.add(person);
        person_dao.add(person2);
        Assert.assertTrue(person_dao.removeByUser("mdevey91"));
        Assert.assertNull(person_dao.queryPersonId("12345")); //show if it was really deleted
        Assert.assertNotNull(person_dao.queryPersonId("lj34lhs")); //show that it didn't delete the whole database.
    }

    @Test
    public void dropTable() throws Exception {
        Assert.assertTrue(person_dao.dropTable());
    }

    @Test
    public void createTable() throws Exception {
        person_dao.dropTable();
        Assert.assertTrue(person_dao.createTable());
    }

    @Test
    public void getTable() throws Exception {
        PersonResult person = new PersonResult("lj34lhs", "ljf3jl", "Matt", "Devey", "m", "Daniel", "Denise", "Abbie");
        person_dao.add(person);
        Assert.assertNotNull(person_dao.getTable("mdevey91"));
    }

}