import java.util.ArrayList;

public class checkForFind {

	  static int checkForFindFunction(ArrayList<drone> drones, ArrayList<target> targets, int droneSearchRadius, int simCounter) {
		  //runs through every drone
		  for (int i = 0; i < drones.size(); i++) {
			  drone cdrone = drones.get(i);
			  //runs through every target, per drone
			  for(int j =0; j< targets.size(); j++) {
				  target ctarget = targets.get(j);
				  //if the distances between the current drone and current targets X/Y are less than the search radius, stops simulation and outputs found statement
				  if((Math.abs(cdrone.getX() - ctarget.getX()) < droneSearchRadius) && (Math.abs(cdrone.getY() - ctarget.getY()) < droneSearchRadius)) {
				  		System.out.println("Found in: "+simCounter+" cycles.");
				  		return 0;
				  	} 
				  
			  }
			  	
			  
		  }
		
		  return 1;
	  }
	
	
	

}
