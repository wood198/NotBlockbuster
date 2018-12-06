import java.sql.*;
import java.util.Scanner;

public class Returns {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();
    CustomerInfo i = new CustomerInfo();

    //This is the method other methods throw to when they need to see if someone already has a movie rented
    public boolean returnMovieFromOtherClasses(int userID) throws Exception {

        //finds what movie in what format the user might have already rented
        Scanner in = new Scanner(System.in);
        boolean returned = false;
        PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT idmovie, idformat FROM customer WHERE idcustomer = ?");
        stat.setInt(1, userID);
        ResultSet rs = stat.executeQuery();
        int movieID = -1;
        int formatID = -1;
        while(rs.next()) {
            movieID = rs.getInt("idmovie");
            formatID = rs.getInt("idformat");
        }

        //Check to see if the user has a movie rented
        stat = c.getDBConnection().prepareStatement("SELECT idmovie, idformat FROM customer WHERE idmovie IS NOT NULL AND idcustomer = ?");
        stat.setInt(1, userID);
        rs = stat.executeQuery();

        int count = 0;
        while (rs.next()) {
            count++;
        }

        //if they do have a movie rented have them return it to proceed in the other methods (return true if they return the movie)
        if(count > 0) {
            System.out.println("WARNING: If you do not return your movie, you cannot rent another or delete your account");
            String doReturn = w.getString("Would you like to return your Movie? (y/n)");

            boolean running = true;
            while(running == true) {

                if (doReturn.equals("y")) {
                    PreparedStatement preparedStatementUpdateCus = null;
                    PreparedStatement preparedStatementUpdateForms = null;

                    String updateCusTableSQL = "UPDATE customer SET idmovie = ?, idformat = ? WHERE idcustomer = ?";

                    String updateFormTableSQL = "UPDATE movieforms SET InStock = InStock + 1, CheckedOut = CheckedOut - 1 WHERE idmovie = ? AND idformat = ?";

                    try {


                        preparedStatementUpdateCus = c.getDBConnection().prepareStatement(updateCusTableSQL);
                        preparedStatementUpdateCus.setNull(1, java.sql.Types.INTEGER);
                        preparedStatementUpdateCus.setNull(2, java.sql.Types.INTEGER);
                        preparedStatementUpdateCus.setInt(3, userID);
                        preparedStatementUpdateCus.executeUpdate();

                        preparedStatementUpdateForms = c.getDBConnection().prepareStatement(updateFormTableSQL);
                        preparedStatementUpdateForms.setInt(1, movieID);
                        preparedStatementUpdateForms.setInt(2, formatID);
                        preparedStatementUpdateForms.executeUpdate();

                        System.out.println("Done!");

                        returned = true;

                    } catch (SQLException e) {

                        System.out.println(e.getMessage());
                    }

                    running = false;
                    break;

                } else if (doReturn.equals("n")) {
                    System.out.println("Why are you here then? Do you need to change your major to Art History?");
                    w.promptEnterKey();
                    running = false;
                    break;
                } else {
                    System.out.println("That was not an option select again");
                }
            }
        }
        else {
            System.out.println("You do not have a movie rented. We can continue on.");
            returned = true;
            w.promptEnterKey();
        }
        return returned;
    }

    //the renting method that you can access from the main menu
    public void returnMovie() throws Exception{

        //check their userID and password
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

            //if their password is correct then returnt the movie
            if (pass.equals(retrievedPassword)) {
                stat = c.getDBConnection().prepareStatement("SELECT idmovie, idformat FROM customer WHERE idcustomer = ?");
                stat.setInt(1, userID);
                rs = stat.executeQuery();
                int movieID = -1;
                int formatID = -1;
                while(rs.next()) {
                    movieID = rs.getInt("idmovie");
                    formatID = rs.getInt("idformat");
                }

                //tell them what movie they have rented
                System.out.println("Here is the movie you currently have rented: ");
                PreparedStatement returns = c.getDBConnection().prepareStatement("SELECT stockdetails.Title, formats.FormatType" +
                        " FROM movieforms\n" +
                        "JOIN stockdetails ON movieforms.idmovie = stockdetails.idmovie\n" +
                        "JOIN formats ON movieforms.idformat = formats.idformat\n" +
                        "JOIN customer ON movieforms.idmovie = customer.idmovie and movieforms.idformat = customer.idformat\n" +
                        "WHERE idcustomer = ?");
                returns.setInt(1, userID);
                rs = returns.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print("\t");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue);
                    }
                    System.out.println("");
                }

                boolean running = true;
                    while(running == true) {
                        String doReturn = w.getString("Would you like to return your Movie? (y/n)");

                        //if they want to return then return the movie
                        if (doReturn.equals("y")) {

                            PreparedStatement preparedStatementUpdateCus = null;
                            PreparedStatement preparedStatementUpdateForms = null;

                            String updateCusTableSQL = "UPDATE customer SET idmovie = ?, idformat = ? WHERE idcustomer = ?";

                            String updateFormTableSQL = "UPDATE movieforms SET InStock = InStock + 1 , CheckedOut = CheckedOut - 1 WHERE idmovie = ? AND idformat = ?";

                            try {


                                preparedStatementUpdateCus = c.getDBConnection().prepareStatement(updateCusTableSQL);
                                preparedStatementUpdateCus.setNull(1, java.sql.Types.INTEGER);
                                preparedStatementUpdateCus.setNull(2, java.sql.Types.INTEGER);
                                preparedStatementUpdateCus.setInt(3, userID);
                                preparedStatementUpdateCus.executeUpdate();

                                preparedStatementUpdateForms = c.getDBConnection().prepareStatement(updateFormTableSQL);
                                preparedStatementUpdateForms.setInt(1, movieID);
                                preparedStatementUpdateForms.setInt(2, formatID);
                                preparedStatementUpdateForms.executeUpdate();

                                System.out.println("Done!");

                            } catch (SQLException e) {

                                System.out.println(e.getMessage());

                            }

                            running = false;
                            break;

                        } else if (doReturn.equals("n")) {
                            System.out.println("Why are you here then? Do you need to change your major to Art History?");
                            w.promptEnterKey();
                            running = false;
                            break;
                        } else {
                            System.out.println("That was not an option select again");
                        }
                }
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
