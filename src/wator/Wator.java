package wator;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

/**
 * @author Joopyo Hong
 * @version May 2, 2014
 */
public class Wator extends JFrame {
    static Location[][] ocean;
    static Vector<Pisces> piscesVector;
    ExecutorService pool = Executors.newFixedThreadPool(10);
    private DrawingArea canvas;
    boolean running = true;
    boolean paused = false;
    boolean shouldTakeBreak = true;
    int count = 0;

    
    /**
     * Creates a Wator.
     * 
     * @param canvas The area on which to do the drawing.
     */
    public Wator(DrawingArea canvas) {
        this.canvas = canvas;
        piscesVector = new Vector<Pisces>();
        Wator.ocean = new Location[500][500];
    }
    
    /**
     * Runs the Wator program.
     * 
     */
    public void run() {
        setup();
         
        while (running) {
            while (paused)
                canvas.repaint();
            try {
                if (piscesVector.isEmpty()) break;
                Vector<Pisces> piscesVectorCopy = (Vector<Pisces>) Wator.piscesVector.clone();
                pool.invokeAll((Collection<? extends Callable<Void>>) piscesVectorCopy);
                if (shouldTakeBreak)
                    Thread.sleep(Parameters.speed);
            } catch (InterruptedException e) {
            }
            canvas.repaint();
        }
        pool.shutdown();        
    }
    
    /**
     * Populates ocean with initial batch of fish and sharks.  
     */
    private void setup() {
        initializeOcean();      
        piscesVector.clear();
        canvas.setBackground(Color.DARK_GRAY);
        
        List<Location> locsForCrossOut = new ArrayList<Location>();
        for (int i = 0; i < Wator.ocean.length; i++)
            locsForCrossOut.addAll(Arrays.asList(Wator.ocean[i]));
        
        Random r = new Random();
        
        for (int i = 0; i < Parameters.fishPop; i++) {
            int randomIndex = r.nextInt(locsForCrossOut.size());
            Location randomLoc = locsForCrossOut.get(randomIndex);
            Pisces newFish = new Fish(randomLoc);
            piscesVector.add(newFish);
            Wator.ocean[randomLoc.x][randomLoc.y].setOccupant(newFish);
            locsForCrossOut.remove(randomIndex);
        }
        for (int i = 0; i < Parameters.sharkPop; i++) {
            int randomIndex = r.nextInt(locsForCrossOut.size());
            Location randomLoc = locsForCrossOut.get(randomIndex);
            Pisces newShark = new Shark(randomLoc);
            piscesVector.add(newShark);
            Wator.ocean[randomLoc.x][randomLoc.y].setOccupant(newShark);
            locsForCrossOut.remove(randomIndex);
        }

    }
    
    /**
     * Initializes each location in ocean.  
     */
    static void initializeOcean() {
        for (int i = 0; i < Wator.ocean.length; i++) {
            for (int j = 0; j < Wator.ocean[0].length; j++) {
                ocean[i][j] = new Location(i, j);          
            }
        }
    }

}
