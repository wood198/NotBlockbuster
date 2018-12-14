/* Student Names: Ashley Wood, Zach Jagoda
 * Student IDs: 2271425, 2274813
 * Student Emails: wood198@mail.chapman.edu, jagod101@mail.chapman.edu
 * CPSC 408 - Database Management
 *
 * Final Project: Not Blockbuster
 * DBConnect.java
 */
import java.sql.*;

public class DBConnect {
    public static Connection getDBConnection() throws Exception {
        Connection conn = null;

        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception ex) {

            }

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/notblockbuster?rewriteBatchedStatements=true&relaxAutoCommit=true", "ashleynw04", "--------");
            //conn.setAutoCommit(true);

        }
        catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }
}
