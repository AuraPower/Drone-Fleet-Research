import java.util.ArrayList;

public class checkForFind {

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
				  if((actualdistance < droneSearchRadius )) {
					    //if probabilistic finding is turned off
				  		if(probabilisticRadius==false) {
					  	System.out.println("Found in: "+simCounter+" cycles.");
				  		return 0;
				  		//if probabilistic finding is turned on
				  		} else {
				  			//makes a random number on the exponential curve of x^10 (heavily biased towards 1)
				  			double randomExp = Math.pow(Math.random(), 10);
				  			//makes distCheck the percentage (in decimals) towards the target the drone is from the max finding Radius
				  			double distCheck = actualdistance/droneSearchRadius;
				  			//if the Distance percentage is less than the weighted random number, it is a successful find
				  			if(distCheck<randomExp) {
//				  				System.out.println("actual/search: " + distCheck + ", random Exp: " + randomExp);
//				  				System.out.println("Distance Found At: " + actualdistance);
				  				System.out.println("Found in: "+simCounter+" cycles.");
						  		return 0;
				  			}
				  			
				  			
				  		}
				  		
				  		
				  		
				  		
				  	} 
				  
			  }
			  	
			  
		  }
		
		  return 1;
	  }
	
	
	

}
