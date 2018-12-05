import java.sql.*;
import java.util.Scanner;

public class Returns {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();
    CustomerInfo i = new CustomerInfo();

    public boolean returnMovieFromOtherClasses(int userID) throws Exception {

        Scanner in = new Scanner(System.in);
        boolean returned = false;
        int tries = 3;
        boolean correctPassword = true;
        while (correctPassword) {
            String pass = w.getString("Confirm Your Password: ");

            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT Password FROM passwords WHERE idcustomer = ?");
            stat.setInt(1, userID);
            ResultSet rs = stat.executeQuery();
            String retrievedPassword = null;
            while (rs.next()) {
                retrievedPassword = rs.getString("Password");
            }

            if (pass.equals(retrievedPassword)) {
                //TODO: ENTER ALL THE CHECKING FOR CURRENTLY RENTED MOVIES SQL STUFF HERE
                stat = c.getDBConnection().prepareStatement("SELECT idmovie, idformat FROM customer WHERE idcustomer = ?");
                stat.setInt(1, userID);
                rs = stat.executeQuery();
                int movieID = -1;
                int formatID = -1;
                while(rs.next()) {
                    movieID = rs.getInt("idmovie");
                    formatID = rs.getInt("idformat");
                }

                stat = c.getDBConnection().prepareStatement("SELECT idmovie, idformat FROM customer WHERE idmovie IS NOT NULL AND idcustomer = ?");
                stat.setInt(1, userID);
                rs = stat.executeQuery();

                int count = 0;
                while (rs.next()) {
                    count++;
                }

                //TODO: CHECK FOR MOVIE,
                if(count == 0) {
                    System.out.println("You do not have a movie rented");
                    returned = true;
                    correctPassword = false;
                    w.promptEnterKey();
                    break;
                }
                else if(count > 0) {
                    System.out.println("WARNING: If you do not return your movie, you cannot rent another or delete your account");
                    String doReturn = w.getString("Would you like to return your Movie? (y/n)");

                    if(doReturn == "y") {
                        PreparedStatement updateCustomersRental = c.getDBConnection().prepareStatement("BEGIN TRANSACTION UPDATE customer SET idmovie = ? and idformat = ? WHERE idcustomer = ?" +
                                "UPDATE movieforms SET InStock = InStock + 1 and CheckedOut = CheckedOut - 1 WHERE idmovie = ? and idformat = ? COMMIT TRANSACTION");
                        updateCustomersRental.setInt(1, movieID);
                        updateCustomersRental.setInt(2, formatID);
                        updateCustomersRental.setInt(3, userID);
                        updateCustomersRental.setInt(4, movieID);
                        updateCustomersRental.setInt(5, formatID);
                        returned = true;
                    }
                    else {
                        returned = false;
                    }
                }
                break;

            }
            else {
                System.out.println("The password you have entered is incorrect");
                tries--;
                if(tries == 0){
                    correctPassword = false;
                }
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
                stat = c.getDBConnection().prepareStatement("SELECT idmovie, idformat FROM customer WHERE idcustomer = ?");
                stat.setInt(1, userID);
                rs = stat.executeQuery();
                int movieID = -1;
                int formatID = -1;
                while(rs.next()) {
                    movieID = rs.getInt("idmovie");
                    formatID = rs.getInt("idformat");
                }

                PreparedStatement checkout = c.getDBConnection().prepareStatement("SELECT stockdetails.Title, formats.FormatType" +
                        " FROM movieforms\n" +
                        "JOIN stockdetails ON movieforms.idmovie = stockdetails.idmovie\n" +
                        "JOIN formats ON movieforms.idformat = formats.idformat");
                rs = checkout.executeQuery();
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

                //TODO: ask if they wish to return it
                String doReturn = w.getString("Would you like to return your Movie? (y/n)");

                if(doReturn == "y") {
                    PreparedStatement updateCustomersRental = c.getDBConnection().prepareStatement("BEGIN TRANSACTION UPDATE customer SET idmovie = ? and idformat = ? WHERE idcustomer = ?" +
                            "UPDATE movieforms SET InStock = InStock + 1 and CheckedOut = CheckedOut - 1 WHERE idmovie = ? and idformat = ? COMMIT TRANSACTION");
                    updateCustomersRental.setInt(1, movieID);
                    updateCustomersRental.setInt(2, formatID);
                    updateCustomersRental.setInt(3, userID);
                    updateCustomersRental.setInt(4, movieID);
                    updateCustomersRental.setInt(5, formatID);

                    System.out.println("");
                }
                else {
                    System.out.println("Why are you here then? Do you need to change your major to Art History?");
                    break;
                }

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
