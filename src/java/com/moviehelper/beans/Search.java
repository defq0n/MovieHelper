package com.moviehelper.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;

/**
 * A bean to handle the requirements of searching the movie database.
 * @author zach
 */
@Named(value = "search")
@ConversationScoped
public class Search implements Serializable
{
    /**
     * Initializes search options.
     */
    public Search()
    {
        results = new ArrayList<>();
        dates = new ArrayList<>();
        ratings = new ArrayList<>();
        genres = new ArrayList<>();
        dates.add("any year");
        for (int i = 1950; i <= 2010; i += 10)
        {
            dates.add(Integer.toString(i));
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
    
    public List<String> getDates()
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

    public void setMinReleaseYear(String minReleaseYear)
    {
        this.minReleaseYear = minReleaseYear;
    }

    public void setMaxReleaseYear(String maxReleaseYear)
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

    public String getMinReleaseYear()
    {
        return minReleaseYear;
    }

    public String getMaxReleaseYear()
    {
        return maxReleaseYear;
    }

    public int getRating()
    {
        return rating;
    }
    
    /**
     * Determines whether the current search has found any results yet.
     * @return true if results are found, false otherwise
     */
    public Boolean hasResults()
    {
        return !results.isEmpty();
    }
    
    /**
     * A dummy action method for UI prototyping.
     * @return "search" action.
     */
    public String dummySearch()
    {
        results.clear();
        List<String> contributors = new ArrayList<>();
        contributors.add("Billy Bob");
        contributors.add("Mike");
        contributors.add("Joe");
        results.add(new Movie("Dummy movie", "A dummy movie for testing purposes",
                    "Dummy genre", "April 14th, 2020", contributors, 5));
        return "search";
    }
    
    /**
     * A dummy action method for UI prototyping.
     * @return "search" action.
     */
    public String dummyRandomSearch()
    {
        results.clear();
        List<String> contributors = new ArrayList<>();
        contributors.add("Billy Bob");
        contributors.add("Mike");
        contributors.add("Joe");
        results.add(new Movie("Random movie", "Looks like someone was feeling lucky",
                    "Random genre", "April 14th, 2020", contributors, 5));
        return "search";
    }
    
    //Criteria given by the user
    String minReleaseYear;
    String maxReleaseYear;
    int rating;
    String genre;
    String keyword;
    
    //Options for drop down menus
    List<String> dates;
    List<Integer> ratings;
    List<String> genres;

    List<Movie> results;  //Results of a search
    
}
