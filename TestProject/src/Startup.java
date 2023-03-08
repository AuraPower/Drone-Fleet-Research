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

public class Startup extends JPanel {
  private static final long serialVersionUID = 1L;

	  //paintComponent paints all the drones in the array list "drones"	  
	  public void paintComponent(Graphics g) {
		  super.paintComponent(g);
	  }
  
	  //main
	  public static void main(String[] args) {

		//make the startup frame
	    JFrame startup = new JFrame();
	    startup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Startup startupFrame = new Startup();
		//Graphics Environment to make fullscreen devices
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = graphics.getDefaultScreenDevice();
  	  	device.setFullScreenWindow(startup);//set fullscreen using GraphicsEnvironmentDevice

	    //make the frame
	    startup.add(startupFrame);
  	  	if(DroneFleet.currentFrame==0) {
		  startup.setVisible(true);
  	  	}
  	  	
  	  	//create/add the button to start the simulation and switch to that screen
  	  	JButton startButton = new JButton("Start Simulation");
  	  	startButton.setBounds(960-125/2,25,125,30);
  	  	startupFrame.add(startButton);
  	  		startButton.addActionListener(new ActionListener() {
  	  			public void actionPerformed(ActionEvent e) {
  	  				DroneFleet.currentFrame=1;
//  	  				TopLevelSim.dronefleetFlag=1;
  	  				System.out.println("start button pressed: " + DroneFleet.currentFrame);
  	  				startup.setVisible(false);
  	  				DroneFleet.main(args);
  	  			}
  	  		});
	    
	    
	  }//main end
	} //JPanel end
