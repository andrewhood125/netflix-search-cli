
/**
 * Write a description of class Filter here.
 * 
 * @author (Andrew Hood) 
 * @version (0.1)
 */
import java.util.Scanner;

public class Filter
{
    private String field;
    private String relation;
    private String target;
    
    public Filter()
    {
        Scanner input = new Scanner(System.in);
        
                            
        boolean badFilter = true;
        String[] filter = new String[3];
        while(badFilter)
        {
            System.out.print("initech:AddFilter Peter$ ");
            String temp = input.nextLine();
            if(temp.length() == 0) 
            {
                System.out.println("\n\tAdd Filter {field relation target}" +
                            "\n\tfields:\t\tgenre, title, year, rating, length" +
                            "\n\trelations:\t>, =, <, contains" + 
                            "\n\ttargets:\tmovie, series, 2011, 90" + 
                            "\n\tExample:\tgenre = movie\n");
            } 
            filter = temp.split("\\s", 3);
            if(filter.length == 3) // There are 2 spaces. 
            {
                    // ALL GENRE CASES
                    if(filter[0].equals("genre")) {
                        if(filter[1].equals("=")) {
                            if(filter[2].equals("movie") || filter[2].equals("series")) {
                                badFilter = false; // Good filter!
                            } else {
                                System.out.println("\n\tOnly the targets movie and series are tangible with field genre.\n");
                            }
                        } else {
                            System.out.println("\n\tOnly the relation = is tanginble with field genre.\n");
                        }  
                    }
                    
                    //ALL TITLE CASES
                    if(filter[0].equals("title")) {
                        if(filter[1].equals(">") || filter[1].equals("=") || filter[1].equals("<") || filter[1].equals("contains")) {
                            badFilter = false; // target can be any string in the case of field being title.
                        } else {
                            System.out.println("Hmm.. There was a problem. Did you choose one of the provided relations?");
                        }
                    }
                    
                    //ALL YEAR CASES
                    if(filter[0].equals("year")) {
                        if(filter[1].equals(">") || filter[1].equals("=") || filter[1].equals("<")){
                            try {
                                Integer.parseInt(filter[2]);
                                badFilter = false; // target can be any integer in the case of field being year.
                            } catch(Exception ex) {
                                System.err.println("That's not an integer.");
                            }
                        } else {
                            System.out.println("Hmm.. There was a problem. field year can accept >,=,<");
                        }
                    }
                    
                    //ALL RATING CASES
                    if(filter[0].equals("rating")) {
                        if(filter[1].equals(">") || filter[1].equals("=") || filter[1].equals("<")){
                            try {
                                Double.parseDouble(filter[2]);
                                badFilter = false; // target can be any integer in the case of field being year.
                            } catch(Exception ex) {
                                System.err.println("That's not a double.");
                            }
                        } else {
                            System.out.println("Hmm.. There was a problem. field rating can accept >,=,<");
                        }
                    }
                    
                    //ALL RATING CASES
                    if(filter[0].equals("length")) {
                        if(filter[1].equals(">") || filter[1].equals("=") || filter[1].equals("<")){
                            try {
                                Integer.parseInt(filter[2]);
                                badFilter = false; // target can be any integer in the case of field being year.
                            } catch(Exception ex) {
                                System.out.println("Only integers, enter 90 for 1hr 30m");
                            }
                        } else {
                            System.out.println("Hmm.. There was a problem. field length can accept >,=,<");
                        }
                    }      
            } else if(temp.length() > 0) {
                // Badly formed filter - Display info.
                System.out.println("\n\tBadly formed filter...");
                System.out.println("\n\tAdd Filter {field relation target}" +
                            "\n\tfields:\t\tgenre, title, year, rating, length" +
                            "\n\trelations:\t>, =, <, contains" + 
                            "\n\ttargets:\tmovie, series, 2011, 90" + 
                            "\n\tExample:\tgenre = movie\n");
            }
        }
        
        field = filter[0];
        relation = filter[1];
        target = filter[2];
        
        //Display filter menu
        // get filter input
        
        
    }
    
    public String getField()
    {
        return field;
    }
    
    public String getTarget()
    {
        return target;
    }
    public String getRelation()
    {
        return relation;
    }
    public String toString()
    {
        return field + " " + relation + " " + target;
    }
}
