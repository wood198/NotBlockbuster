import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeInfo {
    public void createEmployee() throws Exception {

        DBConnect c = new DBConnect();
        Scanner in = new Scanner(System.in);

        //TODO Change the variable names/prompts
        String  = "";
        String last_name = "";
        double gpa = 0.0;
        String major = "";
        String advisor = "";

        System.out.println("");
        first_name = in.nextLine();
        System.out.println("");
        last_name = in.nextLine();
        System.out.println("");
        gpa = in.nextDouble();
        in.nextLine(); // Consumes the \n
        System.out.println("");
        major = in.nextLine();
        System.out.println("");
        advisor = in.nextLine();

        //TODO change the SQL statement when we figure out databases
        try {
            PreparedStatement stat = c.getDBConnection().prepareStatement(" INSERT INTO student (FirstName, LastName, GPA, Major, FacultyAdvisor)"
                    + " VALUES (?, ?, ?, ?, ?)");

            stat.setString(1, first_name);
            stat.setString(2, last_name);
            stat.setDouble(3, gpa);
            stat.setString(4, major);
            stat.setString(5, advisor);

            stat.execute();

            System.out.println("New customer added!");
        }catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
}
