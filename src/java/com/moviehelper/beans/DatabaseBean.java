/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.beans;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

import com.moviehelper.moviescraper.Movie;
import com.moviehelper.moviescraper.MovieScraper;
import java.util.Scanner;

import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * A bean to handle interactions between the database and server-side code
 * @author Zach
 */
@Named(value = "databaseBean")
@ApplicationScoped
public class DatabaseBean {
    @Resource(name="jdbc/movies")
     private DataSource movieSource;   
    //TODO: USER DATABASE STUFF
        //DONE***TODO: add user
    
        //DONE***TODO: verify password

        //DONE***TODO: user exists

    
    //TODO: MOVIE DATABASE STUFF
        //TODO: add movie need info for database fields

        //TODO: get movie: possibly use search movie stuff?
            //need sql file
    
        //DONE***TODO: search movie

    /**
     * Add a review for a movie.
     * @param title the title of the movie
     * @param text the text of the reivew
     * @param username the user leaving the review
     * @param rating the numerical rating for the movie
     * @throws IOException
     * @throws SQLException 
     */
    public void addReview(String title, String text, String username, String rating) throws IOException, SQLException
    {
        System.out.println("review data: " + title + ", " + text + ", " + username + ", " + rating + "/ 10");
        String query = SQL.getSQL("add-review");
        
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection("admin","team4");
        
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, username);
            statement.setString(3, text);
            statement.setString(4, rating);
            statement.executeUpdate();
        }
        finally {
            dbConnection.close();
        }
    }
 
    
//USER DATABASE/////////////////////////////////////////////////////////////////    
    // Adds a user to the database
    public void addUser(String username, String password)
            throws SQLException, IOException {
        String query = SQL.getSQL("create-user");
        
        // I'm getting a nullpointer exception from the DataSource if it is not
        // initialized in this way
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection("admin","team4");
        
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }
    
    // Checks the database to see if "username" exists
    public boolean userExists(String username) 
            throws SQLException, IOException {
        String query = SQL.getSQL("user-exists");
        
        // I'm getting a nullpointer exception from the DataSource if it is not
        // initialized in this way
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection();
        
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet results = statement.executeQuery();
            // things don't work correctly without calling next() before looking at the results
            results.next();
            try {
                results.getString("USERNAME");
            } catch (SQLException ex) {
                return false;
            }
            return true;
        } finally {
            dbConnection.close();
        }
    }
    
    // checks to see if "password" matches the password stored in the database
    // for "username"
    public boolean checkPassword(String username, String password)
            throws SQLException, IOException {
        String query = SQL.getSQL("password-query");
        
        // I'm getting a nullpointer exception from the DataSource if it is not
        // initialized in this way
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection();
        
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if(resultSet.getString("PASSWORD").equalsIgnoreCase(password)) {
                //password matches password in database
                return true;
            }
            else {
                //passwords don't match
                return false;
            }
        } finally {
            dbConnection.close();
        }
    }
////////////////////////////////////////////////////////////////////////////////

//MOVIE DATABASE///////////////////////////////////////////////////////////////
    //searches the database for a movie matching the given criteria
    public List<Movie> searchMovie(String genre, String minReleaseYear, String maxReleaseYear, int rating, String keyword) 
            throws SQLException, IOException {
        String query = SQL.getSQL("search-movie");
        List<Movie> dbResults = new ArrayList<>();
        
        // I'm getting a nullpointer exception from the DataSource if it is not
        // initialized in this way
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            
            //add wildcard '%' to keyword for searching
            keyword = "%" + keyword + "%";
            
            statement.setString(1, genre);
            statement.setString(2, minReleaseYear);
            statement.setString(3, maxReleaseYear);
            statement.setDouble(4, rating);
            statement.setString(5, keyword);
            statement.setString(6, keyword);
            statement.setString(7, keyword);
            ResultSet results = statement.executeQuery();

            //create movie bean list of results and return it
            while(results.next()) {
                dbResults.add(new Movie(results.getString("title"), results.getString("description"), results.getString("genre"), results.getString("release_year"), null, results.getInt("rating"), results.getString("poster_link"), results.getString("trailer_link")));
            }
            return dbResults;
            
        } finally {
            dbConnection.close();
        }
    }
    
    public Movie getMovie(String title)
            throws SQLException, IOException {
        String query = SQL.getSQL("get-movie");
        
        // I'm getting a nullpointer exception from the DataSource if it is not
        // initialized in this way
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection("admin","team4");
        
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, title);
            ResultSet results = statement.executeQuery();
            results.next();
            return new Movie(results.getString("title"), results.getString("description"), results.getString("genre"), results.getString("release_year"), null, results.getInt("rating"), results.getString("poster_link"), results.getString("trailer_link"));
        } finally {
            dbConnection.close();
        }
    }
    
    public void addMovie(String title, String posterLink, String trailerLink, String actors, String description, String genre, String releaseYear, int rating)
            throws SQLException, IOException {
        String query = SQL.getSQL("add-movie");
        
        // I'm getting a nullpointer exception from the DataSource if it is not
        // initialized in this way
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection("admin","team4");
        
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            //switch ordering of these to match what is needed in sql query file
            statement.setString(1, title);
            statement.setString(2, posterLink);
            statement.setString(3, trailerLink);
            statement.setInt(4, rating);
            statement.setString(5, actors);
            statement.setString(5, genre);
            statement.setString(6, description);
            statement.setString(7, query);
            statement.executeUpdate();
        } finally {
            dbConnection.close();
        }     
    }
    
    public void addMovie(Movie movie)
            throws SQLException, IOException {
        String query = SQL.getSQL("add-movie");
        
        // I'm getting a nullpointer exception from the DataSource if it is not
        // initialized in this way
        Context initialContext = null;
        try {
            initialContext = new InitialContext();
            movieSource = (DataSource) initialContext.lookup("jdbc/movies");
        } catch (NamingException ex) {
            Logger.getLogger(DatabaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection dbConnection = movieSource.getConnection("admin","team4");
        
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            //switch ordering of these to match what is needed in sql query file
            statement.setString(1, movie.getMovieName());
            statement.setString(2, movie.getPosterLink());
            statement.setString(3, movie.getTrailerLink());
            statement.setInt(4, movie.getRating());
            statement.setString(5, movie.getActors());
            statement.setString(5, movie.getGenre());
            statement.setString(6, movie.getMovieDescription());
            statement.setString(7, movie.getMovieYear());
            statement.executeUpdate();
        } finally {
            dbConnection.close();
        }     
    }
    
    public static void main(String[] args){
        ArrayList<Movie> movies = MovieScraper.getMovies("Harry potter");
        for(Movie t : movies){
            System.out.println(t.toString());
        }
        System.out.println("\n\nIs this information alright to add to the databse? (y/n)");
        Scanner sc = new Scanner(System.in);
        String answer = sc.next();
        if(answer.equals("y")){
            for(Movie m : movies){
                try {
                addMovie(m); //FIX
                } catch (Exception e){
                    System.out.println(e.toString());
                }
            }
            System.out.println("\n\nAdding the movies to the database is complete.");
        } else {
            // try different search
        }   
    }
////////////////////////////////////////////////////////////////////////////////
}
