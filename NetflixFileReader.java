
/**
 * A NetflixFileReader class that handles reading the provided data file. 
 * For each line of the file, a new Media object (either Movie or Series) 
 * should be created and added to a list of Media objects. This class 
 * should include a method that returns the finished list.
 * 
 * @author (Andrew Hood) 
 * @version (0.1)
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;

public class NetflixFileReader
{
    // instance variables
    Scanner movieList;
    ArrayList<Media> masterDataList;
    /**
     * Constructor for objects of class NetflixFileReader
     */
    public NetflixFileReader()
    {
        // Find movieList.txt
        verifyDataFile();
    }

    /**
     * verifyDataFile() - Check if movieList.txt exsists
     * throw a FileNotFoundException if the file can
     * not be found. Exit the program and notify the user.
     */
    private void verifyDataFile()
    {
        try {
            movieList = new Scanner(new File("movieList.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("movieList.txt was not found. Exiting.");
            System.exit(1);
        }
    }
    
    /**
     * isSeries() - parse a string of data and determine 
     * if it is a series (true) or movie (false) private 
     * as only to be called from within class NetflixFileReader
     * 
     * @params String currentLine being parsed
     * @return boolean 
     */
    private boolean isSeries(String postComma)
    {
        if(postComma.matches("\\dhr\\s\\d{1,}m|\\dhr|\\d{1,}m")) {
            return false;
        } else {
            return true;
        }
            
    }
    
    public ArrayList<Media> readDataFile()
    {
        masterDataList = new ArrayList<Media>();
        while(movieList.hasNext()) {
            // Parse this line of data. 
            String currentLine = movieList.nextLine();
            // There is only 1 pipe on each line so 
            // lets split the data and try to break it down
            // into what we need. 
            
            
            
            /**
             * Move this into the respective movie/series classes. 
             * Only have enough to determine isSeries here. 
             */
            String prePipe  = new String(),
                   postPipe = new String(),
                   title    = new String(),
                   year     = new String(),
                   rating   = new String(),
                   postComma= new String();
                   
            // Split the string by the pipe if it has a pipe
            if(currentLine.contains("|"))
            {
                String[] pipe = currentLine.split("\\|");
                try {
                    prePipe = pipe[0];
                    postPipe = pipe[1];
                } catch(Exception ex) {
                     System.err.println(currentLine + ": Incorrectly formatted, pipe?");
                }
            // movieList incorrectly formatted, can't continue with this format error. EXIT
            } else {
                 System.err.println(currentLine + ": Incorrectly formatted, pipe?");
            }
            
            /**
             * Pre Pipe splitting
             */
            // title
            String[] parensWithNumbers = prePipe.split("\\(\\d{4}\\)|\\(\\d{4}-\\d{4}\\)");
            title = parensWithNumbers[0];
            
            //year 
            StringTokenizer getYear = new StringTokenizer(prePipe);
            while(getYear.hasMoreTokens()) 
            {
                String temp;
                if((temp = getYear.nextToken()).matches("\\(\\d{4}\\)|\\(\\d{4}-\\d{4}\\)"))
                {
                    String[] years = temp.split("\\(|-|\\)");
                    year = years[1];
                }
            }
            
            /**
             * Post Pipe slitting
             */
            // Split postPipe by "stars," if it contains "stars,"
            if(postPipe.contains("stars,")) 
            {
                String[] starsComma = postPipe.split("stars,");
                try {
                    rating = starsComma[0];
                    postComma = starsComma[1];
                } catch(Exception ex) {
                     System.out.println("Missing Data... using default:\t" + currentLine);
                }
            // If the rating is missing still get post comma content
            } else if(postPipe.contains(",")) {
                String[] starsComma = postPipe.split(",");
                try {
                    postComma = starsComma[1];
                } catch(Exception ex) {
                     System.out.println("Missing Data... using default:\t" + currentLine);
                }
            }
            
           title    = title.trim();
           year     = year.trim();
           rating   = rating.trim();
           postComma= postComma.trim();
            
            if(isSeries(postComma)) {
               masterDataList.add(new Series(currentLine, title, year, rating, postComma));
            //for this project everything that is not a series is a movie
            } else {
                masterDataList.add(new Movie(currentLine, title, year, rating, postComma));
            }
        }
        System.out.println();
        return masterDataList;
    }
}
