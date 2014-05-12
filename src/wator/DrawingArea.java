package wator;

import java.awt.*;
import java.util.*;

import javax.swing.JPanel;

/**
 * Executes (and remembers) all the various drawing commands
 * that have been issued.
 * 
 * @author Joopyo Hong
 * @version May 2, 2014
 */
public class DrawingArea extends JPanel {
    private static final long serialVersionUID = 1L;
    boolean clearMode = false;

    /**
     * Fills the canvas directly based on ocean field,
     * which is a static two dimensional array in Wator class. 
     * 
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    @Override
    public synchronized void paint(Graphics g) {
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (Wator.ocean != null) {
            for (int i = 0; i < Wator.ocean.length; i++) {
                for (Location loc: Wator.ocean[i]) {
                    if (loc.occupant instanceof Fish) {
                        g.setColor(Color.GREEN);
                        g.fillRect(loc.x, loc.y, 1, 1);
                    } else if (loc.occupant instanceof Shark) {
                        g.setColor(Color.BLUE);
                        g.fillRect(loc.x, loc.y, 1, 1);
                    }
                }
            }
        }
        
    }
    
    /**
     * Clears the canvas.
     */
    public synchronized void clear() {
        Wator.initializeOcean();
        repaint();
    }
    
    

}
