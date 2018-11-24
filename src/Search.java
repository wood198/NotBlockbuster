import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class Search {

    DBConnect c = new DBConnect();
    WelcomeUser w = new WelcomeUser();

    public void printInStock() throws Exception {
        try{
            System.out.println("Here are the formats movies come in: ");
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT * FROM formats");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            int formatID = w.getInt("Which of these formats would you like the movie you rent to be in? ");

            //Print all the movies in stock
            stat = c.getDBConnection().prepareStatement("SELECT stockdetails.idmovie, Title\n" +
                    "  FROM stockdetails JOIN movieforms \n" +
                    "    ON stockdetails.idmovie = movieforms.idmovie\n" +
                    " WHERE InStock > 0 AND idformat = ?");
            stat.setInt(1, formatID);
            rs = stat.executeQuery();
            rsmd = rs.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
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
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT idmovie, Title FROM stockdetails");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
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
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT * FROM genres");
            ResultSet rs = stat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            //Tell the user the Genre they chose
            int genreID = w.getInt("What is number of the genre you would like to search by? ");
            stat = c.getDBConnection().prepareStatement("SELECT GenreType FROM genres WHERE idgenre = ?");
            stat.setInt(1, genreID);
            System.out.println("");
            rs = stat.executeQuery();
            rsmd = rs.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
                    String columnValue = rs.getString(i);
                    System.out.print("You are searching by genre: " + columnValue);
                }
                System.out.println("");
            }

            //Print the list of movies in that genre
            stat = c.getDBConnection().prepareStatement("SELECT stockdetails.idmovie, Title\n" +
                    "  FROM stockdetails JOIN moviegenre \n" +
                    "    ON stockdetails.idmovie = moviegenre.idmovie\n" +
                    " WHERE idgenre = ?");
            stat.setInt(1, genreID);
            rs = stat.executeQuery();
            rsmd = rs.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
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

        try{
            String movieSearch = w.getString("Title or Keyword: ");

            //Searching by the users terms
            PreparedStatement stat = c.getDBConnection().prepareStatement("SELECT idmovie, Title FROM stockdetails WHERE Title LIKE ?");
            stat.setString(1, "%" + movieSearch + "%");
            ResultSet rs = stat.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print("  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
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
}
