package com.moviehelper.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 * A bean to handle the requirements of searching the movie database.
 * @author zach
 */
@Named(value = "search")
@SessionScoped
public class Search implements Serializable
{
    /**
     * Initializes search options.
     */
    public Search()
    {
        dates = new ArrayList<>();
        ratings = new ArrayList<>();
        genres = new ArrayList<>();
        for (int i = 1950; i <= 2010; i += 10)
        {
            dates.add(i);
        }
        for (int i = 0; i <= 10; i++)
        {
            ratings.add(i);
        }
        genres.add("Drama");
        genres.add("Comedy");
        genres.add("Action");
        genres.add("Sci-fi");
        genres.add("Fantasy");
        genres.add("Romance");
        genres.add("Horror");
    }
    
    public List<Integer> getDates()
    {
        return dates;
    }

    public List<Integer> getRatings()
    {
        return ratings;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public List<Movie> getResults()
    {
        return results;
    }

    public void setMinReleaseYear(int minReleaseYear)
    {
        this.minReleaseYear = minReleaseYear;
    }

    public void setMaxReleaseYear(int maxReleaseYear)
    {
        this.maxReleaseYear = maxReleaseYear;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }
    
    public void setKeyword(String key)
    {
        this.keyword = key;
    }
    
    public String getKeyword()
    {
        return this.keyword;
    }

    public String getGenre()
    {
        return genre;
    }

    public int getMinReleaseYear()
    {
        return minReleaseYear;
    }

    public int getMaxReleaseYear()
    {
        return maxReleaseYear;
    }

    public int getRating()
    {
        return rating;
    }
    
    /**
     * Checks that a keyword is not empty, then adds it to the list of keywords.
     * @param keyword The keyword to be added.
     */
    
    //Criteria given by the user
    int minReleaseYear;
    int maxReleaseYear;
    int rating;
    String genre;
    String keyword;
    
    //Options for drop down menus
    List<Integer> dates;
    List<Integer> ratings;
    List<String> genres;

    List<Movie> results;  //Results of a search
    
}
