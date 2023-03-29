import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.math.*;
import java.net.URL;

public class DroneFleet extends JPanel {//create the DroneFleet class and have it extend java swing JPanel
  private static final long serialVersionUID = 1L;//boilerplate
 
//public variables
  //array lists for the drones and drone paths
      static ArrayList<drone> drones = new ArrayList<drone>();
      ArrayList<ArrayList<Integer[]>> dronePaths = new ArrayList<ArrayList<Integer[]>>();
  	  static ArrayList<target> targets = new ArrayList<target>();
  //settings variables
  	  private static boolean probabilisticRadius = true;
  	  public static boolean fullMarkerLines = false;
  //timer variables
  	  public static int simFlag=1, simCounter=0;
  //drone&main variables
  	  //number of drones starting on the screen
	  public static int numDrones = 50;
	  //starting positions
	  public static int startingx = (int)(1514/2), startingy = (int)(838/2);
	  public static int directionStart = 1; //0 is nothing, 1 is random directions
	  //drone size
	  public static int dronesize = 30;
	  //drone search radius: how far drones will start to spot targets
	  public static int droneSearchRadius = 50;//100 is a mile radius, 50 half mile radius (for maximum search radius)
	  //drone speed
	  public static int droneSpeed = 10;//in pixels (relate to mph with simCountPerHour)
  //sim variables
	  //simulation speed speed
	  public static int simspeed=1; //lower # is faster
  	  public static int currentFrame = 0;
  	  public static double simCountPerHour = 100.0;//100 is default (100 sim counts = 1 hour) (double for accuracy)
  //target variables
  	  public static boolean isTargetPosRandom = true;
	  public static int targetX = 450, targetY = 300, targetSize = 20, targetSpeed = 2;
  
//BEGIN FUNCTIONS
	    
	  public void paintComponent(Graphics g) {	  //paintComponent paints drones and targets on the screen
		  super.paintComponent(g);
		  drawMarkers(g);//draw the mile markers & label(s)
		  for (int i = 0; i < drones.size(); i++) {//draw drones and drone path
			  drone cdrone = drones.get(i);
			  drawDronePath(g, i);//calls the drawDronePath function to draw the drone's path
			  g.setColor(cdrone.color);
			  g.fillOval(cdrone.x, cdrone.y, cdrone.size, cdrone.size);
//			  g.drawImage(droneImage, 50, 50, this);
		  }//end painting drones
		  for (int i = 0; i<targets.size(); i++) {//draw targets (people, etc)
			  target ctarget = targets.get(i);
			  g.setColor(ctarget.color);
			  g.fillOval(ctarget.getX(), ctarget.getY(), ctarget.getSize(), ctarget.getSize());
		  }//end painting targets
	  }//end paintComponent
	  
	  public void drawMarkers(Graphics g) {//draw the mile markers and label(s)
		  int xspace = 100;//init the xspace variable for drawing the x lines
		  int yspace = 100;//init the yspace variable for drawing the y lines
		  for(int i = 0; i<14; i++) {//draw x axis mile markers
			  if(fullMarkerLines==true) {
				  g.drawLine(xspace, 0, xspace, 800);//draw the long x line at the current xspace
			  }else { g.drawLine(xspace, 0, xspace, 25);}//draw the short x line at the current xspace
			  xspace=xspace+100;//increment the xspace
		  }
		  for(int i = 0; i<7; i++) {//draw y axis mile markers
			  if(fullMarkerLines==true) {
				  g.drawLine(0, yspace, 1500, yspace);//draw the long y line at the current xspace 
			  }else { g.drawLine(0, yspace, 25, yspace);}//draw the short y line at the current xspace
			  yspace=yspace+100;//increment the yspace
		  }
		  g.drawLine(5,15,5,25);//draw legend left line
		  g.drawLine(95, 15, 95, 25);//draw legend middle line
		  g.drawLine(5, 20, 95, 20);//draw legend right line
	  }//end drawMarkers
	  
	  private void drawDronePath(Graphics g, int droneIndex) {//drawDronePath draws the array list "dronePath" based on the points provided.
		  //every time a drone performs the function move it records the "path" (i.e. old position and new position)
		    ArrayList<Integer[]> dronePath = dronePaths.get(droneIndex);
		    for (int i = 0; i < dronePath.size() - 1; i++) {
		      Integer[] point1 = dronePath.get(i);
		      Integer[] point2 = dronePath.get(i + 1);
		      g.setColor(Color.GRAY);
		      g.drawLine(point1[0], point1[1], point2[0], point2[1]);
		    }
		  }
	  
	  private void moveDrones() {	  //move moves every drone at the same time (1 call = every drone moves)
		  for (int i = 0; i < drones.size(); i++) {//specific drone for loop (iterates for every drone)
			  drone drone = drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
			  
			  boolean insideArea=false;
			  int newX = 0;
		      int newY = 0;
		      
			  while(!insideArea) {//while the drone's new position is not within the screen, run this while loop
			  	Random random = new Random();//create a new random number
	            double direction = random.nextDouble() * 2 * Math.PI;//Generate a random direction between 0 and 2π radians
	            int xVelocity = (int) Math.round(droneSpeed * Math.cos(direction));//Calculate the x component of the velocity based on the direction
	            int yVelocity = (int) Math.round(droneSpeed * Math.sin(direction));//Calculate the y component of the velocity based on the direction
	            newX = drone.x + xVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            newY = drone.y + yVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            
	            if (newX >= 0 && newX < 1500 && newY >= 0 && newY < 800) {// Check if the new position is inside the screen
	                insideArea = true;//if the new position is in the area, break out of the while loop
	            }//end if
			  }//end while loop
		        drone.x = newX; //Move the drone to the new x position after breaking out of the while loop
		        drone.y = newY; //Move the drone to the new y position after breaking out of the while loop
		      dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
	      }//end specific drone for loop
	      repaint();//repaints the screen with the new drones' location --- REPAINTS SCREEN EVERY TIME THE DRONES MOVE
	  }//end moveDrones
	  
	  private void moveTargets() {//moves the targets 
		  for (int i = 0; i<targets.size(); i++) {//for every target
			  target ctarget = targets.get(i);//set the current target to the target currently being worked on
			  boolean insideArea=false;
			  int newX = 0;
		      int newY = 0;
		      
			  while(!insideArea) {//while the target's new position is not within the screen, run this while loop
			  	Random random = new Random();//create a new randomness "class"
	            double direction = random.nextDouble() * 2 * Math.PI;//Generate a random direction between 0 and 2π radians
	            int xVelocity = (int) Math.round(targetSpeed * Math.cos(direction));//Calculate the x component of the velocity based on the direction
	            int yVelocity = (int) Math.round(targetSpeed * Math.sin(direction));//Calculate the y component of the velocity based on the direction
	            newX = ctarget.x + xVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            newY = ctarget.y + yVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            
	            if (newX >= 0 && newX < 1500 && newY >= 0 && newY < 800) {// Check if the new position is inside the screen
	                insideArea = true;//if the new position is in the area, break out of the while loop
	            }//end if
			  }//end while loop
		        ctarget.x = newX; //Move the target to the new x position after breaking out of the while loop
		        ctarget.y = newY; //Move the target to the new y position after breaking out of the while loop
		  }//end painting targets
		  repaint();//repaints the screen with the new target's location --- REPAINTS SCREEN EVERY TIME THE TARGETS MOVE
	  }//end targets
	
	  //main
	  public static void main(String[] args){
	    JFrame sim = new JFrame();//makes Jframe
	    sim.setSize(1514, 838);//sets the JFrame's size to 1514x838 for 15mix8mi plus some for edges and app header
	    sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the program to close on the X button (only viable for non fullscreen)
  	  	DroneFleet simFrame = new DroneFleet();//makes a new frame based on this class (DroneFleet)
	    sim.add(simFrame);//make the frame
	    sim.setVisible(true);//set the panel to be visible
	  	//create/add the button to start the simulation and switch to that screen
	  	JButton endButton = new JButton("Open Statistics");//create the button to go to the next screen (statistics screen)
	  	simFrame.setLayout(null);
	  	sim.setLayout(null);
	  	endButton.setBounds(800-125/2,25,125,30);//set the bounds of the button to be the top middle of the sim screen
	  	simFrame.add(endButton);//adds the button to the sim frame
	  	endButton.setVisible(false);//sets the button to not be visible (becomes visible after sim has finished)
	  	endButton.addActionListener(new ActionListener() {//endButton "press" function
	  		public void actionPerformed(ActionEvent e) {//listener for the button to be pressed
	  			//System.out.println("End button pressed");//debug - write to console that button was pressed
	  			sim.setVisible(false);//set the sim panel to not be visible
	  			Statistics.main(null);//run the statistics class
	  		}
	  	});
	  	JLabel tickLabel = new JLabel("1 mile");
	  	tickLabel.setBounds(30, 25, 80, 20);
	  	simFrame.add(tickLabel);
	  	
	    for (int i = 0; i < numDrones; i++) {//drone creation for loop
	    	int size = dronesize;//sets each drone's size to the global size
	      Color color = Color.black;//makes the drone's color be black (for drawing the component to the screen)
	      simFrame.drones.add(new drone(startingx, startingy, size, color));//sends the current drone to the drone arraylist (0 for most things as they get made during moveDrone)
	      simFrame.dronePaths.add(new ArrayList<Integer[]>());//adds another slot to the dronepath arraylist for every drone created
	    }//end drone creation loop
	    
	    if(isTargetPosRandom) {//checks if the target position is set to random or set
	    	simFrame.targets.add(new target((int)(Math.random()*1500), (int)(Math.random()*800), targetSize, Color.RED));//creates actual target (with random x and y) and adds it to the targets arraylist
	    }else {//if target position is not random
	    	simFrame.targets.add(new target(targetX, targetY, targetSize, Color.RED));	//creates the actual target and adds it to the targets arraylist
	    }
	    
	  	Timer timer = new Timer(simspeed, new ActionListener() {  //MAIN SIM TIMER
	  	     public void actionPerformed(ActionEvent e) {
	  	    	 if(simFlag==1) {//if simFlag shows sim is on
	  	  	       simFrame.moveDrones();//move all the drones
	  	  	       simFrame.moveTargets();
	  	  	       simFlag = checkForFind.checkForFindFunction(drones,targets,droneSearchRadius,simCounter,probabilisticRadius);//check for target find
		  	       simCounter++;//increment the sim counter
	  	    	 }else if(simFlag==0) {//if SimFlag shows sim is off (only occurs after find)
	  	    		//dronefleet final message; drone speed in mph is dronespeed in pixels times the ratio of the simcountperhour/100 (pixels in a mile is 100)
//	  	    		 System.out.println("Hours to find: "+ simCounter/simCountPerHour + ", Drone MPH: " + droneSpeed*(simCountPerHour/100));
	  	    		 endButton.setVisible(true);//shows the endButton (moves to statistics screen)
	  	    	 }//end simflag if
	  	     }//end listener
	  	});//end timer action
	  	   timer.start();//start the sim timer
	  }//end main
	} //JPanel end

class drone {  //drone class for tracking/moving/etc drones
		int x;
		int y;
		int size;
		Color color;
	public drone(int x, int y, int size, Color color) {//drone constructor
		this.x = x;
	    this.y = y;
	    this.size = size;
	    this.color = color;
	}//end constructor
	//Methods for drone class
	public Color getColor() {
		return color;
	}
		public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getSize() {
		return size;
	}
	}//drone class end
	
class target{  //target class for tracking target position, drawing target, etc
	int x;
	int y;
	int size;
	Color color;
	public target(int x, int y, int size, Color color) {	//constructor
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
	}//constructor end
	//target class methods
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public int getSize() {
			return size;
		}
}//target class end