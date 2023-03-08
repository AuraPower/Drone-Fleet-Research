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
		//Graphics Environment to make fullscreen devices
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = graphics.getDefaultScreenDevice();
		//make the statistics frame
	    JFrame statistics = new JFrame();
	    statistics.setSize(1980, 1080);
	    statistics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Statistics statisticsFrame = new Statistics();
	    statisticsFrame.setLayout(null);
	    //make the frame
	    statistics.add(statisticsFrame);
  	  	statistics.setVisible(true);
  	  	device.setFullScreenWindow(statistics);//set fullscreen using GraphicsEnvironmentDevice
  	  	
  	  	JLabel timeToFind = new JLabel("Total time to find target: " + (DroneFleet.simCounter-1)) ;//Stats Label 1 SimCounter: subtracts 1 to account for end frame
  	  	statisticsFrame.add(timeToFind);
  	  	timeToFind.setBounds(960-200/2,540,200,30);
  	  	
  	  	//create/add the button to start the simulation and switch to that screen
	  	JButton terminateButton = new JButton("Close Program");
	  	terminateButton.setBounds(960-125/2,25,125,30);//sets the position/size of the terminateButton
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
