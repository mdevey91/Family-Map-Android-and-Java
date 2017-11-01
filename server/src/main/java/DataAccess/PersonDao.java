package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import SharedFiles.PersonResult;

/**
 * Interacts with the person table
 */

public class PersonDao {
    Connection my_connection = null;

    public PersonDao(Connection my_connection)
    {
        this.my_connection = my_connection;
//        As soon as I create the first instance of this dao I want the table to automatically be created. I don't want to have to call a function to do that.
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists PersonTable (person_id string, descendant string, first_name string, last_name string, gender string, father string, mother string, spouse string)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if the data we are looking for is in the table.
     * @return True if the row we are looking for is in the table
     */
    public PersonResult queryPersonId(String person_id)
    {
        PersonResult person = null;
        try {
            String new_selection = "'" + person_id + "';";
            Statement statement = my_connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from PersonTable where person_id = \"" + person_id + "\";");
            while(rs.next())
            {
                person = new PersonResult(rs.getString("person_id"), rs.getString("descendant"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"), rs.getString("father"), rs.getString("mother"), rs.getString("spouse"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    /**
     * adds an person to table
     * @param person
     * @return true if person was sucessfully added. false if person was not sucessfully added
     */
    public boolean add(PersonResult person)
    {
        try {
            PreparedStatement prep = my_connection.prepareStatement("insert into PersonTable values (?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, person.getPersonID());
            prep.setString(2, person.getDescendant());
            prep.setString(3, person.getFirstname());
            prep.setString(4, person.getLastname());
            prep.setString(5, person.getGender());
            prep.setString(6, person.getFather());
            prep.setString(7, person.getMother());
            prep.setString(8, person.getSpouse());
            prep.addBatch();
            my_connection.setAutoCommit(false);
            prep.executeBatch();
            my_connection.setAutoCommit(true);
            prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Removes a person from the table person table.
     * @param removal
     * @return true if person was sucessfully removed. false if person was not sucessfully removed.
     */
    public boolean remove(String removal, String column)
    {
        try {
            Statement statement = my_connection.createStatement();
            PreparedStatement prep = my_connection.prepareStatement("delete from PersonTable where ? = ?;");
            prep.setString(1, column);
            prep.setString(2, removal);
            prep.executeBatch();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean removeByUser(String user_id)
    {
        try {
            Statement statement = my_connection.createStatement();
            PreparedStatement prep = my_connection.prepareStatement("delete from PersonTable where descendant =?;");
            prep.setString(1, user_id);
            prep.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean dropTable()
    {
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("drop table if exists PersonTable;");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean createTable()
    {
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists PersonTable (person_id string, descendant string, first_name string, last_name string, gender string, father string, mother string, spouse string);");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public ArrayList<PersonResult> getTable(String username)
    {
        ArrayList<PersonResult> people = new ArrayList<>();
        try {
            Statement statement = my_connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from PersonTable where descendant = \"" + username + "\";");
            while(rs.next())
            {
                PersonResult person = new PersonResult(rs.getString("person_id"), rs.getString("descendant"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("gender"), rs.getString("father"), rs.getString("mother"), rs.getString("spouse"));
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;

    }

}

