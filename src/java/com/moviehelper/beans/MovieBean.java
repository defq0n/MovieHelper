/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.beans;

import com.moviehelper.moviescraper.Movie;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 * A bean to store data related to a movie.
 * @author zach
 */
@Named(value = "movie")
@SessionScoped
public class MovieBean implements Serializable
{
    public MovieBean(/*String name, String description, String genre, String releaseDate, List<String> contributors, int rating, String poster_link, String trailer_link*/)
    {
        this.name = "";
        this.description = "";
        this.genre = "";
        this.releaseDate = "";
        this.contributors = new ArrayList<>();
        this.rating = 0;
        this.imageURL = "";
        this.trailerURL = "";
        this.userReviewRating = "";
        this.userReviewText = "";
        ratings = new ArrayList<>();
        for (int i = 0; i <= 10; i++) ratings.add(Integer.toString(i));
    }
//    public MovieBean(String name, String description, String genre, String releaseDate, List<String> contributors, int rating, String poster_link, String trailer_link)
//    {
//        this.name = name;
//        this.description = description;
//        this.genre = genre;
//        this.releaseDate = releaseDate;
//        this.contributors = new ArrayList<>(); // get from database
//        this.rating = rating;
//        this.imageURL = poster_link;
//        this.trailerURL = trailer_link;
//        this.userReviewRating = ""; // from database
//        this.userReviewText = "";  //from database
//        ratings = new ArrayList<>();
//        for (int i = 0; i <= 10; i++) ratings.add(Integer.toString(i));
//    }
    
    @Inject DatabaseBean database;
    @Inject UserBean user;

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
    
    public String review() throws IOException, SQLException
    {
        System.out.println("review data from moviebean: " + this.name + ", " + this.userReviewText + ", " + user.username);
        database.addReview(this.name, this.userReviewText, user.getUsername(), Integer.valueOf(this.userReviewRating));
        this.rating = database.updateRating(this.name);
        this.userReviewRating = "";
        this.userReviewText = "";
        return "movie";
    }

    public String getUserReviewText()
    {
        return userReviewText;
    }

    public void setUserReviewText(String userReviewText)
    {
        this.userReviewText = userReviewText;
    }

    public String getUserReviewRating()
    {
        return userReviewRating;
    }

    public void setUserReviewRating(String userReviewRating)
    {
        this.userReviewRating = userReviewRating;
    }

    public List<String> getRatings()
    {
        return ratings;
    }

    public void setRatings(List<String> ratings)
    {
        this.ratings = ratings;
    }
    
    public String loadMovie(Movie m)
    {
        System.out.println("name from loadMovie: " + m.getMovieName());
        this.name = m.getMovieName();
        this.description = m.getMovieDescription();
        this.genre = m.getGenre();
        this.releaseDate = m.getMovieYear();
        //this.contributors = Arrays.asList(m.getActors());
        this.rating = m.getRating();
        this.imageURL = m.getPosterLink();
        this.trailerURL = m.getTrailerLink();
        return "movie";
    }
    
    private String name;
    private String description;
    private String trailerURL;
    private String imageURL;
    private String genre;
    private String releaseDate;
    private List<String> contributors;
    private Map<String, String> reviews;   //Map from username to the reveiw
    private int rating;
    
    String userReviewText;
    String userReviewRating;
    
    List<String> ratings;
}
