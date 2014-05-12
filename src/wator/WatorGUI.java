package wator;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


/**
 * 
 * @author Joopyo Hong
 * @version May 2, 2014
 */
public class WatorGUI extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    private WatorGUI watorGui;
    private Wator wator;
    
    private DrawingArea canvas;
    private JPanel titledCanvas;
    
    private JTextField fishNumField;
    private JTextField sharkNumField;
    private JTextField fishReproField;
    private JTextField sharkReproField;
    private JTextField sharkEnergyField;
    private JTextField sharkIncrementField;
    
    private JPanel controlPanel;
    private JPanel inputPanel;
    
    private JTextField statusField;
    private JSlider speedControlSlider;
    private JPanel inputs;    
    
    private JButton startButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton clearButton;
    private int speed;

    private static final int INITIAL_CANVAS_WIDTH = 500;
    private static final int INITIAL_CANVAS_HEIGHT = 500;

    /**
     * Constructor for Wator GUI.
     */
    public WatorGUI() {
        setTitle("Wator");
        watorGui = this;
        watorGui.setPreferredSize(new Dimension(510, 770));
    }

    /**
     * Starts Wator GUI.
     * 
     * @param args Ignored.
     */
    public static void main(String[] args) {
        new WatorGUI().setup();
    }

    /**
     * Creates and displays the GUI
     */
    public void setup() {
        createGui();
        pack();
        setVisible(true);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);
        wator = new Wator(canvas);
    }

    /**
     * Runs Wator program
     */
    @Override
    public void run() {
        changeSpeed(speedControlSlider.getValue());
        wator.run();

        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        
    }
    

    /**
     * Lays out the GUI for the Wator program.
     */
    private void createGui() {
        createComponents();
        arrangeComponents();
        attachListeners();
    }

    /**
     * Creates all Components used by the GUI.
     */
    private void createComponents() {

        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(60,30));
        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(75,30));
        stopButton = new JButton("Stop");
        stopButton.setPreferredSize(new Dimension(60,30));
        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(65,30));
        statusField = new JTextField();

        setTitle("Wator");
        canvas = new DrawingArea();
        canvas.setBackground(Color.DARK_GRAY);
        titledCanvas = new JPanel();
        titledCanvas.setPreferredSize(new Dimension(INITIAL_CANVAS_WIDTH, INITIAL_CANVAS_HEIGHT));
        titledCanvas.setLayout(new BorderLayout());
        titledCanvas.add(canvas, BorderLayout.CENTER);
        addTitledBorder(titledCanvas, "Ocean");
        
        
        controlPanel = new JPanel();
        inputPanel = new JPanel();
        addTitledBorder(inputPanel, "Parameters");
        
        createSpeedSlider();
    }

    /**
     * Creates the speed control.
     */
    private void createSpeedSlider() {
        speedControlSlider =
            new JSlider(SwingConstants.HORIZONTAL, 0, 100, speed);
        speedControlSlider.setPreferredSize(new Dimension(150, 40));
        speedControlSlider.setMajorTickSpacing(20);
        speedControlSlider.setMinorTickSpacing(10);
        speedControlSlider.setPaintTicks(true);
        speedControlSlider.setPaintLabels(true);
        speedControlSlider.setValue(100);
    }

    /**
     * Arranges GUI components
     */
    private void arrangeComponents() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
        
        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        fileMenu.add(eMenuItem);
              
        setLayout(new BorderLayout());
        add(titledCanvas, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        layoutControlPanel();
        layoutInputPanel();
        
        displayStatus("Ready");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    /**
     * Lays out the speed control and button components
     */
    private void layoutControlPanel() {
        inputs = new JPanel();
        JPanel sliderPanel = new JPanel();

        sliderPanel.add(new JLabel("Speed:"));
        sliderPanel.add(speedControlSlider);

        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(inputs, BorderLayout.CENTER);
        controlPanel.add(statusField, BorderLayout.SOUTH);

        inputs.add(sliderPanel);
        inputs.add(startButton);
        inputs.add(pauseButton);
        inputs.add(stopButton);
        inputs.add(clearButton);
    }
    
    /**
     * Lays out the user input components
     */
    private void layoutInputPanel() {
        
        inputPanel.setLayout(new GridLayout(1, 2));
        
        JPanel fishPanel = new JPanel();
        fishPanel.setLayout(new GridLayout(5, 2));
           
        JPanel sharkPanel = new JPanel();
        sharkPanel.setLayout(new GridLayout(5, 2));
   
        inputPanel.add(fishPanel);
        inputPanel.add(sharkPanel);
        
        fishNumField = new JTextField("10000");
        sharkNumField = new JTextField("5000");
        fishReproField = new JTextField("5");
        sharkReproField = new JTextField("5");
        sharkEnergyField = new JTextField("5");
        sharkIncrementField = new JTextField("2");
        
        fishPanel.add(new JLabel("Fish"));
        fishPanel.add(new JLabel(""));
        fishPanel.add(new JLabel("Initial Population"));
        fishPanel.add(fishNumField);
        fishPanel.add(new JLabel("Gestation Period"));
        fishPanel.add(fishReproField);
        fishPanel.add(new JLabel(""));
        fishPanel.add(new JLabel(""));
        fishPanel.add(new JLabel(""));
        fishPanel.add(new JLabel(""));
        
        sharkPanel.add(new JLabel("Shark"));
        sharkPanel.add(new JLabel(""));
        sharkPanel.add(new JLabel("Initial Population"));
        sharkPanel.add(sharkNumField);
        sharkPanel.add(new JLabel("Gestation Period"));
        sharkPanel.add(sharkReproField);
        sharkPanel.add(new JLabel("Energy Tank"));
        sharkPanel.add(sharkEnergyField);
        sharkPanel.add(new JLabel("Energy+ per Fish"));
        sharkPanel.add(sharkIncrementField);
    }

    /**
     * Attach listeners to all GUI components that need them.
     */
    private void attachListeners() {
        
        DocumentListener docListener = new DocumentListener() {
            
            private void inputValidityCheck(DocumentEvent e) {
                try {
                    getInt(e.getDocument().getText(0, e.getDocument().getLength()));
                    startButton.setEnabled(true);
                    displayStatus("Ready");
                } catch (NumberFormatException er) {
                    displayStatus("Please insert a valid input.");
                    startButton.setEnabled(false);
                } catch (BadLocationException er) {
                    displayStatus("Internal Error");
                }
                    
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) {    
                inputValidityCheck(e);
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                inputValidityCheck(e);
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                inputValidityCheck(e);
            }
        };
        
        
        fishNumField.getDocument().addDocumentListener(docListener);
        sharkNumField.getDocument().addDocumentListener(docListener);
        fishReproField.getDocument().addDocumentListener(docListener);
        sharkReproField.getDocument().addDocumentListener(docListener);
        sharkEnergyField.getDocument().addDocumentListener(docListener);
        sharkIncrementField.getDocument().addDocumentListener(docListener);
        

        // Start
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                wator = new Wator(canvas);
                startButton.setEnabled(false);
                clearButton.setEnabled(false);
                stopButton.setEnabled(true);
                pauseButton.setEnabled(true);
                displayStatus("Running");

                Parameters.fishPop = getInt(fishNumField.getText());
                Parameters.sharkPop = getInt(sharkNumField.getText());
                Parameters.fishRepro = getInt(fishReproField.getText());
                Parameters.sharkRepro = getInt(sharkReproField.getText());
                Parameters.sharkEnergy = getInt(sharkEnergyField.getText());
                Parameters.sharkIncrement = getInt(sharkIncrementField.getText());
                
                fishNumField.setEnabled(false);
                sharkNumField.setEnabled(false);
                fishReproField.setEnabled(false);
                sharkReproField.setEnabled(false);
                sharkEnergyField.setEnabled(false);
                sharkIncrementField.setEnabled(false);
                
                Thread thread = new Thread(watorGui);
                thread.start();
            }
        });
        

        // Pause
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if ("Pause".equals(pauseButton.getText())) {
                    wator.paused = true;
                    pauseButton.setText("Resume");
                    displayStatus("Paused");
                }
                else {
                    wator.paused = false;
                    pauseButton.setText("Pause");
                    displayStatus("Running");
                }
            }
        });
        // Clear
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                canvas.clear();
            }
        });
         
        // Stop
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if ("Resume".equals(pauseButton.getText())) {
                    wator.paused = false;
                    pauseButton.setText("Pause");
                }
                
                wator.running = false;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                pauseButton.setEnabled(false);
                clearButton.setEnabled(true);
                
                fishNumField.setEnabled(true);
                sharkNumField.setEnabled(true);
                fishReproField.setEnabled(true);
                sharkReproField.setEnabled(true);
                sharkEnergyField.setEnabled(true);
                sharkIncrementField.setEnabled(true);
                displayStatus("Ready");          
                
            }
        });
        // Set speed
        speedControlSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                changeSpeed(speedControlSlider.getValue());
            }
        });
        
    }
    
    /**
     * Converts provided string into a corresponding integer.
     * If the string is not valid for conversion, exception is thrown.
     * 
     * @param s The string that will be converted to an integer
     * @throws NumberFormatException    If the string does not represent positive integers including zero.
     */
    private int getInt(String s) throws NumberFormatException {
        int number;
        number = Integer.parseInt(s);
        if (number < 0)
            throw new NumberFormatException();
        
        return number;
        
    }

    /**
     * Adds a titled border to the given Component. According to the Swing documentation,
     * the <code>setBorder(Border border)</code> does not work well with all types
     * of JComponents.
     * 
     * @param component The component to which a titled forder is to be added.
     * @param title The text used for the title.
     */
    private static void addTitledBorder(JComponent component, String title) {
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, title);
        component.setBorder(titledBorder);
    }
    
    
    /**
     * Changes the speed at which the movements are executed, to correspond to
     * the speed set by the speed control slider. A speed of zero
     * means the minimum speed, while the maximum speed available on the
     * slider means that there is no delay while progressing to the next round.
     * @param speed 
     */
    private void changeSpeed(int speed) {
        if (speed == 100)
            wator.shouldTakeBreak = false;
        else if (speed == 0) {
            wator.shouldTakeBreak = true;
            Parameters.speed = 100000;
        }
        else {
            wator.shouldTakeBreak = true;
            Parameters.speed = 100000 / speed;
        }
    }
    
    /**
     * Changes the speed at which the movements are executed, to correspond to
     * the speed set by the speed control slider. A speed of zero
     * means the minimum speed, while the maximum speed available on the
     * slider means that there is no delay while progressing to the next round.
     * @param status    Status of  
     */
    void displayStatus(String status) {
        statusField.setText(status);
    }
}