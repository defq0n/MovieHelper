/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;

/**
 * A bean to handle data unique to each user.
 * @author Zach
 */
@Named(value = "user")
@SessionScoped
public class UserBean implements Serializable {
    DatabaseBean database;
    public UserBean()
    {
        username = "";
        password = "";
        loggedIn = false;
        faveMovie = "";
        reviews = new HashMap<>();
        database = new DatabaseBean();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getPasswordValidation() {
        return passwordValidation;
    }

    public void setPasswordValidation(String passwordValidation) {
        this.passwordValidation = passwordValidation;
    }

    public String getFaveMovie() {
        return faveMovie;
    }

    public void setFaveMovie(String faveMovie) {
        this.faveMovie = faveMovie;
    }

    //This review map format probably won't make the final cut
    public Map<String, Pair<Integer, String>> getReviews() {
        return reviews;
    }
    
    public ArrayList<Entry<String, Pair<Integer, String>>> getReviewList()
    {
        Set<Entry<String, Pair<Integer, String>>> reviewSet = reviews.entrySet();
        return new ArrayList<>(reviewSet);
    }
    
    /**
     * Action method to handle requirements of logging in
     * @return the "search" action
     */
    public String login()
    {
        addReview("Dummy Movie", 5, "This movie seemed really bland to me. Almost like the creators were just trying to come up with something to take up space, or maybe show off some kind of proof-of-concept. "
                                    + "A solid 5 from me.");
        //STUB
        loggedIn = true;
        return "search";
    }
    
    /**
     * Action method to handle requirements of registration
     * @return the "search" action if successful, the "register" action otherwise
     */
    public String register()
    {
        addReview("Dummy Movie", 5, "This movie seemed really bland to me. Almost like the creators were just trying to come up with something to take up space, or maybe show off some kind of proof-of-concept. "
                                    + "A solid 5 from me.");
        try {
            //STUB
            database.addUser(username, password);
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        } catch (IOException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!password.equals(passwordValidation))
        {
            //Passwords did not match
            return "register";
        }
        loggedIn = true;
        return "search";
    }
    
    public String logout()
    {
        this.username = "";
        this.password = "";
        loggedIn = false;
        return "search";
    }
    
    /**
     * Adds a review for the current user.
     * @param title Title of the movie being reviewed
     * @param rating Numerical rating of the movie
     * @param text Text of the review
     */
    public void addReview(String title, int rating, String text)
    {
        reviews.put(title, new Pair(rating, text));
    }
    
    String username;
    String password;
    String passwordValidation;
    Boolean loggedIn;
    
    String faveMovie;
    
    //Map from a movie title to the integer score and text of a review
    Map<String, Pair<Integer, String>> reviews;
    
    /**
     * Private helper class for review map
     * @param <X> The first object in the pair
     * @param <Y> The second object in the pair
     */
    private class Pair<X, Y>
    {
        public final X x;
        public final Y y;
        
        public Pair(X x, Y y)
        {
            this.x = x;
            this.y = y;
        }
        
        public final X getFirst()
        {
            return x;
        }
        
        public final Y getSecond()
        {
            return y;
        }
    }
}
