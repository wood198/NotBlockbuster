import java.util.Scanner;

public class WelcomeUser {

    public static void main(String[] args) throws Exception{

        WelcomeUser w = new WelcomeUser();
        Search s = new Search();
        CustomerInfo c = new CustomerInfo();
        Returns r = new Returns();
        Delete d = new Delete();
        PrintRental p = new PrintRental();

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
                case(2): //Print All Movies
                    s.printAll();
                    break;
                case(3): //Print Movies by Genre
                    s.printByGenre();
                    break;
                case(4): //Search for Movie
                    s.searchMovie();
                    break;
                case(5): //Rent Movie
                    p.login();
                    break;
                case(6): //Return Movie
                    r.returnMovie();
                    break;
                case(7): //Delete/Update Movie
                    d.deleteOrUpdate();
                    break;
                case(8): //Generate CSV
                default:
                    break;
            }
        }
    }

    public void displayMenu() {
        System.out.println("Not Blockbuster Menu: \n" +
                           "1. Print All (In-Stock) Movies \n" +
                           "2. Print All Movies \n" +
                           "3. Search by Genre \n" +
                           "4. Search for Movie by Title\n" +
                           "5. Rent Movie \n" +
                           "6. Return Movie \n" +
                           "7. Delete/Update Customer Account \n" +
                           "8. Generate CSV File \n" +
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

    public void promptEnterKey(){
        System.out.println("Press \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
