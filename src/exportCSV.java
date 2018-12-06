import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class exportCSV {
    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void generateReport() throws Exception {
        String cusfilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\customer.csv";
        try {
            FileWriter fw = new FileWriter(cusfilename);
            String query = "select * from customer";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append(',');
                fw.append(rs.getString(8));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Customer File Failed to Export");
            e.printStackTrace();
        }

        String formfilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\formats.csv";
        try {
            FileWriter fw = new FileWriter(formfilename);
            String query = "select * from formats";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Format File Failed to Export");
            e.printStackTrace();
        }

        String genresfilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\genres.csv";
        try {
            FileWriter fw = new FileWriter(genresfilename);
            String query = "select * from genres";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Genres File Failed to Export");
            e.printStackTrace();
        }

        String moviefilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\movieForms.csv";
        try {
            FileWriter fw = new FileWriter(moviefilename);
            String query = "select * from movieforms";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("MovieForms File Failed to Export");
            e.printStackTrace();
        }

        String movgenfilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\movieGenre.csv";
        try {
            FileWriter fw = new FileWriter(movgenfilename);
            String query = "select * from moviegenre";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("MovieGenre File Failed to Export");
            e.printStackTrace();
        }

        String passfilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\passwords.csv";
        try {
            FileWriter fw = new FileWriter(passfilename);
            String query = "select * from passwords";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Password File Failed to Export");
            e.printStackTrace();
        }

        String prodfilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\production.csv";
        try {
            FileWriter fw = new FileWriter(prodfilename);
            String query = "select * from production";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println("Production File Failed to Export");
            e.printStackTrace();
        }

        String stockfilename = "C:\\Users\\Ashley-Laptop\\Desktop\\DBProjectCSVs\\stockDetails.csv";
        try {
            FileWriter fw = new FileWriter(stockfilename);
            String query = "select * from stockdetails";
            Statement stmt = c.getDBConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            System.out.println("Files have been Generated");
        } catch (Exception e) {
            System.out.println("StockDetails File Failed to Export");
            e.printStackTrace();
        }
    }
}
