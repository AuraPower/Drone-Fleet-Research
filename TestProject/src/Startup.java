  import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import java.math.*;
import java.net.URL;
import java.text.NumberFormat;

public class Startup extends JPanel {
    private static final long serialVersionUID = 1L;

    // create an array of strings to populate our dropdown list for the movement type
    public static String[] droneMovementOptions = {"Select Movement Type", "Random", "Grid"};
    // create a variable to hold the selected option for the movement type
    public static String droneMovementSelectedOption = "";
    
    // create an array of strings to populate our dropdown list for the marker type
    public static String[] markerlengthOptions = {"Select Marker Size", "Short", "Full-Screen"};
    // create a variable to hold the selected option for the marker type
    public static String markerLengthSelectedOption = "";
    
    // create an array of strings to populate our dropdown list for the position randomness
    public static String[] droneposTypeOptions = {"Select Drone Starting Position Type", "Manual", "Center", "Random"};
    // create a variable to hold the selected option for the position randomness
    public static String droneposTypeSelectedOption = "";
    
    //variable for debug mode
    public static boolean debugMode = false; //false is debug mode disabled, true is enabled
    
    //Returns a random number between 0 and the input
  	public static int getRandom(int max) {
  		return (int) (Math.random()*max);
  	}
  	
    //paintComponent paints all the drones in the array list "drones"	  
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    //main
    public static void main(String[] args) {
        //make the startup frame
        JFrame startup = new JFrame();
        startup.setSize(1514, 838);//sets the JFrame's size to 1514x838 for 15mix8mi plus some for edges and app header
        startup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Startup startupFrame = new Startup();
        startupFrame.setLayout(null);//sets the JFrame to have no layout manager (automatically moves widgets for some reason

        //create the combo box for movement selection
        JComboBox<String> movementDropdownBox = new JComboBox<String>(droneMovementOptions);
        movementDropdownBox.setBounds((1514/2)-160/2, (838/2), 160, 30);
        startupFrame.add(movementDropdownBox);
        // create an ActionListener to update the variable when the combo box selection changes
        movementDropdownBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	droneMovementSelectedOption = (String) movementDropdownBox.getSelectedItem();
            }
        });
        
        //create the combo box for grid length selection
        JComboBox<String> fullgridDropdownBox = new JComboBox<String>(markerlengthOptions);
        fullgridDropdownBox.setBounds((1514/2)-150/2, (838/2)+150, 150, 30);
        startupFrame.add(fullgridDropdownBox);
        // create an ActionListener to update the variable when the combo box selection changes
        fullgridDropdownBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	markerLengthSelectedOption = (String) fullgridDropdownBox.getSelectedItem();
            }
        });
        
        //create the Y Input for the starting position of the drones
        NumberFormat screenYFormat = NumberFormat.getIntegerInstance();
        NumberFormatter screenYFormatter = new NumberFormatter(screenYFormat);
        screenYFormatter.setMinimum(0);
        screenYFormatter.setMaximum(DroneFleet.screenY);
        JFormattedTextField startPosDronesYInput = new JFormattedTextField(screenYFormatter);
        startPosDronesYInput.setValue(0);
        startPosDronesYInput.setBounds(100,130,230,30);
        startupFrame.add(startPosDronesYInput);
        
        JLabel YInputText = new JLabel("Input Drone Starting Y Position (0 - "+ DroneFleet.screenY + ") ");
        YInputText.setBounds(100,100,300,30);
        startupFrame.add(YInputText);
        
        //create the X Input for the starting position of the drones
        NumberFormat screenXFormat = NumberFormat.getIntegerInstance();
        NumberFormatter screenXFormatter = new NumberFormatter(screenXFormat);
        screenXFormatter.setMinimum(0);
        screenXFormatter.setMaximum(DroneFleet.screenX);
        JFormattedTextField startPosDronesXInput = new JFormattedTextField(screenXFormatter);
        startPosDronesXInput.setValue(0);
        startPosDronesXInput.setBounds(100,230,230,30);
        startupFrame.add(startPosDronesXInput);

        JLabel XInputText = new JLabel("Input Drone Starting X Position (0 - "+ DroneFleet.screenX + ") ");
        XInputText.setBounds(100,200,300,30);
        startupFrame.add(XInputText);
        
        //create the combo box for drone starting position selection
        JComboBox<String> dronepostypeDropdownBox = new JComboBox<String>(droneposTypeOptions);
        dronepostypeDropdownBox.setBounds(100, 50, 230, 30);
        startupFrame.add(dronepostypeDropdownBox);
        // create an ActionListener to update the variable when the combo box selection changes
        dronepostypeDropdownBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	droneposTypeSelectedOption = (String) dronepostypeDropdownBox.getSelectedItem();
            	if(droneposTypeSelectedOption == "Manual") {
            		YInputText.setVisible(true);
            		XInputText.setVisible(true);
            		startPosDronesYInput.setVisible(true);
            		startPosDronesXInput.setVisible(true);
            	}else if (droneposTypeSelectedOption == "Center") {
            		YInputText.setVisible(false);
            		XInputText.setVisible(false);
            		startPosDronesYInput.setVisible(false);
            		startPosDronesXInput.setVisible(false);
            		DroneFleet.startingx = (int)(DroneFleet.screenX/2);
            		DroneFleet.startingy = (int)(DroneFleet.screenY/2);
            	}else if (droneposTypeSelectedOption == "Random") {
            		YInputText.setVisible(false);
            		XInputText.setVisible(false);
            		startPosDronesYInput.setVisible(false);
            		startPosDronesXInput.setVisible(false);
            		DroneFleet.startingx = getRandom(DroneFleet.screenX);
            		DroneFleet.startingx = getRandom(DroneFleet.screenY);
            	}
            		
            }
        });
        
        //create/add the button to start the simulation and switch to that screen
        JButton startButton = new JButton("Start Simulation");
        startButton.setBounds((DroneFleet.screenX/2)-125/2, 25, 125, 30);
        startupFrame.add(startButton);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DroneFleet.currentFrame = 1;
//                System.out.println("start button pressed: " + DroneFleet.currentFrame);
                startup.setVisible(false);
                DroneFleet.main(args);
            }
        });
        
      //create/add the button to switch between debug and non-debug mode
        JButton debugButton = new JButton("Debug");
        debugButton.setBounds((DroneFleet.screenX)-125, DroneFleet.screenY-30, 125, 30);
        startupFrame.add(debugButton);
        debugButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                debugMode = !debugMode;
                System.out.println("debug mode button pressed " + debugMode);
                if(debugMode) {
                	startupFrame.setBackground(Color.gray);
                }else if (!debugMode) {
                	startupFrame.setBackground(Color.white);
                }
            
            }
        });
        
        startButton.addActionListener(new ActionListener() {
     	   @Override
     	   public void actionPerformed(ActionEvent e) {
     		   if(droneposTypeSelectedOption == "Manual") {
     			// Get the current value of the text field
          	      Object valueX = startPosDronesXInput.getValue();
          	      // Check if the value is within the bounds
          	      if (valueX instanceof Integer) {
          	         int intXValue = (Integer) valueX;
          	         if (intXValue < 0 || intXValue > DroneFleet.screenX) {
          	            // Value is outside the bounds, display error message
          	            JOptionPane.showMessageDialog(startupFrame, "Value must be between " + 0 + " and " + DroneFleet.screenX);
          	         } else {
          	            DroneFleet.startingx = intXValue;
          	         }
          	      }
     		   }
     	   }
     	});
        startButton.addActionListener(new ActionListener() {
      	   @Override
      	   public void actionPerformed(ActionEvent e) {
      		 if(droneposTypeSelectedOption == "Manual") {
      	      // Get the current value of the text field
      	      Object valueY = startPosDronesYInput.getValue();
      	      // Check if the value is within the bounds
      	      if (valueY instanceof Integer) {
      	         int intYValue = (Integer) valueY;
      	         if (intYValue < 0 || intYValue > DroneFleet.screenY) {
      	            // Value is outside the bounds, display error message
      	            JOptionPane.showMessageDialog(startupFrame, "Value must be between " + 0 + " and " + DroneFleet.screenY);
      	         } else {
      	            DroneFleet.startingy = intYValue;
      	         }
      	      }
      		 }
      	   }
      	});

        startup.add(startupFrame);
        if (DroneFleet.currentFrame == 0) {
            startup.setVisible(true);
        }
    }
}

