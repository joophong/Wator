package wator;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Joopyo Hong
 * @version May 2, 2014
 */
public abstract class Pisces {
    protected Location loc; // x,y
    
    
    /**
     * Constructor for Pisces class
     */
    public Pisces(Location loc) {
        this.loc = loc;
    }
    
    @Override
    public String toString() {
        return "Pisces(" + loc.x + ", " + loc.y + ")";
        
    }
    
    /**
     * Returns 4 adjacent locations of this Pisces.
     * 
     * @return List of 4 adjacent locations
     */
    Set<Location> getAdjacentLocations() {
        Set<Location> adjLoc = new HashSet<Location>();
        
        adjLoc.add(new Location(loc.x + 1, loc.y));
        adjLoc.add(new Location(loc.x, loc.y + 1));
        adjLoc.add(new Location(loc.x - 1, loc.y));
        adjLoc.add(new Location(loc.x, loc.y - 1));
        
        return adjLoc;
    }
    
    /**
     * Removes this Pisces from the Pisces vector.
     * 
     */
    void die() {
        Wator.piscesVector.remove(this);
    }
    
    /**
     * Decides which one to synchronize first, this Pisces' location or the its potential destination location, 
     * by comparing their x-y coordinates 
     * 
     * @param dest  Potential destination location of this Pisces
     * @return True if this Pisces' location must be synchronized first, false otherwise.
     */
    boolean shouldLockThisFirst(Location dest) {
        if (loc.x > dest.x)
            return true;
        else if (loc.x < dest.x)
            return false;
        else {
            if (loc.y > dest.y)
                return true;
            else if (loc.y < dest.y)
                return false;
            else {
                System.out.println("Two distinct Locations cannot share same location.");
                assert false;
                return false;
            }
        }
    }
    

}
