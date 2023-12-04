import java.util.ArrayList;
import java.util.Random;

public class Movement {
	
	static ArrayList<int[]> coordinates = new ArrayList<>();
	static ArrayList<int[]> coordinates_fluid = new ArrayList<>();
	static boolean ODO_divideScreenIntoGrid = false; // ODO (only do once) check for dividing up the screen (we only need to divide it up once, not every time)
	static boolean ODO_initializeCoordinatesRandom = false; // ODO for initializing the random coordinates
	static int cellWidth = 0;
	static int cellHeight = 0;
	
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
			  Drone cdrone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
			  
			  if(cdrone.faulted == true && (Math.random()<DroneFleet.droneFalsePosChance)) { //if the current drone is faulted & a false positive is generated
				  DroneFleet.falsePositiveCount += 1; //adds to the false positive counter
				  Startup_Multi.numFalsePositives.set(Startup_Multi.numTrialsRun-1, Startup_Multi.numFalsePositives.get(Startup_Multi.numTrialsRun-1)+1);
//				  System.out.println("Number of false positives:" + DroneFleet.falsePositiveCount);
				  if(Startup_Multi.droneMovementSelectedOption == "Grid") {
					  verifyTargetFind_Grid(cdrone);
				  }else if(Startup_Multi.droneMovementSelectedOption == "Random") {
					  Movement.verifyTargetFind_Random(cdrone);
				  }
				  
			  }
			  
		}
	}
	
	//verifyTargetFind moves the closest drone to the one that "found" a target to verify its find (only works for grid search currently)
	public static void verifyTargetFind_Grid(Drone drone) {//"drone" is the one that "found" a target
//		int positionFindOccurredAtX = drone.x;
//		int positionFindOccurredAtY = drone.y;
		int[] coordsFindOccurredAt = {drone.x, drone.y};
		//find the closet drone via droneID (they should always be close to each other)
		int closestDroneID = 0;
		if(drone.droneID!=0) {//as long as the drone isnt drone 0, find the drone before it
			closestDroneID = drone.droneID - 1;
		} else {//otherwise find the drone after it
			closestDroneID = drone.droneID + 1;
		}
		Drone cdrone = DroneFleet.drones.get(closestDroneID); // cdrone is the closest drone
//	  	int[] newCoordinates = coordinates.get(drone.droneID); //get the coordinates of drone that "found" the target - DEPRECIATED - we use the point at which the "find occured"
//	  	System.out.println("Drone #" + closestDroneID + "currently at: " + tempCoords[0] +"," + tempCoords[1]+ ", moving to "+ newCoordinates[0] +", "+ newCoordinates[1]);
	  	cdrone.setHasReachedGrid(false);
		coordinates_fluid.set(closestDroneID, coordsFindOccurredAt); //set the closest drone's target coordinates to coordinates of the drone that "found" the target
		//find the drone that has the closestDroneID (the on thats moving to check a positive) and start the countdown at x
		
		cdrone.setResetCounter(50);
//		System.out.println("Drone # " +closestDroneID + "'s reset counter = " + cdrone.resetCounter);
		
	}
	
	public static void verifyTargetFind_Random(Drone drone) {//"drone" is the one that "found" a target
		int[] coordsFindOccurredAt = {drone.x, drone.y};
		//find a random drone via droneID
		int closestDroneID = 0;
		if(drone.droneID!=0) {//as long as the drone isnt drone 0, find the drone before it
			closestDroneID = drone.droneID - 1;
		} else {//otherwise find the drone after it
			closestDroneID = drone.droneID + 1;
		}
		Drone cdrone = DroneFleet.drones.get(closestDroneID); // cdrone is the randomly chosen drone
//	  	int[] newCoordinates = coordinates.get(drone.droneID); //get the coordinates of drone that "found" the target - DEPRECIATED - we use the point at which the "find occured"
//	  	System.out.println("Drone #" + closestDroneID + "currently at: " + tempCoords[0] +"," + tempCoords[1]+ ", moving to "+ newCoordinates[0] +", "+ newCoordinates[1]);
	  	cdrone.setHasReachedGrid(false);
		coordinates_fluid.set(closestDroneID, coordsFindOccurredAt); //set the randomly chosen drone's target coordinates to coordinates of the drone that "found" the target
		//find the drone that has the closestDroneID (the on thats moving to check a positive) and start the countdown at x
		
		cdrone.setResetCounter(8);
//		System.out.println("Drone # " +closestDroneID + "'s reset counter = " + cdrone.resetCounter);
		
	}
		
	//random drone movement
//	public static void moveDronesRandom() {	  //move moves every drone at the same time (1 call = every drone moves)
//		  for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
//			  drone drone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
//			  
//			  boolean insideArea=false;
//			  int newX = 0;
//		      int newY = 0;
//		      
//			  while(!insideArea) {//while the drone's new position is not within the screen, run this while loop
//			  	Random random = new Random();//create a new random number
//	            double direction = random.nextDouble() * 2 * Math.PI;//Generate a random direction between 0 and 2π radians
//	            int xVelocity = (int) Math.round(DroneFleet.droneSpeed * Math.cos(direction));//Calculate the x component of the velocity based on the direction
//	            int yVelocity = (int) Math.round(DroneFleet.droneSpeed * Math.sin(direction));//Calculate the y component of the velocity based on the direction
//	            newX = drone.x + xVelocity;//Calculate the new position based on the old position and the random velocity just generated
//	            newY = drone.y + yVelocity;//Calculate the new position based on the old position and the random velocity just generated
//	            
//	            if (newX >= 0 && newX < 1500 && newY >= 0 && newY < 800) {// Check if the new position is inside the screen
//	                insideArea = true;//if the new position is in the area, break out of the while loop
//	            }//end if
//			  }//end while loop
//		        drone.x = newX; //Move the drone to the new x position after breaking out of the while loop
//		        drone.y = newY; //Move the drone to the new y position after breaking out of the while loop
//		        DroneFleet.dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
//	      }//end specific drone for loop
//	  }//end moveDronesRandom
	//random drone movement
	
	public static void moveDronesRandom() {
		if(!ODO_initializeCoordinatesRandom) {// only do once flag
//			System.out.println("INIT RANDOM");
			initializeCoordinatesRandom();
			coordinates_fluid = coordinates;//copy the grid coordinates to the changing coordinate base, coordinates_fluid
			ODO_initializeCoordinatesRandom = true;
			
			
			}
		moveDronesRandomMove();
	}
	
	public static void initializeCoordinatesRandom() {
		coordinates.clear();
		coordinates_fluid.clear();
//		System.out.println(DroneFleet.drones.size());
		for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
			Drone drone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
			int[] current_coord = {drone.x, drone.y};
			int[] test = {0,0};
//			System.out.println("Adding coordinate");
			coordinates.add(test);
			drone.setHasReachedGrid(true);
			
		}
//		System.out.println("INIT RANDOM COORDS COMPLETE");
//		System.out.println(coordinates);
	}

	
	public static void moveDronesRandomMove() {	  //move moves every drone at the same time (1 call = every drone moves)
		falsePositiveChance();
		  for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
			  Drone drone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected drone
			  
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
			  
			  //TODO: do this only if the drone is not verifying
			  if(drone.getResetCounter() >= 2) {
		        	drone.setResetCounter(drone.getResetCounter() - 1);
//		        	System.out.println("-1");
		        } else if (drone.getResetCounter() == 1) {
		        	drone.setResetCounter(drone.getResetCounter() - 1);
		        	
		        } else if (drone.getResetCounter() == 0) {
		        	//do nothing
		        	int[] coord_temp = {newX,newY};
					coordinates_fluid.set(i, coord_temp);
					drone.setHasReachedGrid(true);
		        }
			  
			  
			  
			  	int[] coordinate = coordinates_fluid.get(i);//set the int array coordinate to the current drone's destination from the arraylist coordinates_fluid
			  	int droneX = drone.getX();//get the current done's X position
		        int droneY = drone.getY();//get the current drone's Y position
		        int coordX = coordinate[0];//set the coordX variable to the random coordinate
		        int coordY = coordinate[1];//set the coordY variable to the random coordinate
		        double angle = Math.atan2(coordY - droneY, coordX - droneX);//finds the angle neccessary to go towards the random coordinate
		        double dx = DroneFleet.droneSpeed * Math.cos(angle);//finds the movement change needed in the X direction
		        double dy = DroneFleet.droneSpeed * Math.sin(angle);//finds the movement change needed in the Y direction
		        drone.x = (droneX + (int)dx);//move the drone's X in the direction of it's random coordinate
		        drone.y = (droneY + (int)dy);//move the drone's Y in the direction of it's random coordinate
		        DroneFleet.drones.get(i).x = droneX + (int)dx;
		        DroneFleet.drones.get(i).y = droneY + (int)dy;
			  
//		        drone.x = newX; //Move the drone to the new x position after breaking out of the while loop
//		        drone.y = newY; //Move the drone to the new y position after breaking out of the while loop
		        DroneFleet.dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
	      }//end specific drone for loop
	  }//end moveDronesRandom
	
	public static void moveSingularDroneToCornersInsideArea(Drone drone, int boxWidth, int boxHeight) {
		double dronespeed = DroneFleet.droneSpeed;
	    int newX = drone.targetPositions[drone.currentTargetIndex];
	    int newY = drone.targetPositions[drone.currentTargetIndex + 1];

	    // Calculate the distance to the target position
	    double deltaX = newX - drone.x;
	    double deltaY = newY - drone.y;
	    double distanceToTarget = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

	    // Calculate the movement in the x and y direction based on speed
	    double moveX = (deltaX / distanceToTarget) * dronespeed;
	    double moveY = (deltaY / distanceToTarget) * dronespeed;

	    // Update the drone's position
	    if (distanceToTarget <= dronespeed) {
	        // If the distance is less than the speed, move directly to the target
	        drone.x = newX;
	        drone.y = newY;
	        
	        // Increment target index only when the drone reaches the target
	        drone.currentTargetIndex += 2;
	        if (drone.currentTargetIndex >= drone.targetPositions.length) {
	            drone.currentTargetIndex = 0; // Start over when all positions have been visited
	        }
	    } else {
	        // Move incrementally towards the target
	        drone.x += moveX;
	        drone.y += moveY;
	    }
	}
	
	public static void initmoveSingularDroneToCornersInsideAreaPositions(int boxWidth, int boxHeight) {
		for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
			Drone  drone = DroneFleet.drones.get(i);
			int coordinates[] = coordinates_fluid.get(i);
		
			drone.targetPositions = new int[] {coordinates[0], coordinates[1], 
                    coordinates[0] - boxWidth / 2, coordinates[1] - boxHeight / 2, 
                    coordinates[0] + boxWidth / 2, coordinates[1] - boxHeight / 2, 
                    coordinates[0] - boxWidth / 2, coordinates[1] + boxHeight / 2, 
                    coordinates[0] + boxWidth / 2, coordinates[1] + boxHeight / 2};

			drone.currentTargetIndex = 0; // Start at the center positio
		}
//		System.out.println("created target positions");
	}
	
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
//		coordinates.clear(); //need to clear the coordinates every time we need to recalculate
		int[] gridPair = new int[2];//create a new int array for the number of rows/columns
		gridPair = gridPair(DroneFleet.numDrones, DroneFleet.screenY, DroneFleet.screenX);//find the number of rows/columns with the gridPair function
        int rows = gridPair[0];//sets "rows" to the 0th position of gridPairs
        int cols = gridPair[1];//sets "columns" to the 1st position of gridPairs
        cellWidth = width / cols;//find the cellWidth by dividing the screen width by the number of columns
        cellHeight = height / rows;//find the cellHeight by dividing the screen height by the number of rows
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
	
	public static int[] divideScreenIntoGrid_singular(int width, int height, int numDrones, int droneID){//divides the area into a grid for a singular point
		ArrayList<int[]> coordinates_singular = new ArrayList<>();//create a new arraylist of "coordinates"
		int[] gridPair = new int[2];//create a new int array for the number of rows/columns
		gridPair = gridPair(DroneFleet.numDrones, DroneFleet.screenY, DroneFleet.screenX);//find the number of rows/columns with the gridPair function
        int rows = gridPair[0];//sets "rows" to the 0th position of gridPairs
        int cols = gridPair[1];//sets "columns" to the 1st position of gridPairs
        cellWidth = width / cols;//find the cellWidth by dividing the screen width by the number of columns
        cellHeight = height / rows;//find the cellHeight by dividing the screen height by the number of rows
        int offsetX = (width - cellWidth * cols) / 2;//find the X offset (center) of the boxes by math
        int offsetY = (height - cellHeight * rows) / 2;//find the Y offest (center of the boxes by math
        //for every box:
        for (int i = 0; i < rows; i++) {//for every row
            for (int j = 0; j < cols; j++) {//for every column
                int x = offsetX + j * cellWidth + cellWidth / 2;//find the center x coordinate of every box using math
                int y = offsetY + i * cellHeight + cellHeight / 2;//find the center y coordinate of every box using math
                int[] coordinate = {x, y};//make a coordinate int array with the previously found center coordinates
                coordinates_singular.add(coordinate);//add the coordinate int array to the coordinates arraylist
            }//end every column
        }//end every row
        //If there are more drones than coordinates, fill that many 0,0 coordinates
        if(numDrones>coordinates.size()) {
        	int numCoordFill = numDrones-coordinates.size();
        	int[] zerocoordinate = {0,0};
        	for(int i = 0; i < numCoordFill; i++) {
        		coordinates_singular.add(zerocoordinate);
        	}
        }
        int[] coordinates_singular_return = coordinates_singular.get(droneID); //return only the specific droneID coordinates we requested
        return coordinates_singular_return;
        
	}//end divideScreenIntoGrid function
	
	public static void moveDronesGrid() {	  //maps the # of drones to a grid, has them search that grid
		
		if(!ODO_divideScreenIntoGrid) {// only do once flag
		divideScreenIntoGrid(DroneFleet.screenX, DroneFleet.screenY, DroneFleet.numDrones); //divide the screen up into the coordinate grid
		coordinates_fluid = coordinates;//copy the grid coordinates to the changing coordinate base, coordinates_fluid
		ODO_divideScreenIntoGrid = true;
		
//		System.out.println("Dividing screen");
		}
		
		if (DroneFleet.drones.size() > 1){
			Drone testdrone = DroneFleet.drones.get(1);
			if(testdrone.targetPositions != null) {
				
			}else {
				initmoveSingularDroneToCornersInsideAreaPositions(cellWidth, cellHeight);
			}
		}
		
		
		falsePositiveChance(); //checks for false positives by faulted drones and sends the closest drone to check if so (changes the closest drones destination)
		for (int i = 0; i < DroneFleet.drones.size(); i++) {//specific drone for loop (iterates for every drone)
			  Drone drone = DroneFleet.drones.get(i);//makes the temp drone "drone" have the same attributes as the currently selected 
			  int[] coordinate = coordinates_fluid.get(i);//set the int array coordinate to the current drone's destination from the arraylist coordinates_fluid
		        
			  if(drone.getHasReachedGrid() == false) {//if the drone has not reached the center point of the grid
			  	int droneX = drone.getX();//get the current done's X position
		        int droneY = drone.getY();//get the current drone's Y position
		        int coordX = coordinate[0];//set the coordX variable to the previously found drone's box's center X location
		        int coordY = coordinate[1];//set the coordY variable to the previously found drone's box's center Y location
		        double angle = Math.atan2(coordY - droneY, coordX - droneX);//finds the angle neccessary to go towards the center location
		        double dx = DroneFleet.droneSpeed * Math.cos(angle);//finds the movement change needed in the X direction
		        double dy = DroneFleet.droneSpeed * Math.sin(angle);//finds the movement change needed in the Y direction
		        drone.x = (droneX + (int)dx);//move the drone's X in the direction of it's box's center X coordinate
		        drone.y = (droneY + (int)dy);//move the drone's Y in the direction of it's box's center Y coordinate
			  }else if(drone.getHasReachedGrid() == true) {//if the drone has already reached the center point of the grid
				  //SEARCH THE SMALL GRID?
				  moveSingularDroneToCornersInsideArea(drone, cellWidth, cellHeight);
			  }
		        DroneFleet.dronePaths.get(i).add(new Integer[] {drone.x + drone.getSize()/2, drone.y + drone.getSize()/2});//checks old/new position to draw the position paths
			  
		        //hasReachedGrid check - if the drone has reached its center point of its grid (or wihtin a few pixels), update the flag
			        double distanceThreshold = 5.0; // Adjust this threshold as needed
	
			        double distanceToTarget = Math.sqrt(Math.pow(drone.x - coordinate[0], 2) + Math.pow(drone.y - coordinate[1], 2));
	
			        if (distanceToTarget <= distanceThreshold) {
			            drone.setHasReachedGrid(true);
			        }
		        
		        //decrement the reset count if its running (2-50), reset its fluid coordinate destination if 1, and do nothing for 0
		        if(drone.getResetCounter() >= 2) {
		        	drone.setResetCounter(drone.getResetCounter() - 1);
//		        	System.out.println("-1");
		        } else if (drone.getResetCounter() == 1) {
		        	drone.setResetCounter(drone.getResetCounter() - 1);
		        	int[] coordinates_temp = divideScreenIntoGrid_singular(DroneFleet.screenX, DroneFleet.screenY, DroneFleet.numDrones,i);
		        	
//		        	System.out.println("reset? setting drone " + i + " to: " + coordinates_temp[0] + "," + coordinates_temp[1]);
		        	
		        	coordinates_fluid.set(i, coordinates_temp);
		        } else if (drone.getResetCounter() == 0) {
		        	//do nothing
		        }
		
		}//end specific drone for loop
	  }//end moveDronesGrid
	
//target movement -----
	//random target movement
	public static void moveTargetsRandom() {//moves the targets 
		  //for (int i = 0; i<DroneFleet.targets.size(); i++) {//for every target XXX changed here
			  //Target ctarget = DroneFleet.targets.get(i);//set the current target to the target currently being worked on
			  Target ctarget = DroneFleet.targets; // XXX Changed here
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
	
//} XXX Changed here
