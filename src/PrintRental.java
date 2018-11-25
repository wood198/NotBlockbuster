import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class PrintRental {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void checkout() throws Exception {

        Scanner in = new Scanner(System.in);

        //"are you a new customer?"
            //if yes enter userID and password
                //if password is correct then continue to checkout
                //if no then try again (3 tries)
                    //have a change password option?
            //if no then create account w/password then continue to checkout

        //checkout:
            //check to see if customer has a movie already rented
                //if yes say they have to return the movie in order to get a new one (give the option)
                    //if wanting to return send them to Returns.java
                //if no then continue with checkout
            //ask for the id of the movie and the form of the movie they want to check out
            //check if it is in stock
                //if it is then rent it to the customer telling them what the price is (update idmovie in customer table)
            //"Have a nice day!"

    }
}
