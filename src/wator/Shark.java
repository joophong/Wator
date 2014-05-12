package wator;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author Joopyo Hong
 * @version May 2, 2014
 */
public class Shark extends Pisces implements Callable<Void> {
    int movesTilRepro;
    int energyRemains;

    /**
     * Constructor for Shark class
     */
    public Shark(Location loc) {
        super(loc);
        movesTilRepro = Parameters.sharkRepro;
        energyRemains = Parameters.sharkEnergy;
    }
    
    /**
     * toString() method for Fish class
     * 
     * @return String representation of this Fish
     */
    @Override
    public String toString() {
        return "Shark(" + loc.x + ", " + loc.y + ")";
        
    }
    
    /**
     * If there is any Fish location around this Shark, moves there and eats the Fish.
     * If there is no Fish location but empty location, move there.
     * Gives birth to new Shark based on Fish's gestation period.
     * Dies and remove this Shark from the Pesces vector if this Shark's energy tank hits 0.
     * 
     */
    private void move() {
        
        if (energyRemains == 0) {
            die();
            Wator.ocean[loc.x][loc.y].setOccupant(null);
            return;
        }
        
        Set<Location> destCandidates = getAdjacentLocations();
        for (int i = 0; i < 2; i++) {
            for (Location dest: destCandidates) {
                
                if (shouldLockThisFirst(dest)) {
                    synchronized (Wator.ocean[loc.x][loc.y]) {
                        synchronized(Wator.ocean[dest.x][dest.y]) {
                            Pisces destOccupant = Wator.ocean[dest.x][dest.y].getOccupant();
                            
                            if (i == 0) {
                                // in the 1st run, dismiss unless the destination is fish
                                if (!(destOccupant instanceof Fish))
                                    continue;
                            } else {
                                // in the 2nd run, dismiss only when destination is shark, i.e. allow either fish or empty(null)
                                if (destOccupant instanceof Shark)
                                    continue;
                            }
                            
                            if (destOccupant instanceof Fish)
                                energyRemains += Parameters.sharkIncrement;
                            
                            if (movesTilRepro <= 0) {
                                Shark child = new Shark(loc);
                                Wator.ocean[loc.x][loc.y].setOccupant(child);
                                Wator.piscesVector.add(child); // adds child fish to the pisces vector
                                movesTilRepro = 5;
                            } else
                                Wator.ocean[loc.x][loc.y].setOccupant(null);
                            
                            Wator.ocean[dest.x][dest.y].setOccupant(this);
                            this.loc = dest;
                            movesTilRepro -= 1;
                            energyRemains -= 1;
                            return;
                        }
                    }
                
                } else {
                    synchronized(Wator.ocean[dest.x][dest.y]) {
                        if (!(Wator.ocean[dest.x][dest.y].getOccupant() instanceof Fish))
                            continue;
                        synchronized (Wator.ocean[loc.x][loc.y]) {
                            Pisces destOccupant = Wator.ocean[dest.x][dest.y].getOccupant();
                                     
                            if (i == 0) {
                                if (!(destOccupant instanceof Fish))
                                    continue;
                            } else {
                                if (destOccupant instanceof Shark)
                                    continue;
                            }
                            
                            if (destOccupant instanceof Fish)
                                energyRemains += Parameters.sharkIncrement;
                            
                            if (movesTilRepro <= 0) {
                                Shark child = new Shark(loc);
                                Wator.ocean[loc.x][loc.y].setOccupant(child);
                                Wator.piscesVector.add(child); // adds child fish to the pisces vector
                                movesTilRepro = 5;
                            } else
                                Wator.ocean[loc.x][loc.y].setOccupant(null);
                            
                            Wator.ocean[dest.x][dest.y].setOccupant(this);
                            this.loc = dest;
                            movesTilRepro -= 1;
                            energyRemains -= 1;
                            return;
                        }
                    }
                }
            
            }
        }
        
        movesTilRepro -= 1;
        energyRemains -= 1;
    }


    @Override
    public Void call() throws Exception {
        move();
        return null;
    }

}
