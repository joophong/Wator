package wator;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author Joopyo Hong
 * @version May 2, 2014
 */
public class Fish extends Pisces implements Callable<Void> {
    int movesTilRepro;
    
    /**
     * Constructor for Fish class
     */
    public Fish(Location loc) {
        super(loc);
        movesTilRepro = Parameters.fishRepro;
    
    }
    
    /**
     * toString() method for Fish class
     * 
     * @return String representation of this Fish
     */
    @Override
    public String toString() {
        return "Fish(" + loc.x + ", " + loc.y + ")";
        
    }
    
    
    /**
     * Move, if there is any empty location around this Fish, one location.
     * Gives birth to new Fish based on Fish's gestation period.
     * 
     */
    private void move() {
        
        Set<Location> destCandidates = getAdjacentLocations();
        for (Location dest: destCandidates) {
                
            if (shouldLockThisFirst(dest)) {
                synchronized (Wator.ocean[loc.x][loc.y]) {
                    if (Wator.ocean[loc.x][loc.y].getOccupant() instanceof Shark) {
                        die();
                        return;
                    }
                    synchronized(Wator.ocean[dest.x][dest.y]) {
                        if (Wator.ocean[dest.x][dest.y].getOccupant() != null)
                            continue;
                        
                        if (movesTilRepro <= 0) {
                            Fish child = new Fish(loc);
                            Wator.ocean[loc.x][loc.y].setOccupant(child);
                            Wator.piscesVector.add(child); // adds child fish to the pisces vector
                            movesTilRepro = 5;
                        } else
                            Wator.ocean[loc.x][loc.y].setOccupant(null);
                        
                        Wator.ocean[dest.x][dest.y].setOccupant(this);
                        this.loc = dest;
                        break;
                    }
                }
            
            } else {
                synchronized(Wator.ocean[dest.x][dest.y]) {
                    if (Wator.ocean[dest.x][dest.y].getOccupant() != null)
                        continue;
                    synchronized (Wator.ocean[loc.x][loc.y]) {
                        if (Wator.ocean[loc.x][loc.y].getOccupant() instanceof Shark) {
                            die();
                            return;
                        }
                        
                        if (movesTilRepro <= 0) {
                            Fish child = new Fish(loc);
                            Wator.ocean[loc.x][loc.y].setOccupant(child);
                            Wator.piscesVector.add(new Fish(loc)); // adds child fish to the pisces vector
                            movesTilRepro = 5;
                        } else
                            Wator.ocean[loc.x][loc.y].setOccupant(null);
                        
                        Wator.ocean[dest.x][dest.y].setOccupant(this);
                        this.loc = dest;
                        break;
                    }
                }
            }
        
        }
        
        movesTilRepro -= 1;
    }
    

    @Override
    public Void call() throws Exception {
        if (Wator.ocean[loc.x][loc.y].getOccupant() != this) {
            die();
            return null;
        }
        move();
        return null;
    }

}
