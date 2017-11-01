package DataAccess;

import org.junit.Assert;

import ServerModel.AuthToken;

/**
 * Created by devey on 3/13/17.
 */
public class AuthTokenDaoTest {
    private TransactionManager manager = new TransactionManager();
    private AuthTokenDao auth_token_dao = new AuthTokenDao(manager.getConnection());
    @org.junit.Before
    public void setUp() throws Exception {
        auth_token_dao.dropTable();
        auth_token_dao.createTable();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        manager.closeConnection();
    }

    @org.junit.Test
    public void queryAuthTokenId() throws Exception {
        AuthToken authToken = new AuthToken("mdevey91", "lkj453hl3j");
        auth_token_dao.add(authToken);
        Assert.assertEquals(auth_token_dao.queryAuthTokenId("lkj453hl3j").getAuthToken(), "lkj453hl3j");

    }

    @org.junit.Test
    public void queryUserName() throws Exception {
        AuthToken authToken = new AuthToken("mdevey91", "lkj453hl3j");
        auth_token_dao.add(authToken);
        Assert.assertEquals(auth_token_dao.queryUserName("mdevey91").getAuthToken(), "lkj453hl3j");
    }

    @org.junit.Test
    public void add() throws Exception {
        AuthToken authToken = new AuthToken("mdevey91", "lkj453hl3j");
        Assert.assertTrue(auth_token_dao.add(authToken));
    }

    @org.junit.Test
    public void dropTable() throws Exception {
        Assert.assertTrue(auth_token_dao.dropTable());
    }

    @org.junit.Test
    public void createTable() throws Exception {
        auth_token_dao.dropTable();
        Assert.assertTrue(auth_token_dao.createTable());
    }

}