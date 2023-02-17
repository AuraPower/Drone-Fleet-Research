  import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.math.*;
import java.net.URL;

public class DroneFleet extends JPanel {
  private static final long serialVersionUID = 1L;
  
   //array lists for the drones and drone paths
  static ArrayList<drone> drones = new ArrayList<drone>();
  ArrayList<ArrayList<Integer[]>> dronePaths = new ArrayList<ArrayList<Integer[]>>();
  static ArrayList<target> targets = new ArrayList<target>();
  
  
  //public variables
  //timer
  	  public static int simFlag=1;
	  public static int simCounter=0;
  
  //initialization variables
  //drone&main variables
  	  //number of drones starting on the screen
	  public static int numDrones = 400;
	  //starting positions
	  public static int startingx = 900;
	  public static int startingy = 500;
	  public static int directionStart = 1; //0 is nothing, 1 is random directions
	  //drone size
	  public static int dronesize = 30;
	  //drone search radius: how far drones will spot targets
	  public static int droneSearchRadius = 15;
	  //simulation speed speed
	  public static int simspeed=1; //lower is faster
  //target variables
	  public static int targetX = 600;
	  public static int targetY = 420;
	  public static int targetSize = 20;
  
	  //BEGIN FUNCTIONS
	  
	  //Returns a random number between 0 and the input
	  public static int getRandom(int max) {
		  return (int) (Math.random()*max);
	  }
	  
	  
	  //paintComponent paints all the drones in the array list "drones"	  
	  public void paintComponent(Graphics g) {
		  super.paintComponent(g);
		  for (int i = 0; i < drones.size(); i++) {
			  drone cdrone = drones.get(i);
			  drawDronePath(g, i);
			  g.setColor(cdrone.color);
			  g.fillOval(cdrone.x, cdrone.y, cdrone.size, cdrone.size);
			  
		  }	
		  //draw targets (people, etc)
		  for (int i = 0; i<targets.size(); i++) {
			  target ctarget = targets.get(i);
			  g.setColor(ctarget.color);
			  g.fillOval(ctarget.getX(), ctarget.getY(), ctarget.getSize(), ctarget.getSize());
		  }
		  
	  }
  
	  //drawDronePath draws the array list "dronePath" based on the points provided.
	  //every time a drone performs the function move it records the "path" (i.e. old position and new position)
	  private void drawDronePath(Graphics g, int droneIndex) {
		    
		    ArrayList<Integer[]> dronePath = dronePaths.get(droneIndex);
		    for (int i = 0; i < dronePath.size() - 1; i++) {
		      Integer[] point1 = dronePath.get(i);
		      Integer[] point2 = dronePath.get(i + 1);
		      g.setColor(Color.GRAY);
		      g.drawLine(point1[0], point1[1], point2[0], point2[1]);
		    }
		  }
	  
	  //move moves every drone at the same time
	  private void moveDrones() {
		  for (int i = 0; i < drones.size(); i++) {
			  drone drone = drones.get(i);
	      drone.setxSpeed(getRandom(10));
	      drone.setySpeed(getRandom(10));
	      if(getRandom(1)<0.5) {
	    	  drone.yDirection = drone.yDirection * -1;
	      }
	      if(getRandom(1)<0.5) {
	    	  drone.xDirection = drone.xDirection * -1;
	      }
	      
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
	      
	      dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});
	    }
	    repaint();
	  }
	
	  //main
	  public static void main(String[] args) {
		  //makes screen & sets size, allows it to close on X button in corner
	    JFrame frame = new JFrame();
	    frame.setSize(1980, 1080);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //makes a new drone fleet
	    DroneFleet mover = new DroneFleet();
	    //allows for random variables
	    Random random = new Random();
	    //drone creation
	    for (int i = 0; i < numDrones; i++) {
	      int size = dronesize;
	      //sets initial direction
	      int xDirection;
    	  int yDirection;
	      if(directionStart==1) {
	      xDirection = random.nextInt(2) * 2 - 1;
	      yDirection = random.nextInt(2) * 2 - 1;
	      } else {
	    	  xDirection = 0;
	    	  yDirection = 0;
	      }
	      int xSpeed = getRandom(10);
	      int ySpeed = getRandom(10);
	      Color color = Color.black;
	      mover.drones.add(new drone(startingx, startingy, size, xDirection, yDirection, xSpeed, ySpeed, color));
	      mover.dronePaths.add(new ArrayList<Integer[]>());
	    }
	    
	    //create the target (target)
	    
	    mover.targets.add(new target(targetX, targetY, targetSize, Color.RED));
	    
	    //make the frame
	    frame.add(mover);
	    frame.setVisible(true);
	    
//	    checkForFind finder = new checkForFind();
	    Timer timer = new Timer(simspeed, new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  //if 
	    	  if(simFlag==1) {
	  	        mover.moveDrones();
	  	        simFlag = checkForFind.checkForFindFunction(drones,targets,droneSearchRadius,simCounter);
	    	  }
	        simCounter++;
	      }
	    });
	    timer.start();
	  }
	} //JPanel end
	
	class drone {
			int x;
			int y;
			int size;
			int xDirection;
			int yDirection;
			int xSpeed;
			int ySpeed;
			Color color;
	  
	  
	  public drone(int x, int y, int size, int xDirection, int yDirection, int xSpeed, int ySpeed, Color color) {
	    this.x = x;
	    this.y = y;
	    this.size = size;
	    this.xDirection = xDirection;
	    this.yDirection = yDirection;
	    this.xSpeed = xSpeed;
	    this.ySpeed = ySpeed;
	    this.color = color;
	  }
	  
	  
	  //GETTERS AND SETTERS for drone class
	  
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
	
	
	//target class
	class target{
			int x;
			int y;
			int size;
			Color color;
		
		//constructor
		public target(int x, int y, int size, Color color) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.color = color;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getSize() {
			return size;
		}
		
		
		
		
		
		
	}

