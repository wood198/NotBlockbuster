import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerInfo {
    public void createCustomer() throws Exception {

        DBConnect c = new DBConnect();
        Scanner in = new Scanner(System.in);

        String first_name = "";
        String last_name = "";
        String email_input = "";
        String address_input = "";
        int phone = 0;

        System.out.println("Please enter your first name: ");
        first_name = in.nextLine();
        System.out.println("Please enter your last name: ");
        last_name = in.nextLine();
        System.out.println("Please enter your email: ");
        email_input = in.nextline();
        System.out.println("Please enter your address: ");
        address_input = in.nextLine();
        System.out.println("Pleas enter your phone number with no dashes: ");
        phone = in.nextInt();
        in.nextLine(); //consumes the \n


        //TODO change the SQL statement when we figure out databases
        try {
            //TODO figure out if thats how TXNs actually work
            START TRANSACTION;
            PreparedStatement stat = c.getDBConnection().prepareStatement(" INSERT INTO student (cusFirstName, cusLastName, cusEmail, cusAddress, cusPhone)"
                    + " VALUES (?, ?, ?, ?, ?)");

            stat.setString(1, first_name);
            stat.setString(2, last_name);
            stat.setString(3, email_input);
            stat.setString(4, address_input);
            stat.setInt(5, phone);

            stat.execute();
            COMMIT;

            System.out.println("New customer added!");
        }catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
}
