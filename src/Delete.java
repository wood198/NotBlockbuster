import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class Delete{

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void deleteOrUpdate() throws Exception {

        Scanner in = new Scanner(System.in);

        boolean correct = true;
        while(correct == true){
            int userID = w.getInt("What is your UserID? ");
            String pass = w.getString("What is your Password? ");

            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT Password FROM passwords WHERE idcustomer = ?");
            stat.setInt(1,userID);

            //TODO: figure out how to actually check to see if password matches the one in the passwords table

            if(stat.equals(pass)){

                int choose = w.getInt("Select an Option: \n" +
                        "1. Delete Account \n" +
                        "2. Update Profile");

                if (choose == 1) {
                    //TODO: this needs to be checked once we have creating a customer operational
                    stat = c.getDBConnection().prepareStatement("DELETE FROM customer AND passwords WHERE userID = ?");
                    stat.setInt(1, userID);

                    stat.execute();

                    System.out.println("Your customer account has been deleted from our system");
                } else if (choose == 2) {

                    boolean answer = true;
                    while (answer = true) {
                        int update = w.getInt("Select something to update: \n" +
                                "1. Email \n" +
                                "2. Address \n" +
                                "3. Phone");
                        if (update == 1) {

                            String newEmail = w.getString("Enter Your Updated Email: ");
                            stat = c.getDBConnection().prepareStatement("UPDATE customer SET Email = ? WHERE idcustomer = ?");
                            stat.setString(1, newEmail);
                            stat.setInt(2, userID);
                            System.out.println("Email has been updated");

                            String continueUpdates = w.getString("Would you like to update something else? (y/n) ");
                            if(continueUpdates.equals("y")){
                                answer = true;
                            }else {
                                answer = false;
                            }

                        } else if (update == 2) {

                            String newAddress = w.getString("Enter Your Updated Address: ");
                            stat = c.getDBConnection().prepareStatement("UPDATE customer SET Address = ? WHERE idcustomer = ?");
                            stat.setString(1, newAddress);
                            stat.setInt(2, userID);
                            System.out.println("Address has been updated");

                            String continueUpdates = w.getString("Would you like to update something else? (y/n) ");
                            if(continueUpdates.equals("y")){
                                answer = true;
                            }else {
                                answer = false;
                            }

                        } else if (update == 3) {

                            //TODO: Make sure they can only enter 10 digits
                            String newPhone = w.getString("Enter Your Updated Phone Number: ");
                            stat = c.getDBConnection().prepareStatement("UPDATE customer SET Phone = ? WHERE idcustomer = ?");
                            stat.setString(1, newPhone);
                            stat.setInt(2, userID);
                            System.out.println("Phone Number has been updated");

                            String continueUpdates = w.getString("Would you like to update something else? (y/n) ");
                            if(continueUpdates.equals("y")){
                                answer = true;
                            }else {
                                answer = false;
                            }

                        } else {

                            System.out.println("That was not an option.");

                        }
                    }

                }

                correct = false;
            }else{
                System.out.println("The password you have entered is incorrect");
            }

        }

    }


}
