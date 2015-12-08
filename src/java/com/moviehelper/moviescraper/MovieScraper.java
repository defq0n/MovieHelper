/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.moviescraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author defq0n
 */
public class MovieScraper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //testFormatString();
        //testParseMoviesHTML();
        testGetMovieInformation();
        //System.out.println(getPosterLink("/title/tt0241527/"));
    }
    
    /**
     * testFormatString() tests formatString(String).
     * @author defq0n
     */
    private static void testFormatString(){
        System.out.println("Type in a string to format");
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");   
        System.out.println(formatString(sc.next()));
    }
    
    /**
     * formatString(String) asks the user for a string
     *      and returns the string without spaces for URL use. Currently only changes spaces,
     *      will have to be updated to handle any other special character assuming its not valid.
     * @author defq0n
     * @param str String to be formatted
     * @return Formatted string of str
     */
    public static String formatString(String str){
        char charArray[] = new char[str.length()];
        for(int i = 0; i < charArray.length; i++){
            char s = str.charAt(i);
            if(s == ' '){
                charArray[i] = '+';
            } else {
                charArray[i] = s;
            }
        }
        return String.valueOf(charArray);
    }
    
    /**
     * testParseMovies() tests parseMoviesHTML(String).
     * @author defq0n
     */
    private static void testParseMoviesHTML(){
        System.out.println("Type in a movie you want to search for and get the movies HTML results: ");
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        String moviesHTML[] = parseMoviesHTML(sc.next());
        int i = 1;
        for (String moviesHTML1 : moviesHTML) {
                System.out.println(i + ": " +moviesHTML1);
                i++;
        }
    }
    
    /**
     * parseMoviesHTMl(String) takes a formatted title query and
     *      returns a string array of movie HTML source code. 
     *      Maximum of 10 movies. 
     *      //TODO Doesn't always work but works for most cases, HTML tends to change
     *      for some results, will be changed when the reason is found out.
     * @author defq0n
     * @param titleQuery Formatted title query.
     * @return moviesHTMl String array of each HTML source.
     */
    public static String[] parseMoviesHTML(String titleQuery){
        String moviesHTML[] = {"","","","","","","","","",""}; //List of movies in HTML, limied to 10, initalized for easy string addition
        try{
            //Get the document using JSoup
            Document d = Jsoup.connect("http://www.imdb.com/find?ref_=nv_sr_fn&q="+ titleQuery +"&s=all").get();
            //Get the HTML body element
            Element e = d.body();
            //Declare Variables
            String xhtml = e.toString(); // HTML of body element
            String tbody = ""; //HTML of tbody element        
            //get tbody html and store in #tbody
            for(int i = xhtml.indexOf("<tbody>"); i < xhtml.indexOf("</tbody>"); i++){
                tbody += xhtml.charAt(i);
            }
            //loop over tbody to find how many movies there are and store the results in #moviesHTML
            int counter = 0; // counter for while loop
            while(counter < 10){ //hard code 10 because thats the maximum amount in #moviesHTML
                for(int i = tbody.indexOf("<td class=\"result_text\">")+24; i < tbody.indexOf(") </td>")+7; i++){
                    moviesHTML[counter] += tbody.charAt(i);
                }
                //now we have to reset tbody for the next 
                String temp_tbody = "";
                for(int i = tbody.indexOf(") </td>")+7; i < tbody.length(); i++){
                    temp_tbody += tbody.charAt(i);
                }
                //set tbody to the temporary one to get rid of the previous result
                tbody = temp_tbody;
                //index counter for next movie
                counter++;
            }
            } catch(Exception e){
                System.out.println(e.getStackTrace()[1]);
        }
        return moviesHTML;
    }
    
    /**
     * testGetMovieInformation() tests getMovieInformation().
     * @author defq0n
     */
    private static void testGetMovieListInformation(){
        System.out.println("Type in a movie you want to search for and get the moives html results: ");
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        String moviesHTML[] = parseMoviesHTML(sc.next());
        String[][] result = getMovieListInformation(moviesHTML);
        System.out.println("URL\t\t\t" + "Title\t\t" + "Year\t" + "Category");
        for(int i = 0; i < result[0].length; i++){ 
            if(!result[0][i].equals("") && !result[1][i].equals("") && !result[2][i].equals("")){
            System.out.print(result[0][i] + "\t");
            System.out.print(result[1][i] + "\t");
            System.out.print(result[2][i] + "\t");
            System.out.print(result[3][i] + "\n");
            }
        }
    }
    
    /**
     *  getMovieListInformation gets all the movies and its presented information and 
     *      sets it into a multidimensional array. This assumes that the HTML from
     *      parseMoviesHTMl(String) was obtained correctly. 
     *  @author defq0n
     *  @param moviesHTML Movie HTML source code array from parseMoviesHTMl(String).
     *  @return result Multidimensional array containing {url, title, year, category}
     */
    public static String[][] getMovieListInformation(String[] moviesHTML){
        //declare variables, all of these have to be initialized for simple string addition
        String[][] result = {{"","","","","","","","","",""}, 
                            {"","","","","","","","","",""}, 
                            {"","","","","","","","","",""},
                            {"","","","","","","","","",""}}; //initalize four different arrays for url, title, and year, genre
        int counter = 0;
        while(counter < 10){
            //start by getting the year of the movies
            for(int i = moviesHTML[counter].indexOf("a href=\"")+8; i < moviesHTML[counter].indexOf("?"); i++){
                result[0][counter] += moviesHTML[counter].charAt(i);               
            }
            //have to get the offset for dynamic results
            int offset = (moviesHTML[counter].indexOf("\">")-moviesHTML[counter].indexOf("a href=\""))+ 2;          
            //next get the name of the movie
            for(int i = moviesHTML[counter].indexOf("a href=\"")+offset; i < moviesHTML[counter].indexOf("</a>"); i++){
                result[1][counter] += moviesHTML[counter].charAt(i);
            }
            //next get the year of the movie
            for(int i = moviesHTML[counter].indexOf("</a> (")+6; i < moviesHTML[counter].indexOf(")"); i++){
                result[2][counter] += moviesHTML[counter].charAt(i);
            }
            
            //next get the category
            int secondPar = moviesHTML[counter].indexOf(")"); //get the index of the first parenthesis, so we can get the index of the next one. this assumes the returned string is (YEAR) (CATEGORY)
            for(int i = moviesHTML[counter].indexOf(") (")+3; i < moviesHTML[counter].indexOf(")", secondPar + 1); i++){
                //If nothing is returned, that meanst the entry is a movie *** also there is a category called "TV Movie"
                if(moviesHTML[counter].indexOf(")", secondPar + 1) - moviesHTML[counter].indexOf(") (") > 10){ //if the distance of the next parenthesis is > than the previous by 10...
                    break; //this check assures that the movie doesn't go by another name, which would mess with parsing the category
                }
                result[3][counter] += moviesHTML[counter].charAt(i);
            }
            //This checks to make sure that there is a movie in the selected index, and replaces "" with "Movie"
            if(!result[0][counter].equals("") && result[3][counter].equals("")){
                result[3][counter] = "Movie";
            }
            //add one to the counter to move onto the next movie
            counter++;           
        }
        return result;
    }
    
    /**
    * getMovieInformation gets the movie description, the poster link, and the actors
    * from the list of movies as the parameter. They are returned in an ArrayList<Movie>
    * as movie objects, which you will be able to get all the movies information.
    * @author defq0n
    * @param movieList is a multidimensional array from getMovieListInformation.
    * @return movieArray a ArrayList containing Movie objects.
    */
    public static ArrayList<Movie> getMovieInformation(String[][] movieList){
        ArrayList<Movie> movieArray = new ArrayList<>();
        for(int i = 0; i < movieList[0].length; i++){
            if(!movieList[3][i].equals("")){
                if(movieList[3][i].toLowerCase().contains("movie")){
                    Movie tempMovie = new Movie(movieList[1][i]);
                    String pageLink = movieList[0][i];
                    tempMovie.setPageLink(pageLink);
                    tempMovie.setMovieYear(movieList[2][i]);
                    tempMovie.setMovieDescription(getMovieDescription(pageLink));
                    tempMovie.setPosterLink(getPosterLink(pageLink));
                    tempMovie.setActors(getMovieActors(pageLink));
                    tempMovie.setGenre(getGenre(pageLink));
                    movieArray.add(tempMovie);
                }
            }
        }
        return movieArray;
    }
    
    private static void testGetMovieInformation(){
        String[][] m = getMovieListInformation(parseMoviesHTML(formatString("harry potter")));
        ArrayList<Movie> ar = getMovieInformation(m);
        for(Movie t : ar){
            System.out.println(t.toString());
        }
    }
    
    /**
    * getMovieDescription parses through the movie's page html and returns the description.
    * @author defq0n
    * @param pageLink is the extended imdb url for the movie page.
    * @return movieDescription String containing the description
    */
    public static String getMovieDescription(String pageLink){
        String movieDescription = "";
        try {
            Document d = Jsoup.connect("http://imdb.com" + pageLink).get();
            Element e = d.body();
            String html = e.toString();
            String descriptionDiv = "";
            for(int i = html.indexOf("description\">")+13; i < html.indexOf("<div class=\"txt-block\" itemprop=\"director\""); i++){
                descriptionDiv += html.charAt(i);
            }
            for(int i = 0; i < descriptionDiv.indexOf("</p>"); i++){
                movieDescription += descriptionDiv.charAt(i);
            }
        } catch(Exception e){
            System.out.println(e.toString());
        }
        return movieDescription;
    }
    
    /**
    * getMovieDescription parses through the movie's page html and returns the poster url link.
    * @author defq0n
    * @param pageLink is the extended imdb url for the movie page.
    * @return posterLink String containing the poster url link.
    */
    public static String getPosterLink(String pageLink){
        String posterLink = "";
        try {
            Document d = Jsoup.connect("http://imdb.com" + pageLink).get();
            Element e = d.body();
            String html = e.toString();
            String posterDiv = "";
            for(int i = html.indexOf("class=\"image\">")+14; i < html.indexOf("<div class=\"pro-title-link text-center\">"); i++){
                posterDiv += html.charAt(i);
            }
            for(int i = posterDiv.indexOf("src=\"")+5; i < posterDiv.indexOf(".jpg\"")+4; i++){
                posterLink += posterDiv.charAt(i);
            }
        } catch(Exception e){
            System.out.println(e.toString());
        }
        return posterLink;
    }
    
    public static String getGenre(String pageLink){
    String genre = "";
    try {
        Document d = Jsoup.connect("http://imdb.com" + pageLink).get();
        Element e = d.body();
        String html = e.toString();
        String genreDiv = "";
        for(int i = html.indexOf("itemprop=\"genre\""); i < html.indexOf("itemprop=\"description\""); i++){
            genreDiv += html.charAt(i);
        }
        for(int i = genreDiv.indexOf("itemprop=\"genre\"")+17; i < genreDiv.indexOf("</span>"); i++){
            genre += genreDiv.charAt(i);
        }
    } catch(Exception e){
        System.out.println(e.toString());
    }
    return genre;
}
    
    /**
    * getMovieActors parses through the movie's page html and returns three actors.
    * @author defq0n
    * @param pageLink is the extended imdb url for the movie page.
    * @return movieActors String containing three actors.
    */
    public static String[] getMovieActors(String pageLink){
        String[] movieActors = {"", "", ""};
        try {
            Document d = Jsoup.connect("http://imdb.com" + pageLink).get();
            Element e = d.body();
            String html = e.toString();
            String actorsDiv = "";
            for(int i = html.indexOf("<h4 class=\"inline\">Stars:</h4>")+30; i < html.indexOf("See full cast and crew"); i++){
                actorsDiv += html.charAt(i);
            }
            String tempDiv = actorsDiv;
            for(int i = 0; i < 3; i++){ //we will get the first three top actors
                String actor = "";
                String t = "itemprop=\"url\"><span class=\"itemprop\" itemprop=\"name\">";
                for(int j = tempDiv.indexOf(t)+t.length(); j < tempDiv.indexOf("</span></a>"); j++){
                    actor += tempDiv.charAt(j);
                }
                movieActors[i] = actor;
                tempDiv = "";
                for(int j = actorsDiv.indexOf(actor + "</span>")+actor.length()+7; j < actorsDiv.length(); j++){
                    tempDiv += actorsDiv.charAt(j);
                }
            }
        } catch(Exception e){
            System.out.println(e.toString());
        }
        return movieActors;
    }
}

