import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class Delete{

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();
    Returns r = new Returns();

    public void deleteOrUpdate() throws Exception {

        Scanner in = new Scanner(System.in);
        try{

            //checks for a userID and Password
            boolean correct = true;
            while(correct == true){
                int userID = w.getInt("What is your UserID? ");
                String pass = w.getString("Enter Your Password: ");

                //checks the users input against the password in the table for that ID
                PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT Password FROM passwords WHERE idcustomer = ?");
                stat.setInt(1, userID);
                ResultSet rs = stat.executeQuery();
                String retrievedPassword = null;
                while (rs.next()) {
                    retrievedPassword = rs.getString("Password");
                }

                //if they enter a correct password for the entered ID they get to update the account or delete it
                if (pass.equals(retrievedPassword)) {
                    int choose = w.getInt("Select an Option: \n" +
                            "1. Delete Account \n" +
                            "2. Update Profile \n" +
                            "");

                    //check to see if they currently have a movie rented. If no, delete customer if yes return the movie then delete
                    if (choose == 1) {

                        if(r.returnMovieFromOtherClasses(userID)) {
                            PreparedStatement delC = null;

                            String deleteCus = "DELETE FROM customer WHERE idcustomer = ?";

                            try {

                                String deleteAccount = w.getString("Are you sure you want to delete your account? ");
                                if(deleteAccount.equals("y")) {
                                    delC = c.getDBConnection().prepareStatement(deleteCus);
                                    delC.setInt(1, userID);
                                    delC.executeUpdate();

                                    System.out.println("Your customer account has been deleted from our system");

                                    w.promptEnterKey();
                                } else if(deleteAccount.equals("n")){
                                    System.out.println("Okay!");
                                    w.promptEnterKey();
                                }

                            } catch (SQLException e) {

                                System.out.println(e.getMessage());
                            }

//                            PreparedStatement del = c.getDBConnection().prepareStatement("DELETE FROM customer, passwords WHERE userID = ?");
//                            del.setInt(1, userID);
//                            del.execute();
//                            System.out.println("Your customer account has been deleted from our system");

                        } else {
                            System.out.println("You must return your currently rented movie in order to delete your account");
                        }

                    }
                    else if (choose == 2) {

                        boolean answer = true;
                        while (answer = true) {
                            int update = w.getInt("Select something to update: \n" +
                                    "1. Email \n" +
                                    "2. Address \n" +
                                    "3. Phone");
                            if (update == 1) {

                                String newEmail = w.getString("Enter Your Updated Email: ");
                                PreparedStatement upEm = c.getDBConnection().prepareStatement("UPDATE customer SET Email = ? WHERE idcustomer = ?");
                                upEm.setString(1, newEmail);
                                upEm.setInt(2, userID);
                                System.out.println("Email has been updated");

                                String continueUpdates = w.getString("Would you like to update something else? (y/n) ");
                                if(continueUpdates.equals("y")){
                                    answer = true;
                                }else {
                                    answer = false;
                                }
                                w.promptEnterKey();
                            }
                            else if (update == 2) {

                                String newAddress = w.getString("Enter Your Updated Address: ");
                                PreparedStatement upAd = c.getDBConnection().prepareStatement("UPDATE customer SET Address = ? WHERE idcustomer = ?");
                                upAd.setString(1, newAddress);
                                upAd.setInt(2, userID);
                                System.out.println("Address has been updated");

                                String continueUpdates = w.getString("Would you like to update something else? (y/n) ");
                                if(continueUpdates.equals("y")){
                                    answer = true;
                                }else {
                                    answer = false;
                                }
                                w.promptEnterKey();
                            }
                            else if (update == 3) {

                                String newPhone = "";
                                boolean digits = false;
                                while(!digits) {
                                    newPhone = w.getString("Please Enter Your Updated Phone Number (Do not include dashes or parenthesis): ");
                                    digits = true;

                                    if (newPhone.length() < 10) {
                                        System.out.println("Phone Number Must Be 10 Digits Long");
                                    }
                                }

                                PreparedStatement upPh = c.getDBConnection().prepareStatement("UPDATE customer SET Phone = ? WHERE idcustomer = ?");
                                upPh.setString(1, newPhone);
                                upPh.setInt(2, userID);
                                System.out.println("Phone Number has been updated");

                                String continueUpdates = w.getString("Would you like to update something else? (y/n) ");
                                if(continueUpdates.equals("y")){
                                    answer = true;
                                }else {
                                    answer = false;
                                }
                                w.promptEnterKey();
                            }
                            else {
                                System.out.println("That was not an option.");
                            }
                        }
                    }
                    correct = false;
                }
                else{
                    System.out.println("The password you have entered is incorrect");
                }
            }
        }catch(SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
