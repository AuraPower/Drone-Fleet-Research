  import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;    
import java.math.*;
import java.net.URL;


public class Statistics extends JPanel {
	  private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		//make the statistics frame
	    JFrame statistics = new JFrame();
	    statistics.setSize(1514, 838);
	    statistics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Statistics statisticsFrame = new Statistics();
	    statisticsFrame.setLayout(null);
	    //make the frame
	    statistics.add(statisticsFrame);
  	  	statistics.setVisible(true);
  	  	
  	  	JLabel TimeToFind = new JLabel("Hours to find: "+ DroneFleet.simCounter/DroneFleet.simCountPerHour) ;//Stats Label 1 SimCounter: subtracts 1 to account for end frame
  	  	statisticsFrame.add(TimeToFind);
  	  	TimeToFind.setBounds((1514-200)/2,540,200,30);
  	  	
  	  	JLabel DroneSpeed = new JLabel("Drone MPH: " + DroneFleet.droneSpeed*(DroneFleet.simCountPerHour/100)) ;//Stats Label 1 SimCounter: subtracts 1 to account for end frame
	  	statisticsFrame.add(DroneSpeed);
	  	DroneSpeed.setBounds((1514-200)/2,650,200,30);
  	  	
  	  	//create/add the button to start the simulation and switch to that screen
	  	JButton terminateButton = new JButton("Close Program");
	  	terminateButton.setBounds((1514-125)/2,25,125,30);//sets the position/size of the terminateButton
	  	statisticsFrame.add(terminateButton);
		  	terminateButton.setVisible(true);
		  		//endButton "press" finder
		  		terminateButton.addActionListener(new ActionListener() {
		  			public void actionPerformed(ActionEvent e) {
		  				System.out.println("Program Terminated.");//write termination message to console
		  				System.exit(0);//terminate console (0 indicates successful termination)
		  			}
		  		});//end terminateButton action listener
	  	
	  		
	}//end main

}//end Statistics
