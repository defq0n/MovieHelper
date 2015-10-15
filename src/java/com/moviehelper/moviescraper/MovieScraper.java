/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moviehelper.moviescraper;

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
        //testGetSearchHTML();
        //testParseMovies();
        testGetMovieInformation();
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
    private static void testParseMovies(){
        System.out.println("Type in a movie you want to search for and get the movies HTML results: ");
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        String moviesHTML[] = parseMoviesHTML(sc.next());
        for (String moviesHTML1 : moviesHTML) {
                System.out.println(moviesHTML1);
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
                for(int i = tbody.indexOf("<td class=\"result_text\">"); i < tbody.indexOf(") </td>")+7; i++){
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
    private static void testGetMovieInformation(){
        System.out.println("Type in a movie you want to search for and get the moives html results: ");
        Scanner sc = new Scanner(System.in).useDelimiter("\\n");
        String moviesHTML[] = parseMoviesHTML(sc.next());
        String[][] result = getMovieInformation(moviesHTML);
        System.out.println("URL\t\t\t" + "Title\t\t" + "Year");
        for(int i = 0; i < 10; i++){ //hard coded LOL because im lazy (10 lines)
            System.out.print(result[0][i] + "\t");
            System.out.print(result[1][i] + "\t");
            System.out.print(result[2][i] + "\n");
        }
    }
    
    /**
     *  getMovieInformation gets all the movies and its presented information and 
     *      sets it into a multidimensional array. This assumes that the HTML from
     *      parseMoviesHTMl(String) was obtained correctly. 
     *  @author defq0n
     *  @param moviesHTML Movie HTML source code array from parseMoviesHTMl(String).
     *  @return result Multidimensional array containing {url[], title[], year[]}
     */
    public static String[][] getMovieInformation(String[] moviesHTML){
        //declare variables, all of these have to be initialized for simple string addition
        String[][] result = {{"","","","","","","","","",""}, 
                            {"","","","","","","","","",""}, 
                            {"","","","","","","","","",""}}; //initalize three different arrays for url, title, and year
        int counter = 0;
        while(counter < 10){
            //start by getting the year of the movies
            for(int i = moviesHTML[counter].indexOf("a href=\"")+8; i < moviesHTML[counter].indexOf("?"); i++){
                result[0][counter] += moviesHTML[counter].charAt(i);               
            }
            //next get the name of the movie
            boolean tenth = true; //since this is hardcoded, we have to check if its the 10th movie too add one more character - hopefully dynamic in future
            for(int i = moviesHTML[counter].indexOf((counter+1) + "\"")+3; i < moviesHTML[counter].indexOf("</a>"); i++){
                if(counter == 9 && tenth){
                    i++;
                    tenth = false;
                }
                result[1][counter] += moviesHTML[counter].charAt(i);
            }
            //next get the year of the movie
            for(int i = moviesHTML[counter].indexOf("</a> (")+6; i < moviesHTML[counter].indexOf(")"); i++){
                result[2][counter] += moviesHTML[counter].charAt(i);
            }
            //add one to the counter to move onto the next movie
            counter++;           
        }
        return result;
    }
}

