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
import javax.swing.JPanel;
import javax.swing.Timer;
import java.math.*;
import java.net.URL;

public class DroneFleet extends JPanel {//create the DroneFleet clas and have it extend javaswing JPanel
  private static final long serialVersionUID = 1L;//boilerplate
 
//public variables
  //array lists for the drones and drone paths
      static ArrayList<drone> drones = new ArrayList<drone>();
      ArrayList<ArrayList<Integer[]>> dronePaths = new ArrayList<ArrayList<Integer[]>>();
  	  static ArrayList<target> targets = new ArrayList<target>();
  //settings variables
  	  public static boolean probabilisticRadius = true;
  //timer variables
  	  public static int simFlag=1, simCounter=0;
  //drone&main variables
  	  //number of drones starting on the screen
	  public static int numDrones = 40;
	  //starting positions
	  public static int startingx = (int)(1920/2.5), startingy = (int)(1080/2.5);
	  public static int directionStart = 1; //0 is nothing, 1 is random directions
	  //drone size
	  public static int dronesize = 30;
	  //drone search radius: how far drones will start to spot targets
	  public static int droneSearchRadius = 100;
  //sim variables
	  //simulation speed speed
	  public static int simspeed=1; //lower # is faster
  	  public static int currentFrame = 0;
  //target variables
	  public static int targetX = 200, targetY = 150, targetSize = 20;
  
//BEGIN FUNCTIONS
	  public static int getRandom(int max) {	  //Returns a random number between 0 and the input
		  return (int) (Math.random()*max);
	  }
	    
	  public void paintComponent(Graphics g) {	  //paintComponent paints drones and targets on the screen
		  super.paintComponent(g);
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
	      drone.setxSpeed(getRandom(30));//sets the drone's x speed magnitude (how far left/right)
	      drone.setySpeed(getRandom(30));//sets the drone's y speed magnitude (how far up or down)
	      if(getRandom(1)<0.5) {//uses the random function to decide the drone's y direction (up or down)
	    	  drone.yDirection = drone.yDirection * -1;
	      }
	      if(getRandom(1)<0.5) {//uses the random function to decide the drone's x direction (left or right)
	    	  drone.xDirection = drone.xDirection * -1;
	      }
	      //new position calculations
	      if((drone.xSpeed * drone.xDirection) + drone.x + drone.size>= getWidth()) {
	    	  drone.x = drone.x + (-1 * drone.xSpeed);
	      } else {
	    	  drone.x = drone.x + (drone.xDirection * drone.xSpeed);
	      }
	      if((drone.xSpeed * drone.xDirection) + drone.x - 10 <= 0) {
	    	  drone.x = drone.x + (1 * drone.xSpeed);
	      } else {
	    	  drone.x = drone.x + (drone.xDirection * drone.xSpeed);
	      }
	      if((drone.ySpeed * drone.yDirection) + drone.y + drone.size>= getHeight()) {
	    	  drone.y = drone.y + (-1 * drone.ySpeed);
	      } else {
	    	  drone.y = drone.y + (drone.yDirection * drone.ySpeed);
	      }
	      if((drone.ySpeed * drone.yDirection) + drone.y - 10 <= 0) {
	    	  drone.y = drone.y + (1 * drone.ySpeed);
	      } else {
	    	  drone.y = drone.y + (drone.yDirection * drone.ySpeed);
	      }
	      dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
	    }//end specific drone for loop
	    repaint();//repaints the screen with the new drones' location
	  }//end moveDrones
	
	  //main
	  public static void main(String[] args){
		Random random = new Random();	    //allows for random variables
	    JFrame sim = new JFrame();//makes Jframe
	    sim.setSize(1920, 1080);//sets the JFrame's size to 1980x1080 in case the fullscreen window does not work
	    sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the program to close on the X button (only viable for non fullscreen)
//		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();//Graphics Environment to make fullscreen devices
//		GraphicsDevice device = graphics.getDefaultScreenDevice();//gets the default screen on the device (for multi-monitor devices)
//  	  	device.setFullScreenWindow(sim);//set fullscreen using GraphicsEnvironmentDevice
  	  	DroneFleet simFrame = new DroneFleet();//makes a new frame based on this class (DroneFleet)
	    sim.add(simFrame);//make the frame
	    sim.setVisible(true);//set the panel to be visible
	  	//create/add the button to start the simulation and switch to that screen
	  	JButton endButton = new JButton("Open Statistics");//create the button to go to the next screen (statistics screen)
	  	endButton.setBounds(960-125/2,25,125,30);//set the bounds of the button to be the top middle of the sim screen
	  	simFrame.add(endButton);//adds the button to the sim frame
	  	endButton.setVisible(false);//sets the button to not be visible (becomes visible after sim has finished)
	  	endButton.addActionListener(new ActionListener() {//endButton "press" function
	  		public void actionPerformed(ActionEvent e) {//listener for the button to be pressed
	  			//System.out.println("End button pressed");//debug - write to console that button was pressed
	  			sim.setVisible(false);//set the sim panel to not be visible
	  			Statistics.main(null);//run the statistics class
	  		}
	  	});
	    
	    for (int i = 0; i < numDrones; i++) {//drone creation for loop
	    	int size = dronesize;//sets each drone's size to the global size
	    	int xDirection;//initiliazes xDirection (have to init here for drones arraylist)
	    	int yDirection;//initiliazes yDirection (have to init here for drones arraylist)
	    	if(directionStart==1) {//random direction start condition if statement
	    		xDirection = random.nextInt(2) * 2 - 1;//randonmizes the x direction
	    		yDirection = random.nextInt(2) * 2 - 1;//randomizes the y direction
	    	} else {//no direction start condition
	    		xDirection = 0;//no x direction to start
	    		yDirection = 0;//no y direction to start
	    	}//end direction checking
	      int xSpeed = getRandom(10);//sets the x speed (magnitude) randomly 0-10
	      int ySpeed = getRandom(10);//sets the y speed (magnitude) randomly 0-10
	      Color color = Color.black;//makes the drone's color be black (for drawing the component to the screen)
	      simFrame.drones.add(new drone(startingx, startingy, size, xDirection, yDirection, xSpeed, ySpeed, color));//sends the current drone to the drone arraylist
	      simFrame.dronePaths.add(new ArrayList<Integer[]>());//adds another slot to the dronepath arraylist for every drone created
	    }//end drone creation loop
	    simFrame.targets.add(new target(targetX, targetY, targetSize, Color.RED));	//creates the actual target and adds it to the targets arraylist

	  	Timer timer = new Timer(simspeed, new ActionListener() {  //MAIN SIM TIMER
	  	     public void actionPerformed(ActionEvent e) {
	  	    	 if(simFlag==1) {//if simFlag shows sim is on
	  	  	       simFrame.moveDrones();//move all the drones
	  	  	       simFlag = checkForFind.checkForFindFunction(drones,targets,droneSearchRadius,simCounter,probabilisticRadius);//check for target find
		  	       simCounter++;//increment the sim counter
	  	    	 }else if(simFlag==0) {//if SimFlag shows sim is off (only occurs after find)
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
		int xDirection;
		int yDirection;
		int xSpeed;
		int ySpeed;
		Color color;
	public drone(int x, int y, int size, int xDirection, int yDirection, int xSpeed, int ySpeed, Color color) {//drone constructor
		this.x = x;
	    this.y = y;
	    this.size = size;
	    this.xDirection = xDirection;
	    this.yDirection = yDirection;
	    this.xSpeed = xSpeed;
	    this.ySpeed = ySpeed;
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
	public int getxSpeed() {
		return xSpeed;
	}
	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	public int getySpeed() {
		return ySpeed;
	}
	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
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