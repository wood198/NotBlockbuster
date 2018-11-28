import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerInfo {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void createCustomer() throws Exception {
        String first_name = w.getString("Please Enter Your First Name: ");
        String last_name = w.getString("Please Enter Your Last Name: ");
        String email_input = w.getString("Please Enter Your Email Address: ");
        String address_input = w.getString("Please Enter Your Address: ");
        int phone = w.getInt("Please Enter Your Phone Number: ");

        //TODO: Have to make it so that they can only enter 10 digits. (No spaces or dashes)

        try {
            //TODO figure out if thats how TXNs actually work
            PreparedStatement stat = c.getDBConnection().prepareStatement(" START TRANSACTION INSERT INTO customer(FirstName, LastName, Email, Address, Phone, idmovie, idformat)"
                    + " VALUES (?, ?, ?, ?, ?) COMMIT OR ROLLBACK");

            stat.setString(1, first_name);
            stat.setString(2, last_name);
            stat.setString(3, email_input);
            stat.setString(4, address_input);
            stat.setInt(5, phone);
            stat.setInt(6, 0);      //Hi Zach! I used 0 instead of Null since there are no 0s in our data
            stat.setInt(7, 0);      //we can use 0 to represent that they havent checked any movies out

            stat.execute();

            //newPassword();

            System.out.println("You now have an account with NotBlockbuster!");
            PreparedStatement ps = c.getDBConnection().prepareStatement("SELECT idcustomer FROM passwords WHERE Password = ?");
            //ps.setString(1, new_password);
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
                    String columnValue = rs.getString(i);
                    System.out.print("Your UserID is: " + columnValue);
                }
                System.out.println("");
            }
        }
        catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void password(String first, String last, String email, String address, int phone) throws Exception{
        String new_password = w.getString("Please Create A Password: ");
        PreparedStatement pass = c.getDBConnection().prepareStatement("SELECT idcustomer FROM customer WHERE FirstName = ? AND LastName = ? AND Email = ? AND Address = ? AND Phone = ?");
        pass.setString(1, first);
        pass.setString(2, last);
        pass.setString(3, email);
        pass.setString(4, address);
        pass.setInt(5, phone);
    }

    public void createNewPassword(int id) throws Exception{
        String checkEmail = w.getString("Type In Your Email For Verification: ");
        PreparedStatement pass = c.getDBConnection().prepareStatement("SELECT idcustomer FROM customer WHERE Email = ?");

    }
}
