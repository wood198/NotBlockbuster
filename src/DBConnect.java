import java.sql.*;

public class DBConnect {
    public static Connection getDBConnection() throws Exception {
        Connection conn = null;

        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception ex) {

            }

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/notblockbuster", "ashleynw04", "-------");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return conn;


    }

}
