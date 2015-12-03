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
        //***TODO: add user
        //TODO: verify password
        //***TODO: user exists
    
    //TODO: MOVIE DATABASE STUFF
        //TODO: add movie
        //TODO: get movie
 
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
    
    //TODO: user-exists sql query
    // Checks the database to see if a user exists
    public boolean userExists(String username) 
            throws SQLException, IOException {
        String query = SQL.getSQL("user-exists");
        Connection dbConnection;
        dbConnection = movieSource.getConnection();
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
    
    //TODO: password sql query
    public boolean checkPassword(String username, String password)
            throws SQLException, IOException {
        String query = SQL.getSQL("password-query");
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
    //TODO: movie search sql query
    //searches the database for a movie matching the given criteria
    public void searchMovie(String genre, String minReleaseYear, String maxReleaseYear, int rating, String keyword) 
            throws SQLException, IOException {
        String query = SQL.getSQL("search-movie");
        Connection dbConnection = movieSource.getConnection();
        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            //TODO: set each search criteria 
            // i.e. statement.setString(1, genre)
            ResultSet results = statement.executeQuery();
            results.next();
            //TODO: do stuff with the results contained in resultSet
                //movie name
            //create movie bean and return it?
            //MovieBean movieResult = new MovieBean();
            // figure out what to return
        } finally {
            dbConnection.close();
        }
    }
    
    public void addMovie(String title, String genre, String releaseYear, int rating)
            throws SQLException, IOException {
        
    }
////////////////////////////////////////////////////////////////////////////////

}
