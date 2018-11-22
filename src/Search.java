import java.sql.*;
import java.util.*;

public class Search {
    public void printInStock() throws Exception {

    }

    public void printCheckedOut() throws Exception {

    }

    public void printAll() throws Exception {

        DBConnect c = new DBConnect();

        try{

            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT * FROM stockdetails");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

        } catch(SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void printByGenre() throws Exception {

    }

    public void searchMovie() throws Exception {

    }
}
