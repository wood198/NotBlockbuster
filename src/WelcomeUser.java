//import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.Scanner;

public class WelcomeUser {
    public static void main(String args[]) throws Exception{

        WelcomeUser w = new WelcomeUser();
        Search s = new Search();
        CustomerInfo c = new CustomerInfo();
        Returns r = new Returns();

        boolean loop = true;

        System.out.println("---- Welcome to Not Blockbuster! ----\n\n");

        while(loop) {
            w.displayMenu();
            int x = getInt("Please Enter a Menu Option: ");

            switch(x) {
                case(0): //Quit
                    System.out.println("---- EXITING PROGRAM ----");
                    loop = false;
                    break;
                case(1): //Print All (In-Stock) Movies
                    s.printInStock();
                    break;
                case(2): //Print All (Checked Out) Movies
                    s.printCheckedOut();
                    break;
                case(3): //Print All Movies
                    s.printAll();
                    break;
                case(4): //Print Movies by Genre
                    s.printByGenre();
                    break;
                case(5): //Search for Movie
                    s.searchMovie();
                    break;
                case(6): //Rent Movie
                case(7): //Return Movie
                    r.returnMovie();
                    break;
            }
        }
    }

    public void displayMenu() {
        System.out.println("Not Blockbuster Menu: \n" +
                           "1. Print All (In-Stock) Movies \n" +
                           "2. Print All (Checked Out) Movies \n" +
                           "3. Print All Movies \n" +
                           "4. Print Movies by Genre \n" +
                           "5. Search for Movie \n" +
                           "6. Rent Movie \n" +
                           "7. Return Movie \n" +
                           "0. Quit");
    }

    public static String getString(String input) {
        System.out.print(input);
        Scanner temp = new Scanner(System.in);
        return temp.nextLine();
    }

    public static float getFloat(String input) {
        while (true) {
            String temp = getString(input);
            try {
                return Float.parseFloat(temp);
            }
            catch(Exception e) {
                System.out.println("Please Enter a Float");
            }
        }
    }

    public static int getInt(String input) {
        while (true) {
            String temp = getString(input);
            try {
                return Integer.parseInt(temp);
            }
            catch(Exception e) {
                System.out.println("Please Enter an Integer");
            }
        }
    }
}
