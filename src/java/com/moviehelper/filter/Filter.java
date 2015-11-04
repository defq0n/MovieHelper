/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.filter;

import static com.moviehelper.moviescraper.MovieScraper.getMovieInformation;
import static com.moviehelper.moviescraper.MovieScraper.parseMoviesHTML;

/**
 *
 * @author Stephen
 */
public class Filter {
    public void filterOnGenre(String movieName) {
        String moviesHTML[] = parseMoviesHTML(movieName);
        String[][] result = getMovieInformation(moviesHTML);
        
        String firstHit = result[1][0];
        String genre = result[3][0];
    }
}
