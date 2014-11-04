
/**
 * An abstract Media class, with concrete Movie and Series subclasses.
 * These classes should be designed to hold all the relevant information 
 * about an item that can be obtained from the data file.
 * 
 * @author (Andrew Hood) 
 * @version (0.1)
 */
public class Series extends Media
{
    private String seriesData;
    
    public Series(String allData, String title, String year, String rating, String postComma)
    {
        super(allData, title, year, rating);
        setSeriesData(postComma);
    }
    
    private void setSeriesData(String postComma)
    {
        seriesData = postComma;
    }
    
    public String toString()
    {
        return getAllData();
    }
}
