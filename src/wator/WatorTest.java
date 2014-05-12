
package wator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Joopyo Hong
 *
 */
public class WatorTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        DrawingArea canvas = null;
        new Wator(canvas);
    }

    /**
     * Test method for {@link wator.Wator#Wator(wator.DrawingArea)}.
     */
    @Test
    public void testWator() {
        assertEquals(500, Wator.ocean.length);
        assertEquals(500, Wator.ocean[0].length);
    }

    /**
     * Test method for {@link wator.Wator#initializeOcean()}.
     */
    @Test
    public void testInitializeOcean() {
        Wator.initializeOcean();
        for (int i = 0; i < Wator.ocean[0].length; i++)
            assertEquals(i, Wator.ocean[0][i].y);
        for (int i = 0; i < Wator.ocean.length; i++)
            assertEquals(i, Wator.ocean[i][0].x);
        
    }

}
