import java.util.ArrayList;
import java.util.Random;

public class Movement {
	
	private static ArrayList<int[]> coordinates = new ArrayList<>();
	
//drone movement
	//DRONE MOVEMENT FUNCTION TEMPLATE:
	//X drone movement
//		public static void moveDronesX() {	  //moves drones like X
//			falsePositiveChance(); //checks for false positives by faulted drones
//			  for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
//				  drone drone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
//				  
//				  //write movement here
//				  
//			     DroneFleet.dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
//		      }//end specific drone for loop
//		  }//end moveDronesX
	//END DRONE MOVEMENT FUNCTION TEMPLATE
	
	//Returns a random number between 0 and the input
	public static int getRandom(int max) {
		return (int) (Math.random()*max);
	}
	
	//false positive chance on move function
	public static void falsePositiveChance() {
		for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
			  drone cdrone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
			  
			  if(cdrone.faulted == true && (Math.random()<DroneFleet.droneFalsePosChance)) { //if the current drone is faulted & a false positive is generated
				  DroneFleet.falsePositiveCount += 1; //adds to the false positive counter
				  System.out.println("Number of false positives:" + DroneFleet.falsePositiveCount);
				  verifyTargetFind(cdrone);
			  }
			  
		}
	}
	
	//verifyTargetFind moves the closest drone to the one that "found" a target to verify its find (only works for grid search currently)
	public static void verifyTargetFind(drone drone) {//"drone" is the one that "found" a target
		//find the closet drone via droneID (they should always be close to each other)
		int closestDroneID = 0;
		if(drone.droneID!=0) {//as long as the drone isnt drone 0, find the drone before it
			closestDroneID = drone.droneID - 1;
		} else {//otherwise find the drone after it
			closestDroneID = drone.droneID + 1;
		}
	  	int[] newCoordinates = coordinates.get(drone.droneID); //get the coordinates of drone that "found" the target 
	  	System.out.println("Drone #" + closestDroneID + " moving to "+ newCoordinates[0] +", "+ newCoordinates[1]);
		coordinates.set(closestDroneID, newCoordinates); //set the closest drone's target coordinates to coordinates of the drone that "found" the target
	}
	
		
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
	
	//grid drone movement
	public static int[] gridPair(int num, int ratioWidth, int ratioHeight) {//this function finds the number of rows and columns based on numDrones and the aspect ratio of the screen
	    int[] pair = new int[2];//create an array for storing the numRows and numColumns
	    double targetRatio = (double)ratioWidth / ratioHeight;//create the target ratio (get close to here) for the multiples to try to achieve
	    double closestRatio = Double.MAX_VALUE;//create a value for the current closest ratio to the target ratio
	    for (int i = 1; i <= num; i++) {//run through every combination of multiples and find the closest to the aspect ratio
	        if (num % i == 0) {//checks if the number divided by the current multiple test number is 0
	            int j = num / i;//sets j to the number divided by the current multiple to find the other multiple
	            double currentRatio = (double) i / j;//find the aspect ratio of the current set of multiples
	            if (Math.abs(currentRatio - targetRatio) < Math.abs(closestRatio - targetRatio)) {//check if the current multiples are closer to the target ratio than the closest so far
	                closestRatio = currentRatio;//set the closest ratio to be the current ratio
	                pair[0] = i;//set the number of rows to the 0th position
	                pair[1] = j;//set the number of columns to the 1st position
	            }//end new closest ratio checker
	        }//end checking through every multiple 
	    }//end checking through every number
	    
	    if (pair[1] == num) {//If the numDrones is something unfactorable like 71 (i.e. the number of columns is numDrones)
	    	//find the closest perfect square (multiple of 2^x) and make that many rows/columns
	        pair[0] = (int) (Math.sqrt(num));
	        pair[1] = (int) (Math.sqrt(num));
	    }
	    
	    return pair;//return the number of rows/columns in the int array they are stored in
	}//end gridPair function
	
	public static void divideScreenIntoGrid(int width, int height, int numDrones){//divides the area into a grid
		
//		ArrayList<int[]> coordinates = new ArrayList<>();//create a new arraylist of "coordinates"
		int[] gridPair = new int[2];//create a new int array for the number of rows/columns
		gridPair = gridPair(DroneFleet.numDrones, DroneFleet.screenY, DroneFleet.screenX);//find the number of rows/columns with the gridPair function
        int rows = gridPair[0];//sets "rows" to the 0th position of gridPairs
        int cols = gridPair[1];//sets "columns" to the 1st position of gridPairs
        int cellWidth = width / cols;//find the cellWidth by dividing the screen width by the number of columns
        int cellHeight = height / rows;//find the cellHeight by dividing the screen height by the number of rows
        int offsetX = (width - cellWidth * cols) / 2;//find the X offset (center) of the boxes by math
        int offsetY = (height - cellHeight * rows) / 2;//find the Y offest (center of the boxes by math
        //for every box:
        for (int i = 0; i < rows; i++) {//for every row
            for (int j = 0; j < cols; j++) {//for every column
                int x = offsetX + j * cellWidth + cellWidth / 2;//find the center x coordinate of every box using math
                int y = offsetY + i * cellHeight + cellHeight / 2;//find the center y coordinate of every box using math
                int[] coordinate = {x, y};//make a coordinate int array with the previously found center coordinates
                coordinates.add(coordinate);//add the coordinate int array to the coordinates arraylist
            }//end every column
        }//end every row
        //If there are more drones than coordinates, fill that many 0,0 coordinates
        if(numDrones>coordinates.size()) {
        	int numCoordFill = numDrones-coordinates.size();
        	int[] zerocoordinate = {0,0};
        	for(int i = 0; i < numCoordFill; i++) {
        		coordinates.add(zerocoordinate);
        	}
        }
        //coordinates is a file wide variable now, no need to return anything
	}//end divideScreenIntoGrid function
	
	public static void moveDronesGrid() {	  //maps the # of drones to a grid, has them search that grid
		
		boolean ODO_divideScreenIntoGrid = false; // ODO (only do once) check for dividing up the screen (we only need to divide it up once, not every time)
		if(!ODO_divideScreenIntoGrid) {// if the ODO is false (not done yet)
		divideScreenIntoGrid(DroneFleet.screenX, DroneFleet.screenY, DroneFleet.numDrones); //divide the screen up into the coordinate grid
		ODO_divideScreenIntoGrid = true;
		}
		
		falsePositiveChance(); //checks for false positives by faulted drones and sends the closest drone to check if so (changes the closest drones destination)
		  for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
			  drone drone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
			  	int[] coordinate = coordinates.get(i);//set the int array coordinate to the current drone's center box coordinates from the arraylist coordinates
		        int droneX = drone.getX();//get the current done's X position
		        int droneY = drone.getY();//get the current drone's Y position
		        int coordX = coordinate[0];//set the coordX variable to the previously found drone's box's center X location
		        int coordY = coordinate[1];//set the coordY variable to the previously found drone's box's center Y location
		        double angle = Math.atan2(coordY - droneY, coordX - droneX);//finds the angle neccessary to go towards the center location
		        double dx = DroneFleet.droneSpeed * Math.cos(angle);//finds the movement change needed in the X direction
		        double dy = DroneFleet.droneSpeed * Math.sin(angle);//finds the movement change needed in the Y direction
		        drone.x = (droneX + (int)dx);//move the drone's X in the direction of it's box's center X coordinate
		        drone.y = (droneY + (int)dy);//move the drone's Y in the direction of it's box's center Y coordinate
		     DroneFleet.dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
	      }//end specific drone for loop
	  }//end moveDronesGrid
	
//target movement -----
	//random target movement
	public static void moveTargetsRandom() {//moves the targets 
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
	            }//end checking for new pos inside area
			  }//end while loop
		        ctarget.x = newX; //Move the target to the new x position after breaking out of the while loop
		        ctarget.y = newY; //Move the target to the new y position after breaking out of the while loop
		  }//end target for loop
		  
	  }//end target movement
	
}
