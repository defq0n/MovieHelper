/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.moviehelper.beans.DatabaseBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 *
 * @author Zach
 */
@Named(value="profile")
@SessionScoped
public class ProfileBean implements Serializable {
    @Inject private DatabaseBean database;
    @Inject private UserBean user;
    
    int userReviewCount;
    List<Review> userReviews;
    
    public ProfileBean() throws SQLException, IOException
    {
    }
    
    @PostConstruct
    public void profileSetup()
    {
        try {
            userReviewCount = database.getReviewsForUser(user.getUsername()).size();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            userReviews = database.getReviewsForUser(user.getUsername());
        } catch (SQLException ex) {
            Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProfileBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getUserReviewCount() {
        return userReviewCount;
    }

    public void setUserReviewCount(int userReviewCount) {
        this.userReviewCount = userReviewCount;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<Review> userReviews) {
        this.userReviews = userReviews;
    }
    
    
}
