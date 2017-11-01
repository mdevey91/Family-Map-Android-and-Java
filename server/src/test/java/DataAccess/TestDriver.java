package DataAccess;

/**
 * Created by devey on 3/14/17.
 */

public class TestDriver {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(
                "DataAccess.AuthTokenDaoTest",
                "DataAccess.EventDaoTest",
                "DataAccess.UserDaoTest",
                "DataAccess.PersonDaoTest",
                "SharedFiles.ServerProxyTest");
    }
}
