
/**
 * An abstract Media class, with concrete Movie and Series subclasses.
 * These classes should be designed to hold all the relevant information 
 * about an item that can be obtained from the data file.
 * 
 * @author (Andrew Hood) 
 * @version (0.1)
 */
public class Movie extends Media
{

    private int length;
    
    public Movie(String allData, String title, String year, String rating, String postComma)
    {
        super(allData, title, year, rating);
        setLength(postComma);
    }
    
    public void setLength(String postComma)
    {
        if(postComma.matches("\\dhr")) 
        {
            String[] temp = postComma.split("hr");
            length = Integer.parseInt(temp[0])*60;
        } else if(postComma.matches("\\dhr\\s\\d{1,}m")){
            String[] temp = postComma.split("hr|m");
            length = Integer.parseInt((temp[0].trim()))*60 + Integer.parseInt((temp[1].trim()));
        } else if(postComma.matches("\\d{1,}m")) {
            String[] temp = postComma.split("m");
            length = Integer.parseInt(temp[0]);
        }
    }
    
    public int getLength()
    {
        return length;
    }
    
    public String toString()
    {
        return getAllData();
    }
   
}
