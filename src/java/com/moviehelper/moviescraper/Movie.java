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
    
    public Movie(String name){
        this.name = name;
    }
    
    public Movie(String name, String year, String description, String posterLink, String actors[]){
        this.name = name;
        this.year = year;
        this.description = description;
        this.posterLink = posterLink;
        this.actors = actors;
    }
    
    public String getMovieName(){
        return this.name;
    }
    
   public String getMovieYear(){
       return this.year;
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

