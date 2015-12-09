/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;

/**
 * A bean to store data related to a movie.
 * @author zach
 */
//@Named(value = "movie")
//@ConversationScoped
public class MovieBean implements Serializable
{
    public MovieBean(String name, String description, String genre, String releaseDate, List<String> contributors, int rating, String poster_link, String trailer_link)
    {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.contributors = contributors;
        this.rating = rating;
        this.imageURL = poster_link;
        this.trailerURL = trailer_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getContributors() {
        return contributors;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }

    public Map<String, String> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, String> reviews) {
        this.reviews = reviews;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    private String name;
    private String description;
    private String trailerURL;
    private String imageURL;
    private String genre;
    private String releaseDate;
    private List<String> contributors;
    private Map<String, String> reviews;    //Map from username to the reveiw
    private int rating;
}
