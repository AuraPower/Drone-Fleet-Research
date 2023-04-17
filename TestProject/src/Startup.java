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

    // create an array of strings to populate our dropdown list
    public static String[] droneMovementOptions = {"Select Movement Type", "Random", "Grid"};
    // create a variable to hold the selected option
    public static String droneSelectedOption = "";
    
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
        movementDropdownBox.setBounds((1514/2)-125/2, (838/2), 125, 30);
        startupFrame.add(movementDropdownBox);
        // create an ActionListener to update the variable when the combo box selection changes
        movementDropdownBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	droneSelectedOption = (String) movementDropdownBox.getSelectedItem();
//                System.out.println("Selected option: " + selectedOption[0]);
            }
        });
        
        //create the Y Input for the starting position of the drones
        NumberFormat screenYFormat = NumberFormat.getIntegerInstance();
        NumberFormatter screenYFormatter = new NumberFormatter(screenYFormat);
        screenYFormatter.setMinimum(0);
        screenYFormatter.setMaximum(DroneFleet.screenY);
        JFormattedTextField startPosDronesYInput = new JFormattedTextField(screenYFormatter);
        startPosDronesYInput.setValue(0);
        startPosDronesYInput.setBounds(100,150,200,30);
        startupFrame.add(startPosDronesYInput);
        startPosDronesYInput.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
               // Get the current value of the text field
               Object value = startPosDronesYInput.getValue();

               // Check if the value is within the bounds
               if (value instanceof Integer) {
                  int intValue = (Integer) value;
                  if (intValue < 0 || intValue > DroneFleet.screenY) {
                     // Value is outside the bounds, display error message and return focus to text field
                     JOptionPane.showMessageDialog(startupFrame, "Value must be between " + 0 + " and " + DroneFleet.screenY);
                     startPosDronesYInput.requestFocus();
                  }
               }
            }
         });
        //create the X Input for the starting position of the drones

        //create/add the button to start the simulation and switch to that screen
        JButton startButton = new JButton("Start Simulation");
        startButton.setBounds((1514/2)-125/2, 25, 125, 30);
        startupFrame.add(startButton);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DroneFleet.currentFrame = 1;
                System.out.println("start button pressed: " + DroneFleet.currentFrame);
                startup.setVisible(false);
                DroneFleet.main(args);
            }
        });

        startup.add(startupFrame);
        if (DroneFleet.currentFrame == 0) {
            startup.setVisible(true);
        }
    }
}

