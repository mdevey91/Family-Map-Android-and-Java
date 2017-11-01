package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ServerModel.Event;
import SharedFiles.EventResult;

/**
 * Used to interact witht the event table in the database
 */

public class EventDao {
    Connection my_connection = null;

    public EventDao(Connection my_connection) {
        this.my_connection = my_connection;
//        As soon as I create the first instance of this dao I want the table to automatically be created. I don't want to have to call a function to do that.
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists EventTable (event_id string, descendant string, person_id string, latitude string, longitude string, country string, city string, event_type string, year string)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks if the data we are looking for is in the table.
     * @return True if the row we are looking for is in the table
     */
    public EventResult queryEventId(String selection){
        EventResult event = null;
        try {
            String new_selection = "'" + selection + "'";
            Statement statement = my_connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from EventTable where event_id = " + new_selection);
            while(rs.next()) {
                event = new EventResult(rs.getString("descendant"), rs.getString("event_id"), rs.getString("person_id"), rs.getString("latitude"), rs.getString("longitude"), rs.getString("country"), rs.getString("city"), rs.getString("event_type"), rs.getString("year"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    /**
     * adds an event to table
     * @param event
     * @return true if event was sucessfully added. false if event was not sucessfully added
     */
    public boolean add(Event event)
    {
        try {
            System.out.println("Called function add");
            PreparedStatement prep = my_connection.prepareStatement("insert into EventTable values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, event.getEventId());
            prep.setString(2, event.getDescendant().getUserName());
            prep.setString(3, event.getPersonId());
            prep.setString(4, event.getLocation().getLatitude());
            prep.setString(5, event.getLocation().getLongitude());
            prep.setString(6, event.getLocation().getCountry());
            prep.setString(7, event.getLocation().getCity());
            prep.setString(8, event.getEventType());
            prep.setString(9, event.getYear());
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

    public boolean addResult(EventResult event)
    {
        try {
            System.out.println("Called function add");
            PreparedStatement prep = my_connection.prepareStatement("insert into EventTable values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, event.getEventID());
            prep.setString(2, event.getDescendant());
            prep.setString(3, event.getPersonID());
            prep.setString(4, event.getLatitude());
            prep.setString(5, event.getLongitude());
            prep.setString(6, event.getCountry());
            prep.setString(7, event.getCity());
            prep.setString(8, event.getDescription());
            prep.setString(9, event.getYear());
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
     * Removed an event from the table.
     * @param removal
     * @return true if event was sucessfully removed. false if event was not sucessfully removed.
     */
    public boolean remove(String column, String removal)
    {
        column = "'" + column + "'";
        try {
            PreparedStatement prep = my_connection.prepareStatement("delete from EventTable where ? = ?;");
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
            PreparedStatement prep = my_connection.prepareStatement("delete from EventTable where descendant =?;");
            prep.setString(1, user_id);
            prep.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean dropTable()
    {
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("drop table if exists EventTable;");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean createTable()
    {
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists EventTable (event_id string, descendant string, person_id string, latitude string, longitude string, country string, city string, event_type string, year string)");
            return true;
        } catch (SQLException e) {
            return false;
        }
     }
    public ArrayList<EventResult> getTable(String username)
    {
        ArrayList<EventResult> events = new ArrayList<>();
        try {
            Statement statement = my_connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from EventTable where descendant = \"" + username + "\";");
            while(rs.next())
            {
                EventResult event = new EventResult(rs.getString("descendant"), rs.getString("event_id"), rs.getString("person_id"), rs.getString("latitude"), rs.getString("longitude"), rs.getString("country"), rs.getString("city"), rs.getString("event_type"), rs.getString("year"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}


