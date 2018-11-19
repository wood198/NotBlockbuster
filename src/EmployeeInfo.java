import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeInfo {
    public void createEmployee() throws Exception {

        DBConnect c = new DBConnect();
        Scanner in = new Scanner(System.in);

        String first_name = "";
        String last_name = "";
        String email_input = "";
        String address_input = "";
        int phone = 0;
        int job = 0;

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
        in.nextLine();
        System.out.println("Do you know the ID number of your job? (y/n) ");
        String jobmenu = in.nextLine();

        if(jobmenu.equals("y")){
            System.out.println("Job Menu: \n" + "1. Manager \n" + "2. Assistant Manager \n" + "3. Cashier \n" + "4. Warehouse Worker")
            System.out.println("What is the ID number of your job?" );
            job = in.nextInt();
            in.nextLine();

        }else(){
            System.out.println("What is the ID number of your job?" );
            job = in.nextInt();
            in.nextLine();
        }

        //TODO change the SQL statement when we figure out databases
        try {
            //TODO figure out if thats how TXNs actually work
            START TRANSACTION;
            PreparedStatement stat = c.getDBConnection().prepareStatement(" INSERT INTO employee (emFirstName, emLastName, emEmail, emAddress, emPhone, idjob)"
                    + " VALUES (?, ?, ?, ?, ?, ?)");

            stat.setString(1, first_name);
            stat.setString(2, last_name);
            stat.setString(3, email_input);
            stat.setString(4, address_input);
            stat.setInt(5, phone);
            stat.setInt(6, job);

            stat.execute();
            COMMIT;

            System.out.println("New employee added to our system!");
        }catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
}
