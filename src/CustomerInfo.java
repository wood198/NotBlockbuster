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

            PreparedStatement ps = c.getDBConnection().prepareStatement("SELECT idcustomer FROM customer WHERE FirstName = ?, LastName = ?, Email = ?, Address = ?, Phone = ?");
            ps.setString(1, first_name);
            ps.setString(2, last_name);
            ps.setString(3, email_input);
            ps.setString(4, address_input);
            ps.setInt(5, phone);


            //TODO: figure out how to grab the persons UserID from the above SQL statement to do the password
            String new_password = w.getString("Please Create A Password: ");
            ps = c.getDBConnection().prepareStatement("UPDATE passwords SET Password = ? WHERE idcustomer = ?");
            ps.setString(1,new_password);
            ps.setInt(2,userID);



            System.out.println("You now have an account with NotBlockbuster!");
            ps = c.getDBConnection().prepareStatement("SELECT idcustomer FROM passwords WHERE Password = ?");
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

    public void createNewPassword() throws Exception{

        int id = w.getInt("Enter Your UserID: ");
        String checkEmail = w.getString("Enter Your Email For Verification: ");
        String checkPhone = w.getString("Enter Your Phone Number For Verification: ");
        PreparedStatement pass = c.getDBConnection().prepareStatement("SELECT idcustomer FROM customer WHERE Email = ? AND Phone = ?");
        pass.setString(1, checkEmail);
        pass.setString(1, checkPhone);

        int count = 0;
        ResultSet rs = pass.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (rs.next()) {
            count++;
        }

        if(count > 0){
            System.out.println("You have been verified as the owner of this account");
            String changePassword = w.getString("Type In Your New Password: ");

            PreparedStatement newPass = c.getDBConnection().prepareStatement("UPDATE passwords SET Password = ? WHERE idcustomer = ?");
            newPass.setString(1, changePassword);
            newPass.setInt(2, id);

        }


    }
}
