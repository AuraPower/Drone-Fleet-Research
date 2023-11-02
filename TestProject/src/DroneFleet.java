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
      static ArrayList<ArrayList<Integer[]>> dronePaths = new ArrayList<ArrayList<Integer[]>>();
  	  static ArrayList<target> targets = new ArrayList<target>();
  	  static ArrayList<int[]> gridSearchCoordinateList = new ArrayList<int[]>();
  //settings variables
  	  private static boolean probabilisticRadius = true;
  	  public static boolean fullMarkerLines = false;
  	  public static int screenX = 1500;//screen X size (width) variable
  	  public static int screenY = 800;//screen Y size (height) variable
  //timer variables
  	  public static int simFlag=1, simCounter=0;
  //drone&main variables
  	  //number of drones starting on the screen
	  public static int numDrones = 64;
	  //starting positions
	  public static int startingx, startingy; // only need to initialize, set in startup
	  public static int directionStart = 1; //0 is nothing, 1 is random directions
	  //drone size
	  public static int dronesize = 30;
	  //drone search radius: how far drones will start to spot targets
	  public static int droneSearchRadius = 50;//100 is a mile radius, 50 half mile radius (for maximum search radius)
	  //drone speed
	  public static int droneSpeed = 10;//in pixels (relate to mph with simCountPerHour)
	  //drone fault ratio (0-1, 0 being no drones faulted, 1 being all)
	  public static double droneFaultRatio = 0.08;
	  //drone false positive ratio (if faulted, chance every move to generate a false positive (0.1=10% chance every time a faulted drone moves)
	  public static double droneFalsePosChance = 0.008; 
	  //drone false positive count
	  public static double falsePositiveCount = 0;
  //sim variables
	  public static int simspeed=50; //lower # is faster
  	  public static int currentFrame = 0;
  	  public static double simCountPerHour = 100.0;//100 is default (100 sim counts = 1 hour) (double for accuracy)
  //target variables
  	  public static boolean isTargetPosRandom = true;
	  public static int targetX = 450, targetY = 300, targetSize = 20, targetSpeed = 2; //target position (only if not random)
  //other
	  BufferedImage droneImg = null;{
		  try {
		      droneImg = ImageIO.read(new File("drone.png")); //image for drones
		  } catch (IOException e) {
			  
		  }
	  }
//BEGIN FUNCTIONS
	    
	  public void paintComponent(Graphics g) {	  //paintComponent paints drones and targets on the screen
		  super.paintComponent(g);
		  drawMarkers(g);//draw the mile markers & label(s)
		  for (int i = 0; i < drones.size(); i++) {//draw drones and drone path
			  drone cdrone = drones.get(i);
			  drawDronePath(g, i);//calls the drawDronePath function to draw the drone's path
			  g.setColor(cdrone.color);
			  if(Startup.debugMode) {//if the simulation is in debug mode, draw the drones as circles with differentiating colors for faulted agents
				  g.fillOval(cdrone.x-cdrone.size/2, cdrone.y-cdrone.size/2, cdrone.size, cdrone.size); //debug mode drawing
			  } else if (Startup.debugMode) {//if not in debug mode, draw drones as normal
				  g.drawImage(droneImg, cdrone.x-cdrone.size/2, cdrone.y-cdrone.size/2, cdrone.x+cdrone.size/2, cdrone.y+cdrone.size/2, 0, 0, 500, 500, null);
			  }			  
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
		  for(int i = 0; i<(screenX/100 - 1); i++) {//draw x axis mile markers
			  if(fullMarkerLines==true) {
				  g.drawLine(xspace, 0, xspace, screenY);//draw the long x line at the current xspace
			  }else { g.drawLine(xspace, 0, xspace, 25);}//draw the short x line at the current xspace
			  xspace=xspace+100;//increment the xspace
		  }
		  for(int i = 0; i<(screenY/100 - 1); i++) {//draw y axis mile markers
			  if(fullMarkerLines==true) {
				  g.drawLine(0, yspace, screenX, yspace);//draw the long y line at the current xspace 
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
	
	  //main
	  public static void run(int runType, boolean fullMarkerLinesIn, int startingxIn, int startingyIn, String droneMovementSelectedOption, boolean debugMode){
		  //variable passthrough
		  fullMarkerLines = fullMarkerLinesIn;
		  startingx = startingxIn;
		  startingy = startingyIn;
		  //runType is 0 for single, 1 for multi-run
		  //need to reset all variables?????
		  simFlag = 1;
		  simCounter = 0;
		  drones.clear();
		  dronePaths.clear();
		  targets.clear();
		  falsePositiveCount = 0;
		  currentFrame = 0;
		  
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
	  	
	  	int droneID = 0;
	    for (int i = 0; i < numDrones; i++) {//drone creation for loop
		    //Randomly faults drones based on fault probability input
	    	boolean faulted = false;
		    double currentFaultChance = Math.random();
		    if(currentFaultChance < droneFaultRatio) {
		    	faulted = true;
		    }
		    int size = dronesize;//sets each drone's size to the global size
		    Color color = Color.black;//makes the drone's color be black (for drawing the component to the screen)
		    if(faulted) {
		    	color = Color.gray;//makes faulted drones gray instead of black
		    }
		    simFrame.drones.add(new drone(startingx, startingy, size, color, faulted, droneID));//sends the current drone to the drone arraylist
		    droneID += 1; //increments the droneID counter
		    simFrame.dronePaths.add(new ArrayList<Integer[]>());//adds another slot to the dronepath arraylist for every drone created
	    }//end drone creation loop
	    
	    if(isTargetPosRandom) {//checks if the target position is set to random or set
	    	if(App.test_mode) {//if testing mode is on, put the target anywhere in the inside 1/9th of the screen
	    		int newtargetX_start = (int)(Math.random()*(screenX));//generate within front 2/3s of screen
	    		int newtargetY_start = (int)(Math.random()*(screenY));//generate within front 2/3s of screen
	    		//if the new target's start pos is in the front 1/3 of the screen, just place on edge of 1/3
//	    		if(newtargetX_start < screenX - (screenX*(2/3))) {
//	    			newtargetX_start = screenX - screenX*(2/3);
//	    		}
//	    		if(newtargetY_start > screenY - (screenY*(2/3))) {
//	    			newtargetY_start = screenY- screenY*(2/3);
//	    		}
		    	simFrame.targets.add(new target(newtargetX_start, newtargetY_start, targetSize, Color.RED));//creates actual target (with random x and y) and adds it to the targets arraylist
	    	}else {//if testing mode is not on, put the target anywhere
		    	simFrame.targets.add(new target((int)(Math.random()*screenX), (int)(Math.random()*screenY), targetSize, Color.RED));//creates actual target (with random x and y) and adds it to the targets arraylist
	    	}
	    }else {//if target position is not random
	    	simFrame.targets.add(new target(targetX, targetY, targetSize, Color.RED));	//creates the actual target and adds it to the targets arraylist
	    }
	    
	  	Timer timer = new Timer(simspeed, new ActionListener() {  //MAIN SIM TIMER
	  	     public void actionPerformed(ActionEvent e) {
	  	    	 if(simFlag==1) {//if simFlag shows sim is on
	  	    	   if(droneMovementSelectedOption == "Random") {
	  	    		   Movement.moveDronesRandom();//moves all the drones
	  	    	   }else if (droneMovementSelectedOption == "Grid") {
	  	    		   Movement.moveDronesGrid();
	  	    	   }else {
	  	    		   System.out.println("Error code 1: Movement Function Incorrectly Selected");
	  	    		   System.exit(0);
	  	    	   }
	  	  	       
	  	  	       Movement.moveTargetsRandom();//moves all the targets
	  	  	       simFrame.repaint();//repaints the screen with the new target's location --- REPAINTS SCREEN EVERY TIME THE TARGETS MOVE
	  	  	       simFlag = checkForFind.checkForFindFunction(drones,targets,droneSearchRadius,simCounter,probabilisticRadius);//check for target find
		  	       simCounter++;//increment the sim counter
	  	    	 }
	  	    	 if(simFlag==0) {//if SimFlag shows sim is off (only occurs after find)
	  	    		//dronefleet final message; drone speed in mph is dronespeed in pixels times the ratio of the simcountperhour/100 (pixels in a mile is 100)
//	  	    		 System.out.println("Hours to find: "+ simCounter/simCountPerHour + ", Drone MPH: " + droneSpeed*(simCountPerHour/100));
	  	    		 
	  	    		 if(runType == 0) {//if single run
	  	    			endButton.setVisible(true);//shows the endButton (moves to statistics screen)
	  	    		 } else if (runType == 1) {//if multi run
	  	    			 Startup_Multi.numTrialsRun += 1;
	  	    			 sim.dispose();
	  	    			 Startup_Multi.startNextTrialFlag = true;
	  	    			 System.out.println("End trial");
	  	    			 return;
	  	    		 }
	  	    		 
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
		boolean faulted;
		int droneID;
		int resetCounter = 0;
	public drone(int x, int y, int size, Color color, boolean faulted, int droneID) {//drone constructor
		this.x = x;
	    this.y = y;
	    this.size = size;
	    this.color = color;
	    this.faulted = faulted; //starts all drones on construction as not faulted
	    this.droneID = droneID;
	}//end constructor
	public boolean isFaulted() {
		return faulted;
	}
	public void setFaulted(boolean faulted) {
		this.faulted = faulted;
	}
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
	public void setResetCounter(int resetCounter) {
		this.resetCounter = resetCounter;
	}
	public int getResetCounter() {
		return resetCounter;
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