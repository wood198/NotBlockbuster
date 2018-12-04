import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class PrintRental {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();
    CustomerInfo i = new CustomerInfo();
    Returns r = new Returns();

    public void login() throws Exception {

        Scanner in = new Scanner(System.in);
        boolean renting = true;
        while (renting = true) {
            String rentedBefore = w.getString("Have you rented with us before? (y/n) ");
            if (rentedBefore.equals("y")) {

                //Gives the User 3 tries to enter the correct password before kicking them back to the main menu
                int tries = 3;
                while (tries > 0) {
                    int userID = w.getInt("Enter Your UserID: ");
                    String pass = w.getString("Enter Your Password: ");

                    PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT Password FROM passwords WHERE idcustomer = ?");
                    stat.setInt(1, userID);
                    ResultSet rs = stat.executeQuery();
                    String retrievedPassword = null;
                    while (rs.next()) {
                        retrievedPassword = rs.getString("Password");
                    }

                    if (pass.equals(retrievedPassword)) {
                        checkout(userID);
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

            } else if (rentedBefore.equals("n")) {

                i.createCustomer();
                int userID = w.getInt("Enter Your UserID? ");
                checkout(userID);
                renting = false;

            } else {

                System.out.println("That was not an option. Try again");

            }
        }
    }

    public void checkout(int userID) throws Exception {

        r.returnMovieFromCheckout(userID);


        //if no then continue with checkout
        //ask for the id of the movie and the form of the movie they want to check out
        //check if it is in stock
        //if it is then rent it to the customer telling them what the price is (update idmovie in customer table)
        //"Have a nice day!"
    }
}
