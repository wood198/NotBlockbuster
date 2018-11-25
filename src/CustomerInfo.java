import java.sql.PreparedStatement;
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


        try {
            //TODO figure out if thats how TXNs actually work
            PreparedStatement stat = c.getDBConnection().prepareStatement(" START TRANSACTION INSERT INTO customer(FirstName, LastName, Email, Address, Phone, idmovie)"
                    + " VALUES (?, ?, ?, ?, ?) COMMIT OR ROLLBACK");

            stat.setString(1, first_name);
            stat.setString(2, last_name);
            stat.setString(3, email_input);
            stat.setString(4, address_input);
            stat.setInt(5, phone);
            ///TODO Zach look at this bc we have to set it to null and I dont have wifi on the plane to look it up:
            stat.setNull(6, Integer.parseInt(null));

            stat.execute();

            System.out.println("New customer added!");

        }
        catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
