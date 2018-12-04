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
        try{
            boolean renting = true;
            while (renting = true) {
                String rentedBefore = w.getString("Have you rented with us before? (y/n) ");

                //the user has an account with Not Blockbuster
                if (rentedBefore.equals("y")) {

                    //Gives the User 3 tries to enter the correct password before kicking them back to the main menu
                    int tries = 3;
                    boolean correctPassword = false;
                    while (correctPassword == false) {
                        int userID = w.getInt("Enter Your UserID: ");
                        String pass = w.getString("Enter Your Password: ");

                        //checks the users input against the password in the table for that ID
                        PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT Password FROM passwords WHERE idcustomer = ?");
                        stat.setInt(1, userID);
                        ResultSet rs = stat.executeQuery();
                        String retrievedPassword = null;
                        while (rs.next()) {
                            retrievedPassword = rs.getString("Password");
                        }

                        //if they enter the correct password they get to rent a movie
                        if (pass.equals(retrievedPassword)) {
                            checkout(userID);
                            correctPassword = true;

                        } else if(!pass.equals(retrievedPassword)){
                            System.out.println("The password you have entered is incorrect");
                            tries--;
                            if(tries == 0){
                                correctPassword = true;
                            }
                        }
                    }

                    //if they mess up 3 times they have to go to change their password
                    if (tries == 0) {
                        String changePassOption = w.getString("You have ran out of tries. Would you like to change your password? (y/n) ");

                        if (changePassOption.equals("y")) {
                            i.createNewPassword();
                        }
                    }

                //The user does not have an account with us so they must create an account in order to check out
                } else if (rentedBefore.equals("n")) {

                    i.createCustomer();
                    int userID = w.getInt("Enter Your UserID? ");
                    checkout(userID);
                    renting = false;

                } else {

                    System.out.println("That was not an option. Try again");

                }
            }
        }catch(SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void checkout(int userID) throws Exception {

        //throws it to go check if they have a movie already rented
        r.returnMovieFromOtherClasses(userID);

        //if they have returned the movie they had rented then we can rent them a movie
        if (r.returnMovieFromOtherClasses(userID) == true){
            boolean renting = true;
            while (renting == true) {

                //get the id of the format and movie they want to rent
                int movieID = w.getInt("Great! Enter the id of movie would you like to rent: ");
                int formatID = w.getInt("Enter the id of the format you want to rent it in: ");

                //check if that movie in that format is in stock
                PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT stockdetails.idmovie, Title\n" +
                        "  FROM stockdetails JOIN movieforms \n" +
                        "    ON stockdetails.idmovie = movieforms.idmovie\n" +
                        " WHERE InStock > 0 AND idformat = ? AND idmovie = ?");
                stat.setInt(1, formatID);
                stat.setInt(1, movieID);
                ResultSet rs = stat.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                int count = 0;
                while (rs.next()) {
                    count++;
                }

                //if it is in stock then continue checkout
                if (count > 0) {
                    System.out.println("Your movie is in stock in that format!");

                    //update their customer table with the new rented updates to her idmovie and her idformat
                    PreparedStatement updateCustomersRental = c.getDBConnection().prepareStatement("BEGIN TRANSACTION UPDATE customer SET idmovie = ? and idformat = ? WHERE idcustomer = ?" +
                            "UPDATE movieforms SET InStock = InStock - 1 and CheckedOut = CheckedOut + 1 WHERE idmovie = ? and idformat = ? COMMIT or ROLLBACK");
                    updateCustomersRental.setInt(1, movieID);
                    updateCustomersRental.setInt(2, formatID);
                    updateCustomersRental.setInt(3, userID);
                    updateCustomersRental.setInt(3, movieID);
                    updateCustomersRental.setInt(3, formatID);

                    //Print out their order for them
                    System.out.println("Here is your order: ");
                    PreparedStatement checkout = c.getDBConnection().prepareStatement("SELECT customer.FirstName, customer.LastName, customer.Email, stockdetails.Title, format.FormatType" +
                            " FROM customer, stockdetails, formats " +
                            "JOIN stockdetails ON customer.idmovie = stockdetails.idmovie\n" +
                            "JOIN formats ON customer.idformat = formats.idformat");
                    rs = checkout.executeQuery();
                    rsmd = rs.getMetaData();
                    while (rs.next()) {
                        for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1) System.out.print("\t");
                            String columnValue = rs.getString(i);
                            System.out.print(columnValue);
                        }
                        System.out.println("");
                    }

                    System.out.println("Thank you for renting from us!");

                //The movie is not in stock
                } else {

                    System.out.println("I'm sorry the movie you wish to rent is not available in that format");
                    String tryAgain = ("Would you like to select a different film? (y/n) ");

                    boolean correct = false;
                    while(correct == false) {
                        if (tryAgain.equals('y')) {
                            renting = true;
                            correct = true;
                        } else if (tryAgain.equals('n')) {
                            correct = true;
                            renting = false;
                        } else {
                            tryAgain = w.getString("That was not an option select (y/n) ");
                        }
                    }
                }
            }

        } else {
            System.out.println("I'm sorry, but in order to rent a new movie you must return the one you currently have");
        }
    }
}
