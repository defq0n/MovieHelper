/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.moviescraper;

/**
 *
 * @author defq0n
 */
public class Movie {
    private final String name;
    private String pageLink;
    private String year;
    private String description;
    private String[] actors;
    private String posterLink;
    private String genre;
    private String trailerLink;
    
    public Movie(String name){
        this.name = name;
    }
    
    public Movie(String name, String year, String description, String posterLink, String actors[], String genre, String trailerLink){
        this.name = name;
        this.year = year;
        this.description = description;
        this.posterLink = posterLink;
        this.actors = actors;
        this.genre = genre;
        this.trailerLink = trailerLink;
    }
    
    @Override
   public String toString(){
       String retur = name + ", " + year + ", " + description + ", " + posterLink + ", " + genre + ", " + trailerLink + ", ";
       for(int i = 0; i < actors.length; i++){
           if(i == 2){
               retur += actors[i];
           } else {
            retur += actors[i] + ", ";
           }
       }
       return retur;
   }
   
   public String getTrailerLink(){
       return this.trailerLink;
   }
   
   public void setTrailerLink(String trailerLink){
       this.trailerLink = trailerLink;
   }
    
    public String getMovieName(){
        return this.name;
    }
    
   public String getMovieYear(){
       return this.year;
   }
   
   public String getGenre(){
       return this.genre;
   }
   
   public void setGenre(String genre){
       this.genre = genre;
   }
   
   public void setMovieYear(String year){
       this.year = year;
   }
   
   public String getMovieDescription(){
       return this.description;
   }
   
   public void setMovieDescription(String description){
       this.description = description;
   }
   
   public String getPosterLink(){
       return this.posterLink;
   }
   
   public void setPosterLink(String posterLink){
       this.posterLink = posterLink;
   }
   
   public String[] getActors(){
       return this.actors;
   }
   
   public void setActors(String[] actors){
       this.actors = actors;
   }
   
   public String getPageLink(){
       return this.pageLink;
   }
   
   public void setPageLink(String pageLink){
       this.pageLink = pageLink;
   }
}

