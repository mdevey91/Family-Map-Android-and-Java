package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ServerModel.AuthToken;

/**
 * Created by devey on 2/18/17.
 */

public class AuthTokenDao
{
    Connection my_connection = null;
    public AuthTokenDao(Connection my_connection){

        this.my_connection = my_connection;
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists AuthTokenTable (auth_token string, user_name string, time timestamp);");
        } catch (SQLException e) {
            System.out.println();
            e.printStackTrace();
        }
    }
    /**
     * checks if the data we are looking for is in the table.
     * @return True if the row we are looking for is in the table
     */
    public AuthToken queryAuthTokenId(String selection)
    {
        AuthToken token = null;
        try {
            String new_selection = "'" + selection + "'";
            Statement statement = my_connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from AuthTokenTable where auth_token = " + new_selection);
            //Sqlite databases will give information as a ResultSet. In order to get the information you want you have to parse it like this
            while(rs.next())
            {
//                token = new AuthToken(rs.getString("user_name"), rs.getString("auth_token"), rs.getTimestamp("time"));
                token = new AuthToken(rs.getString("user_name"), rs.getString("auth_token"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return token;
    }
    public AuthToken queryUserName(String username)
    {
        AuthToken token = null;
        try {
            Statement statement = my_connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from AuthTokenTable where user_name = \"" + username + "\";");
            //Sqlite databases will give information as a ResultSet. In order to get the information you     want you have to parse it like this
            while(rs.next())
            {
                System.out.println("token found");
                token = new AuthToken(rs.getString("user_name"), rs.getString("auth_token"));
            }
        } catch (SQLException e) {
           token = null;
        }
        return token;
    }
    public boolean add(AuthToken my_token)
    {
        try {
            String sql = "insert into AuthTokenTable values ('" + my_token.getAuthToken() + "', '" + my_token.getUserName() + "', CURRENT_TIMESTAMP);";
            Statement statement = my_connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Removed an token from the table.
     * @param row
     * @return true if token was successfully removed. false if token was not sucessfully removed.
     */
    public boolean remove(String row, String column)
    {
        try {
            String sql = "delete from AuthTokenTable where \"" +  column + "\" = \"" + row + "\";";
            PreparedStatement prep = my_connection.prepareStatement(sql);
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
            statement.execute("drop table if exists AuthTokenTable;");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean createTable()
    {
        try {
            Statement statement = my_connection.createStatement();
            statement.execute("create table if not exists AuthTokenTable (auth_token string, user_name string, time timestamp);");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
