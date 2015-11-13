/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.beans;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;

/**
 * A bean to handle data unique to each user.
 * @author Zach
 */
@Named(value = "user")
@ConversationScoped
public class User implements Serializable {
    
    public User()
    {
        username = "";
        password = "";
        loggedIn = false;
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
    
    String username;
    String password;
    Boolean loggedIn;
}
