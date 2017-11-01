package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ServerModel.User;
import SharedFiles.RegisterRequest;

/**
 * Interacts with the user table in the database.
 */

public class UserDao {
    Connection my_connection = null;

    public UserDao(Connection my_connection) {
        this.my_connection = my_connection;
//        As soon as I create the first instance of this dao I want the table to automatically be created. I don't want to have to call a function to do that.
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists UserTable (user_name string, password string, email string, first_name string, last_name string, gender string, person_id string)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if the data we are looking for is in the table.
     * @return True if the row we are looking for is in the table
     */
    public User queryUserName(String selection)
    {
        User user = null;
        try {
            String new_selection = "'" + selection + "';";
            Statement statement = my_connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from UserTable where user_name = " + new_selection );
            while(rs.next())
            {
                user = new User(rs.getString("user_name"), rs.getString("password"), rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"), rs.getString("person_id"));
            }
        } catch (SQLException e) {
            System.out.println("row was not in the table.");
        }
        return user;
    }

    /**
     * adds an user to table
     * @param user
     * @return true if user was sucessfully added. false if user was not sucessfully added
     */
    public boolean add(User user)
    {

        try {
            System.out.println("Called function add");
            PreparedStatement prep = my_connection.prepareStatement("insert into UserTable values (?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, user.getUserName());
            prep.setString(2, user.getPassword());
            prep.setString(3, user.getEmail());
            prep.setString(4, user.getFirstName());
            prep.setString(5, user.getLastName());
            prep.setString(6, user.getGender());
            prep.setString(7, user.getPersonId());
            prep.addBatch();
            my_connection.setAutoCommit(false);
            prep.executeBatch();
            my_connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean addRequest(RegisterRequest user)
    {

        try {
            System.out.println("Called function add");
            PreparedStatement prep = my_connection.prepareStatement("insert into UserTable values (?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, user.getUserName());
            prep.setString(2, user.getPassword());
            prep.setString(3, user.getEmail());
            prep.setString(4, user.getFirstname());
            prep.setString(5, user.getLastname());
            prep.setString(6, user.getGender());
            prep.setString(7, user.getPersonID());
            prep.addBatch();
            my_connection.setAutoCommit(false);
            prep.executeBatch();
            my_connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * Removed an user from the table.
     * @param removal
     * @return true if user was sucessfully removed. false if user was not sucessfully removed.
     */
    public boolean remove(String removal, String column)
    {
        try {
            Statement statement = my_connection.createStatement();
            PreparedStatement prep = my_connection.prepareStatement("delete from UserTable where ? = ?;");
            prep.setString(1, column);
            prep.setString(2, removal);
            prep.executeBatch();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean dropTable()
    {
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("drop table if exists UserTable;");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean createTable()
    {
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists UserTable (user_name string, password string, email string, first_name string, last_name string, gender string, person_id string)");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
