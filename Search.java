
/**
 * class Search is the client class. 
 * 
 * @author (Andrew Hood) 
 * @version (0.1)
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
public class Search
{
    // instance variables
    private NetflixFileReader movieListReader;
    private ArrayList<Media> masterList;
    private ArrayList<Media> currentList;
    private ArrayList<Filter> filterList;
    private int exitStatus; //  0 - Run; 1 - Quit

    /**
     * Default constructor for class Search
     */
    public Search()
    {
        movieListReader = new NetflixFileReader();
        masterList = movieListReader.readDataFile();
        filterList = new ArrayList<Filter>();
        currentList = new ArrayList<Media>();
        populateCurrentList();
        setExitStatus(0);
        mainMenu();
    }

    public void setExitStatus(int status)
    {
        exitStatus = status;
    }

    public int getExitStatus()
    {
        return exitStatus;
    }

    public void printMasterList()
    {
        for(int i = 0; i < masterList.size(); i++)
        {
            System.out.println(masterList.get(i));
        }
    }

    public void mainMenu()
    {
        char menuChoice = ' ';
        Scanner input = new Scanner(System.in);
        System.out.print("initech:~ Peter$ ");
        try {
            menuChoice = input.nextLine().charAt(0);
        } catch(Exception ex) {
            System.out.println("\n\tItems loaded:\t" + masterList.size() +
                                "\n\tItems selected:\t" + currentList.size() + 
                                "\n\tOptions:" + 
                                "\n\t\tD\t: Display all selected items." +
                                "\n\t\tF\t: Display all filters." +
                                "\n\t\tA\t: Add a filter." +
                                "\n\t\tR\t: Remove a filter." +
                                "\n\t\tE\t: Exit.\n");
        }
        
        if(menuChoice == 'D' || menuChoice == 'd') {
            if(currentList.size() == 0) {
                System.out.println("\n\tThere are no results to display.\n");
            } else {
                System.out.println();
                displayList(currentList);
                System.out.println();
            }
        } else if(menuChoice == 'F' || menuChoice == 'f') {
            if(filterList.size() == 0) {
                System.out.println("\n\tThere are no filters to display.\n");
            } else {
                System.out.println();
                displayList(filterList);
                System.out.println();
            }
        } else if(menuChoice == 'A' || menuChoice == 'a') {
            if(currentList.size() == 0) {
                System.out.println("\n\tThere are already no results to display.\n");
            } else {
                filterList.add(new Filter());
                populateCurrentList();
            }
        } else if(menuChoice == 'R' || menuChoice == 'r') {
            if(filterList.size() == 0) {
                System.out.println("\n\tThere are no filters to remove.\n");
            } else {
                boolean removeFilterEnter = true;
                while(removeFilterEnter)
                {
                    System.out.print("initech:RemoveFilter Peter$ ");
                    String removeFilter = input.nextLine();
                    if(removeFilter.length() == 0)
                    {
                        System.out.println();
                        displayList(filterList);
                        System.out.println("\n\tEnter index to remove.\n");
                    } else {
                        int indexToRemove = 0;
                        try {
                            indexToRemove = Integer.parseInt(removeFilter);
                        } catch(NumberFormatException ex) {
                            System.out.println("\n\tYou must enter a integer.\n");
                        } 
                        if(indexToRemove > 0 && indexToRemove <= filterList.size())
                        {
                            Filter temp = filterList.remove(Integer.parseInt(removeFilter)-1);
                            removeFilterEnter = false;
                        }
                    }
                }
                
                
                populateCurrentList();
            }
        } else if(menuChoice == 'E' || menuChoice == 'e') {
            setExitStatus(1);
        } else if(menuChoice == 'G' || menuChoice == 'g' || menuChoice == 's' || menuChoice == 'S' || menuChoice == 'E' || menuChoice == 'e') { // Easter Egg
            Search.goof();
        }

    }

    public static void main(String[] args)
    {
        Search netflix = new Search();
        while(netflix.getExitStatus() == 0)
        {
            netflix.mainMenu();
        }
    }

    public static void goof()
    {
        System.out.println("Hello, Peter. What's happening? Uh…we have sort of a problem" + 
            "\nhere. Yeah. You apparently didn't pick one of the letters" +
            "\nfrom our new TPS menu above.\n\n");

        System.out.println("Mmmm...yeah. You see, we're picking letters from the TPS menu now" + 
            "\nbefore you press (Enter). Did you see the memo about this?\n\n");

        System.out.println("Yeah. If you could just go ahead and make sure you do that from" +
            "\nnow on, that will be great. And uh, I'll go ahead and make sure" +
            "\nyou get another copy of that memo Mmmkay? Bye bye, Peter.\n\n");
    }

    public void populateCurrentList()
    {
        currentList = new ArrayList<Media>(masterList.size());
        for(int i = 0; i < masterList.size(); i++)
        {
            if(includedByFilters(masterList.get(i))) {
                currentList.add(masterList.get(i));
            }
        }
    }

    public boolean includedByFilters(Media compare)
    {
        for(int i = 0; i < filterList.size(); i++)
        {

            // ALL GENRE CASES
            if(filterList.get(i).getField().equals("genre"))
            {
                if(filterList.get(i).getRelation().equals("="))
                {
                    if(filterList.get(i).getTarget().equals("movie"))
                    {
                        if(compare instanceof Series) {
                            return false;
                        }
                    }
                    if(filterList.get(i).getTarget().equals("series"))
                    {
                        if(compare instanceof Movie) {
                            return false;
                        }
                    }
                }
            }

            //ALL TITLE CASES
            if(filterList.get(i).getField().equals("title"))
            {
                if(filterList.get(i).getRelation().equals(">"))
                {
                    //Check if false
                    for(int j = 0; j < Math.min(compare.getTitle().length(),filterList.get(i).getTarget().length()); j++)
                    {
                        // Change this, right now it will exclude if any letter is larger than that of the filter
                        if(compare.getTitle().charAt(j) <= filterList.get(i).getTarget().charAt(j))
                        {
                            return false;
                        }
                    }
                }

                if(filterList.get(i).getRelation().equals("<"))
                {
                    //Check if false
                    for(int j = 0; j < Math.min(compare.getTitle().length(),filterList.get(i).getTarget().length()); j++)
                    {
                         // Change this, right now it will exclude if any letter is larger than that of the filter
                        if(compare.getTitle().charAt(j) >= filterList.get(i).getTarget().charAt(j))
                        {
                            return false;
                        }
                    }
                }

                if(filterList.get(i).getRelation().equals("="))
                {
                    //Check if false
                    if(!compare.getTitle().equals(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }

                if(filterList.get(i).getRelation().equals("contains"))
                {
                    if(!compare.getTitle().contains(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }
            }
            
            // ALL YEAR CASES
            if(filterList.get(i).getField().equals("year"))
            {
                if(filterList.get(i).getRelation().equals(">")) 
                {
                    if(compare.getYear() <= Integer.parseInt(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }
                
                if(filterList.get(i).getRelation().equals("=")) 
                {
                    if(compare.getYear() != Integer.parseInt(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }
                
                if(filterList.get(i).getRelation().equals("<")) 
                {
                    if(compare.getYear() >= Integer.parseInt(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }
            }
            
            //ALL RATING CASES
            if(filterList.get(i).getField().equals("rating"))
            {
                if(filterList.get(i).getRelation().equals(">"))
                {
                    if(compare.getRating() <= Double.parseDouble(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }
                
                if(filterList.get(i).getRelation().equals("="))
                {
                    if(compare.getRating() != Double.parseDouble(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }
                
                if(filterList.get(i).getRelation().equals("<"))
                {
                    if(compare.getRating() >= Double.parseDouble(filterList.get(i).getTarget()))
                    {
                        return false;
                    }
                }
            }
            
            //ALL LENGTH CASES
            if(filterList.get(i).getField().equals("length"))
            {
                if(compare instanceof Movie)
                {
                    Movie theMovie = (Movie) compare;
                    if(filterList.get(i).getRelation().equals(">"))
                    {
                        if(theMovie.getLength() <= Integer.parseInt(filterList.get(i).getTarget()))
                        {
                            return false;
                        }
                    }
                    
                    if(filterList.get(i).getRelation().equals("="))
                    {
                        if(theMovie.getLength() != Integer.parseInt(filterList.get(i).getTarget()))
                        {
                            return false;
                        }
                    }
                    
                    if(filterList.get(i).getRelation().equals("<"))
                    {
                        if(theMovie.getLength() >= Integer.parseInt(filterList.get(i).getTarget()))
                        {
                            return false;
                        }
                    }
                } else if(compare instanceof Series) {
                    return false;
                }
            }
          
        
        }
        
        return true;
    }

    public void displayList(ArrayList al)
    {
        for(int i = 0; i < al.size(); i++)
        {
            System.out.println("\t" + (i+1) + ". " + al.get(i));
        }
    }
}
