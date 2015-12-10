/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.beans;

/**
 *
 * @author Zach
 */
public class Review {

    public String user;
    public String title;
    public int score;
    public String text;

    public Review(String user, String title, int score, String text) {
        this.user = user;
        this.title = title;
        this.score = score;
        this.text = text;
    }

    public Review(String title, int score, String text) {
        this.user = "should be gotten in xhtml";
        this.title = title;
        this.score = score;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return user + ", " + score + ", " + title + ", " + text;
    }

}
