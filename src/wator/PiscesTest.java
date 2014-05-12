
package wator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joopyo Hong
 *
 */
public class PiscesTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        DrawingArea canvas = null;
        new Wator(canvas);
    }


    /**
     * Test method for {@link wator.Pisces#getAdjacentLocations()}.
     */
    @Test
    public void testGetAdjacentLocations() {
        Pisces p1 = new Shark(new Location(0,0));
        Set<Location> p1Adj = p1.getAdjacentLocations();
        int assertCount = 0;
        
        for (Location l: p1Adj) {
            if (l.x == 499 && l.y == 0) assertCount += 1;
            if (l.x == 0 && l.y == 499) assertCount += 1;
            if (l.x == 1 && l.y == 0) assertCount += 1;
            if (l.x == 0 && l.y == 1) assertCount += 1;
        }
        
        assertEquals(4, assertCount);
        
        
        Pisces p2 = new Shark(new Location(499, 499));
        Set<Location> p2Adj = p2.getAdjacentLocations();
        assertCount = 0;
        
        for (Location l: p2Adj) {
            if (l.x == 498 && l.y == 499) assertCount += 1;
            if (l.x == 499 && l.y == 498) assertCount += 1;
            if (l.x == 499 && l.y == 0) assertCount += 1;
            if (l.x == 0 && l.y == 499) assertCount += 1;
        }
        
        assertEquals(4, assertCount);
        
        
        
    }

    /**
     * Test method for {@link wator.Pisces#die()}.
     */
    @Test
    public void testDie() {
        Pisces p1 = new Shark(new Location(0,0));
        Pisces p2 = new Shark(new Location(499, 499));
        
        Wator.piscesVector.add(p1);
        Wator.piscesVector.add(p2);
        
        assertTrue(Wator.piscesVector.size() == 2);
        
        p1.die();
        p2.die();
        
        assertTrue(Wator.piscesVector.isEmpty());
        
    }

    /**
     * Test method for {@link wator.Pisces#shouldLockThisFirst(wator.Location)}.
     */
    @Test
    public void testShouldLockThisFirst() {
        Pisces p1 = new Shark(new Location(0, 0));
        Pisces p2 = new Shark(new Location(1, 0));
        Pisces p3 = new Shark(new Location(1, 1));
        
        assertFalse(p1.shouldLockThisFirst(p2.loc));
        assertTrue(p2.shouldLockThisFirst(p1.loc));
        
        assertFalse(p2.shouldLockThisFirst(p3.loc));
        assertTrue(p3.shouldLockThisFirst(p2.loc));      
        
    }

}
