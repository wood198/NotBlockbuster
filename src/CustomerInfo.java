/* Student Names: Ashley Wood, Zach Jagoda
 * Student IDs: 2271425, 2274813
 * Student Emails: wood198@mail.chapman.edu, jagod101@mail.chapman.edu
 * CPSC 408 - Database Management
 *
 * Final Project: Not Blockbuster
 * CustomerInfo.java
 */

import java.sql.*;
import java.util.Scanner;

public class CustomerInfo {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void createCustomer() throws Exception {

        System.out.println("--------------\n Create an Account\n--------------");

        //the user inputs all their info
        String first_name = w.getString("Please Enter Your First Name: ");
        String last_name = w.getString("Please Enter Your Last Name: ");
        String email_input = w.getString("Please Enter Your Email Address: ");
        String address_input = w.getString("Please Enter Your Address: ");
        String phone = "";
        boolean digits = false;
        while(!digits) {
            phone = w.getString("Please Enter Your Phone Number (Do not include dashes or parenthesis): ");
            if (phone.length() < 10) {
                System.out.println("Phone Number Must Be 10 Digits Long");
            }else{
                digits = true;
            }
        }

        try {
            //the users info is added to the customer table
            //there is a check constraint on phone to make sure it is exactly 10 digits
            PreparedStatement stat = c.getDBConnection().prepareStatement("INSERT INTO customer (FirstName, LastName, Email, Address, Phone)"
            + " VALUES (?, ?, ?, ?, ?)");
            stat.setString(1, first_name);
            stat.setString(2, last_name);
            stat.setString(3, email_input);
            stat.setString(4, address_input);
            stat.setString(5, phone);
            stat.execute();

            //get their id from the table to use later
            PreparedStatement ps = c.getDBConnection().prepareStatement("SELECT idcustomer FROM customer WHERE FirstName = ? and LastName = ? and Email = ? and Address = ? and Phone = ?");
            ps.setString(1, first_name);
            ps.setString(2, last_name);
            ps.setString(3, email_input);
            ps.setString(4, address_input);
            ps.setString(5, phone);
            ResultSet rs = ps.executeQuery();//
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
            w.promptEnterKey();

            //have the user create a password
            int userID = w.getInt("Enter Your User ID: ");
            String new_password = w.getString("Please Create A Password: ");
            ps = c.getDBConnection().prepareStatement("INSERT INTO passwords (idcustomer, Password) VALUES (?,?)");
            ps.setInt(1,userID);
            ps.setString(2,new_password);
            ps.execute();

            System.out.println("You now have an account with Not Blockbuster!");

        }
        catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void createNewPassword() throws Exception{

        //have the user verify their information in order to create a new password
        try{
            System.out.println("--------------\n Create New Password\n--------------");
            int id = w.getInt("Enter Your UserID: ");
            String checkEmail = w.getString("Enter Your Email For Verification: ");
            PreparedStatement pass = c.getDBConnection().prepareStatement("SELECT idcustomer FROM customer WHERE Email = ?");
            pass.setString(1, checkEmail);

            int count = 0;
            ResultSet rs = pass.executeQuery();
            while (rs.next()) {
                count++;
            }

            //have them create a new password if they are verified and update the password table
            if(count != 0){
                System.out.println("You have been verified as the owner of this account");
                String changePassword = w.getString("Type In Your New Password: ");

                PreparedStatement newPass = c.getDBConnection().prepareStatement("UPDATE passwords SET Password = ? WHERE idcustomer = ?");
                newPass.setString(1, changePassword);
                newPass.setInt(2, id);
                newPass.executeUpdate();


            }
            w.promptEnterKey();

        }catch(SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
