/* Student Names: Ashley Wood, Zach Jagoda
 * Student IDs: 2271425, 2274813
 * Student Emails: wood198@mail.chapman.edu, jagod101@mail.chapman.edu
 * CPSC 408 - Database Management
 *
 * Final Project: Not Blockbuster
 * PrintRental.java
 */

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
            while (renting) {
                String rentedBefore = w.getString("Have you rented with us before? (y/n) ");

                //the user has an account with Not Blockbuster
                if (rentedBefore.equals("y")) {

                    //Gives the User 3 tries to enter the correct password before kicking them back to the main menu
                    int tries = 3;
                    boolean correctPassword = true;
                    while (correctPassword) {
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
                            break;

                        } else if(!pass.equals(retrievedPassword)){
                            System.out.println("The password you have entered is incorrect");
                            tries--;
                            if(tries == 0){
                                correctPassword = false;
                            }
                        }
                    }

                    //if they mess up 3 times they have to go to change their password
                    if (tries == 0) {
                        String changePassOption = w.getString("You have run out of attempts. Would you like to change your password? (y/n) ");

                        if (changePassOption.equals("y")) {
                            i.createNewPassword();
                        }
                    }
                break;
                //The user does not have an account with us so they must create an account in order to check out
                } else if (rentedBefore.equals("n")) {

                    i.createCustomer();
                    int userID = w.getInt("Enter Your UserID? ");
                    checkout(userID);
                    break;

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

        //if they have returned the movie they had rented then we can rent them a movie
        if (r.returnMovieFromOtherClasses(userID)){
            boolean renting = true;
            System.out.println("--------------\n Checkout \n--------------");
            while (renting) {

                //get the id of the format and movie they want to rent
                int movieID = w.getInt("Ready to Checkout? Enter the id of the movie you would like to rent: ");
                int formatID = w.getInt("Enter the id of the format you want to rent it in: ");

                //check if that movie in that format is in stock
                PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT stockdetails.idmovie, Title\n" +
                        "  FROM stockdetails JOIN movieforms \n" +
                        "    ON stockdetails.idmovie = movieforms.idmovie\n" +
                        " WHERE InStock > 0 AND idformat = ? AND movieforms.idmovie = ?");
                stat.setInt(1, formatID);
                stat.setInt(2, movieID);
                ResultSet rs = stat.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                int count = 0;
                while (rs.next()) {
                    count++;
                }

                //if it is in stock then continue checkout
                if (count > 0) {

                    System.out.println("Your Movie is in Stock in that Format!");

                    PreparedStatement preparedStatementUpdateCus = null;
                    PreparedStatement preparedStatementUpdateForms = null;

                    String updateCusTableSQL = "UPDATE customer SET idmovie = ?, idformat = ? WHERE idcustomer = ?";

                    String updateFormTableSQL = "UPDATE movieforms SET InStock = InStock - 1, CheckedOut = CheckedOut + 1 WHERE idmovie = ? AND idformat = ?";

                    try {

                        preparedStatementUpdateCus = c.getDBConnection().prepareStatement(updateCusTableSQL);
                        preparedStatementUpdateCus.setInt(1, movieID);
                        preparedStatementUpdateCus.setInt(2, formatID);
                        preparedStatementUpdateCus.setInt(3, userID);
                        preparedStatementUpdateCus.executeUpdate();

                        preparedStatementUpdateForms = c.getDBConnection().prepareStatement(updateFormTableSQL);
                        preparedStatementUpdateForms.setInt(1, movieID);
                        preparedStatementUpdateForms.setInt(2, formatID);
                        preparedStatementUpdateForms.executeUpdate();

                        System.out.println("Movie Rented to Your Account!");

                    } catch (SQLException e) {

                        System.out.println(e.getMessage());
                    }

                    //Print out their order for them

                    System.out.println("Here is your order: ");
                    PreparedStatement checkout = c.getDBConnection().prepareStatement("SELECT stockdetails.Title, formats.FormatType, customer.FirstName, customer.LastName " +
                            " FROM movieforms\n" +
                            "JOIN stockdetails ON movieforms.idmovie = stockdetails.idmovie\n" +
                            "JOIN formats ON movieforms.idformat = formats.idformat\n" +
                            "JOIN customer ON movieforms.idmovie = customer.idmovie and movieforms.idformat = customer.idformat\n" +
                            "WHERE idcustomer = ?");
                    checkout.setInt(1,userID);
                    rs = checkout.executeQuery();
                    rsmd = rs.getMetaData();
                    while (rs.next())
                    {
                        for (int i = 1; i <= columnsNumber; i++) {
                            if (i > 1) System.out.print("\t");
                            String columnValue = rs.getString(i);
                            System.out.print(columnValue);
                        }
                        System.out.println("");
                    }

                    System.out.println("Thank You for Renting From Not Blockbuster!");
                    w.promptEnterKey();
                    break;

                //The movie is not in stock
                } else {

                    System.out.println("I'm sorry the movie you wish to rent is not available in that format");
                    String tryAgain = w.getString("Would you like to select a different film? (y/n) ");

                    boolean correct = false;
                    while(!correct) {
                        if (tryAgain.equals("y")) {
                            renting = true;
                            correct = true;
                        } else if (tryAgain.equals("n")) {
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
