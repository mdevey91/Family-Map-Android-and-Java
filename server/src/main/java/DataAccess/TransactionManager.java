package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by devey on 2/25/17.
 */

public class TransactionManager {
    Connection connection = null;
    public TransactionManager()
    {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:FamilyMap.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    I only need to initialize once.
    public void initialize()
    {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection()
    {
        return connection;
    }
    public void closeConnection()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
