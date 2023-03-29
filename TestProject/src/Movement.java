import java.util.Random;

public class Movement {
//drone movement
	//random drone movement
	public static void moveDronesRandom() {	  //move moves every drone at the same time (1 call = every drone moves)
		  for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
			  drone drone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
			  
			  boolean insideArea=false;
			  int newX = 0;
		      int newY = 0;
		      
			  while(!insideArea) {//while the drone's new position is not within the screen, run this while loop
			  	Random random = new Random();//create a new random number
	            double direction = random.nextDouble() * 2 * Math.PI;//Generate a random direction between 0 and 2π radians
	            int xVelocity = (int) Math.round(DroneFleet.droneSpeed * Math.cos(direction));//Calculate the x component of the velocity based on the direction
	            int yVelocity = (int) Math.round(DroneFleet.droneSpeed * Math.sin(direction));//Calculate the y component of the velocity based on the direction
	            newX = drone.x + xVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            newY = drone.y + yVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            
	            if (newX >= 0 && newX < 1500 && newY >= 0 && newY < 800) {// Check if the new position is inside the screen
	                insideArea = true;//if the new position is in the area, break out of the while loop
	            }//end if
			  }//end while loop
		        drone.x = newX; //Move the drone to the new x position after breaking out of the while loop
		        drone.y = newY; //Move the drone to the new y position after breaking out of the while loop
		        DroneFleet.dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
	      }//end specific drone for loop
	  }//end moveDronesRandom
	
//target movement
	public static void moveTargets() {//moves the targets 
		  for (int i = 0; i<DroneFleet.targets.size(); i++) {//for every target
			  target ctarget = DroneFleet.targets.get(i);//set the current target to the target currently being worked on
			  boolean insideArea=false;
			  int newX = 0;
		      int newY = 0;
		      
			  while(!insideArea) {//while the target's new position is not within the screen, run this while loop
			  	Random random = new Random();//create a new randomness "class"
	            double direction = random.nextDouble() * 2 * Math.PI;//Generate a random direction between 0 and 2π radians
	            int xVelocity = (int) Math.round(DroneFleet.targetSpeed * Math.cos(direction));//Calculate the x component of the velocity based on the direction
	            int yVelocity = (int) Math.round(DroneFleet.targetSpeed * Math.sin(direction));//Calculate the y component of the velocity based on the direction
	            newX = ctarget.x + xVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            newY = ctarget.y + yVelocity;//Calculate the new position based on the old position and the random velocity just generated
	            
	            if (newX >= 0 && newX < 1500 && newY >= 0 && newY < 800) {// Check if the new position is inside the screen
	                insideArea = true;//if the new position is in the area, break out of the while loop
	            }//end if
			  }//end while loop
		        ctarget.x = newX; //Move the target to the new x position after breaking out of the while loop
		        ctarget.y = newY; //Move the target to the new y position after breaking out of the while loop
		  }//end painting targets
		  
	  }//end targets
	
}
