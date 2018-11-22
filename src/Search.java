import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class Search {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void printInStock() throws Exception {
        try{
            //Print all the movies in stock
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT idmovie, Title, Price FROM stockdetails " +
                    "FULL OUTER JOIN movieforms ON stockdetails.idmovie = movieforms.idmovie WHERE InStock = 1, CheckedOut = 0");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

        } catch(SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    // I dont think we need to print all the things that have been checked out. Bc why would the customers care?
    public void printCheckedOut() throws Exception {
        try{
            //Print all the movies in stock
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT idmovie, Title, Price FROM stockdetails " +
                    "FULL OUTER JOIN movieforms ON stockdetails.idmovie = movieforms.idmovie WHERE CheckedOut = 1, InStock = 0");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

        } catch(SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
   }

    public void printAll() throws Exception {
        try{
            //Print all the movies whether or not they are in stock
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT idmovie, Title, Price FROM stockdetails");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

        } catch(SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void printByGenre() throws Exception {
        try{
            //Printing out a list of genres with their ID numbers
            System.out.println("Here is a list of Genres: ");
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT idmovie, Title, Price FROM stockdetails s " +
                    "FULL OUTER JOIN moviegenre m AND genres g ON s.idmovie = m.idmovie AND m.idgenre = g.idgenre" +
                    "/*SELECT idmovie, Title, Price FROM genres*/");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            //Tell the user the Genre they chose
            int genreID = w.getInt("What is number of the genre you would like to search by? ");
            stat = c.getDBConnection().prepareStatement("SELECT GenreType FROM genres WHERE idgenre = ?");
            stat.setInt(1, genreID);
            rs = stat.executeQuery();
            rsmd = rs.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print("You are searching by the genre: " + columnValue);
                }
                System.out.println("");
            }

            //Print the list of movies in that genre
            //TODO: I think this is how you code for foreign keys but correct me if I'm wrong in the SQL statement
            stat = c.getDBConnection().prepareStatement("SELECT idmovie, Title, Price FROM stockdetails s " +
                    "FULL OUTER JOIN moviegenre m ON s.idmovie = m.idmovie WHERE idgenre = ?");
            stat.setInt(1, genreID);
            rs = stat.executeQuery();
            rsmd = rs.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print("You are searching by the genre: " + columnValue);
                }
                System.out.println("");
            }



        }catch(SQLException ex){
        // handle any errors
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void searchMovie() throws Exception {

    }
}
