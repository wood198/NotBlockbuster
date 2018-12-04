import java.sql.*;
import java.util.Scanner;

public class Returns {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();
    CustomerInfo i = new CustomerInfo();

    public boolean returnMovieFromCheckout(int userID) throws Exception {

        Scanner in = new Scanner(System.in);
        boolean returned = false;
        int tries = 3;
        while (tries > 0) {
            String pass = w.getString("Enter Your Password: ");

            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT Password FROM passwords WHERE idcustomer = ?");
            stat.setInt(1, userID);
            ResultSet rs = stat.executeQuery();
            String retrievedPassword = null;
            while (rs.next()) {
                retrievedPassword = rs.getString("Password");
            }

            if (pass.equals(retrievedPassword)) {

                //TODO: ENTER ALL THE CHECKING FOR CURRENTLY RENTED MOVIES SQL STUFF HERE
                //TODO: CHECK FOR MOVIE,
                //TODO: ASK IF THEY WANT TO RETURN (REMIND THEM THAT THEY HAVE TO IN ORDER TO GO THROUGH WITH THEIR NEW RENTAL),
                //TODO: IF YES, RETURN THE MOVIE (SETTING CUSTOMER BACK TO NULLS IN THE RIGHT PLACES AND RETURNING THE MOVIE TO THE CORRECT INSTOCK PLACES), return true bc the other method relies on it
                //TODO: IF NO, return false for the boolean returned
                break;

            } else {
                System.out.println("The password you have entered is incorrect");
                tries--;
            }
        }
        if (tries == 0) {
            String changePassOption = w.getString("You have ran out of tries. Would you like to change your password? (y/n) ");

            if (changePassOption.equals("y")) {
                i.createNewPassword();
            }
        }
        return returned;
    }

    public void returnMovie() throws Exception{

        Scanner in = new Scanner(System.in);
        int tries = 3;
        while (tries > 0) {
            int userID = w.getInt("Enter your UserID: ");
            String pass = w.getString("Enter Your Password: ");

            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT Password FROM passwords WHERE idcustomer = ?");
            stat.setInt(1, userID);
            ResultSet rs = stat.executeQuery();
            String retrievedPassword = null;
            while (rs.next()) {
                retrievedPassword = rs.getString("Password");
            }

            if (pass.equals(retrievedPassword)) {
                //TODO: tell the user what movie they currently have rented
                //TODO: ask if they wish to return it
                //TODO: if yes update movieforms table and customer table
                //TODO: if no "What the frick are you doing on this page then?"
                //TODO: Once things are sorted out: "Have a nice day!"
                break;
            } else {
                System.out.println("The password you have entered is incorrect");
                tries--;
            }
        }
        if (tries == 0) {
            String changePassOption = w.getString("You have ran out of tries. Would you like to change your password? (y/n) ");

            if (changePassOption.equals("y")) {
                i.createNewPassword();
            }
        }
    }
}
