  import java.awt.Color;
import java.awt.Graphics;
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
	    startup.setSize(1980, 1080);
	    startup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Startup startupFrame = new Startup();

	    //make the frame
	    startup.add(startupFrame);
  	  	if(DroneFleet.currentFrame==0) {
		  startup.setVisible(true);
  	  	}
  	  	
  	  	//create/add the button to start the simulation and switch to that screen
  	  	JButton startButton = new JButton("Start Simulation");
  	  	startButton.setBounds(50,100,95,30);
  	  	startupFrame.add(startButton);
  	  		startButton.addActionListener(new ActionListener() {
  	  			public void actionPerformed(ActionEvent e) {
  	  				DroneFleet.currentFrame=1;
//  	  				TopLevelSim.dronefleetFlag=1;
  	  				System.out.println("start button pressed: " + DroneFleet.currentFrame);
  	  				startup.setVisible(false);
  	  				
  	  			}
  	  		});
	    
	    
	  }//main end
	} //JPanel end

