package wator;

/**
 * @author Joopyo Hong
 * @version May 2, 2014
 */
public class Location {

    final int x;
    final int y;
    Pisces occupant;

    /**
     * Constructor for Location class
     */
    public Location(int x, int y) {
        
        if (x == -1)
            x = Wator.ocean.length - 1;
        else if (y == -1)
            y = Wator.ocean[0].length - 1;
        else if (x == Wator.ocean.length)
            x = 0;
        else if (y == Wator.ocean[0].length)
            y = 0;
        
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "Location(" + x + ", " + y + ": " + occupant + ")";
        
    }
    
    /**
     * Gets Pisces in this Location.
     * 
     * @return Reference to pisces that occupies this location
     */
    public Pisces getOccupant() {
        return occupant;
    }
    
    /**
     * Sets Pisces in this Location.
     * 
     * @param pisces   Pisces to set the occupant field to point to
     */
    public void setOccupant(Pisces pisces) {
        occupant = pisces;
    }
}
