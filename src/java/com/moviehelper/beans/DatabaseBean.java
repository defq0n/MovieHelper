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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

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
    
        //TODO: search movie multiple results

 
    //rate movie?
    //review movie?
 
    
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

//MOVIE DATABASE////////////////////////////////////////////////////////////////
    //TODO: Multiple results
    //searches the database for a movie matching the given criteria
    public MovieBean searchMovie(String genre, String minReleaseYear, String maxReleaseYear, int rating, String keyword) 
            throws SQLException, IOException {
        String query = SQL.getSQL("search-movie");
        
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
            results.next();

////////TODO: Handle multiple results
                //this function should be modified to return a list of movie beans
                //loop through 'results' with while(results.next()) to get next movie
            //create movie bean and return it
            MovieBean movieResult = new MovieBean(results.getString("title"), results.getString("description"), results.getString("genre"), results.getString("release_year"), null, results.getInt("rating"));
            return movieResult;
            
            // figure out what to return
        } finally {
            dbConnection.close();
        }
    }
    
    public void addMovie(String title, String genre, String releaseYear, int rating)
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
//            statement.setString(2, poster_link);
//            statement.setString(3, trailer_link);
            statement.setInt(4, rating);
//            statement.setString(5, actors);
            statement.setString(5, genre);
//            statement.setString(6, description);
            statement.setString(7, query);
            statement.executeUpdate();
        } finally {
            dbConnection.close();
        }
        
    }
////////////////////////////////////////////////////////////////////////////////

}
