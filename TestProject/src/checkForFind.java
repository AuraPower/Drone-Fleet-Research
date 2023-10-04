import java.util.ArrayList;

public class checkForFind {

	public static int numFalseNegatives = 0;//counter for succesful finds made by faulted drones	
	
	//Returns a random number between 0 and the input
	public static int getRandom(int max) {
		return (int) (Math.random()*max);
	}
	
	  static int checkForFindFunction(ArrayList<drone> drones, ArrayList<target> targets, int droneSearchRadius, int simCounter, boolean probabilisticRadius) {
		  //runs through every drone
		  for (int i = 0; i < drones.size(); i++) {
			  drone cdrone = drones.get(i);
			  //runs through every target, per drone
			  for(int j =0; j< targets.size(); j++) {
				  target ctarget = targets.get(j);
				  int xdistance = Math.abs(cdrone.getX() - ctarget.getX());
				  int ydistance = Math.abs(cdrone.getY() - ctarget.getY());
				  double actualdistance = Math.sqrt( (Math.pow(xdistance, 2)) + (Math.pow(ydistance,  2)) );
				  //if the distances between the current drone and current targets X/Y are less than the search radius, stops simulation and outputs found statement
				  if( (actualdistance < droneSearchRadius) ) {
					    //if probabilistic finding is turned off
				  		if(probabilisticRadius==false) {
					  	System.out.println("Found in: "+simCounter+" cycles.");
				  		return 0;
				  		//if probabilistic finding is turned on
				  		} else {
				  			//makes a random number on the log curve of -1.2log(x) (biased towards 1)
				  			double randomNum = Math.random();
				  			
				  			//makes distCheck the percentage (in decimals) towards the target the drone is from the max finding Radius
				  			double distCheck = 1-(actualdistance/droneSearchRadius);
				  			double distCheckLog = -0.875 * Math.log(((distCheck*-1)+1));
				  			distCheckLog = distCheckLog/2.302585094;
				  			if(actualdistance<droneSearchRadius) {//debug, if the drone has a chance of find (if target is within radius) shows debug log
//				  				System.out.println("Distance < 100, percent chance to find: " + (100*distCheck));
				  				System.out.println("Distance: " + actualdistance +", DistCheck:" + distCheck + ", Log'd percent find: " + distCheckLog + ", random Num: " + randomNum);
				  			}
				  			//if the Distance percentage is less than the weighted random number, and its not a faulted drone, it is a successful find
				  			if((distCheckLog>randomNum) && (cdrone.isFaulted()!=true)) {
//				  				System.out.println("actual/search: " + distCheck + ", random Exp: " + randomExp);
//				  				System.out.println("Distance Found At: " + actualdistance);
//				  				System.out.println("Distance: " + actualdistance +", DistCheck:" + distCheck + ", Log'd percent find: " + distCheckLog + ", random Num: " + randomNum);
				  				System.out.println("Found in: "+simCounter+" cycles.");
//						  		return 0;
				  			} else if ((distCheckLog>randomNum) && (cdrone.isFaulted()!=false)){ //sucessful find, but drone is faulted
				  				numFalseNegatives = numFalseNegatives + 1;
				  				System.out.println("Successful find, but drone was faulted, total false negatives: " + numFalseNegatives);
				  			}
				  			
				  			
				  		}
				  		
				  		
				  		
				  		
				  	}
				  
			  }
			  	
			  
		  }
		
		  return 1;
	  }
	
	
	

}
