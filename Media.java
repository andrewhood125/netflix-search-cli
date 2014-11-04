
/**
 * An abstract Media class, with concrete Movie and Series subclasses. 
 * These classes should be designed to hold all the relevant information
 * about an item that can be obtained from the data file.
 * 
 * @author (Andrew Hood)
 * @version (0.1)
 */
public abstract class Media
{
    // instance variables
    private String allData;
    private String title;
    private int year;
    private double rating;
    
    public Media(String allData, String title, String year, String rating)
    {
        setAllData(allData);
        setTitle(title);
        setYear(year);
        setRating(rating);
    }
    
    private void setAllData(String allData)
    {
        this.allData = allData;
    }
    
    private void setTitle(String s)
    {
        title = s;
    }
    
    private void setYear(String s)
    {
        if(!s.isEmpty()) {
            year = Integer.parseInt(s);
        } else {
            year = 0;
        }
    }
    
    public void setRating(String s)
    {
        if(!s.isEmpty()) {
            rating = Double.parseDouble(s);
        } else {
            rating = 0;
        }
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public int getYear()
    {
        return year;
    }
    public double getRating()
    {
        return rating;
    }
    
    public String getAllData()
    {
        return allData;
    }
    
    abstract public String toString();
}
