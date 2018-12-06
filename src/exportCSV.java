import java.sql.*;
import java.util.*;

public class exportCSV {
    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void generateReport() throws Exception {
        try {
            //TODO I have written a testcase just for customers, would need to do for each table. Not sure of exact syntax but this is the right direction
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT * FROM customer INTO OUTFILE 'C:/Desktop/customer.csv'");
            stat.executeQuery();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
