import java.util.ArrayList;
import java.util.Arrays;

public class checkForFind {

    public static int numFalseNegatives = 0; // Counter for successful finds made by faulted drones
    public static int dronesFoundTarget = 0; // Counter for the number of drones that have found the target
    public static final int REQUIRED_DRONES_TO_FIND_TARGET = 5; // Set this to the required number of drones
    public static boolean[] canFindID, didFindID;
    
    // Returns a random number between 0 and the input
    public static int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    public static void Constructor() {
    	canFindID = new boolean[64];
    	didFindID  = new boolean[64];
    }
    
    // Function for only 1 target, this is the function used currently
    static int checkForFindFunction(ArrayList<Drone> drones, Target targets, int droneSearchRadius, int simCounter, boolean probabilisticRadius) {
        
    	boolean foundTheTarget = false;

        for (int i = 0; i < drones.size(); i++) {
            Drone cdrone = drones.get(i);
            Target ctarget = targets;
            
            int xdistance = Math.abs(cdrone.getX() - ctarget.getX());
            int ydistance = Math.abs(cdrone.getY() - ctarget.getY());
            double actualdistance = Math.sqrt((Math.pow(xdistance, 2)) + (Math.pow(ydistance, 2)));

            if (actualdistance < droneSearchRadius && (!didFindID[i])) {
            	canFindID[cdrone.droneID] = true;
                if (probabilisticRadius == false) {
                	
                	//Collects info for the dataGathering
                	didFindID[cdrone.droneID] = true;
                    dronesFoundTarget++;
                    if (dronesFoundTarget >= REQUIRED_DRONES_TO_FIND_TARGET) {
                        foundTheTarget = true;
                        break;
                    }
                } else {
                    double randomNum = Math.random();
                    double distCheck = 1 - (actualdistance / droneSearchRadius);
                    double distCheckLog = -0.875 * Math.log(((distCheck * -1) + 1));
                    distCheckLog = distCheckLog / 2.302585094;

                    if ((distCheckLog > randomNum) && (!cdrone.isFaulted())) {
                        if (cdrone.getResetCounter() <= 2) {
                            if (Startup_Multi.droneMovementSelectedOption.equals("Grid")) {
                                Movement.verifyTargetFind_Grid(cdrone);
                            } else if (Startup_Multi.droneMovementSelectedOption.equals("Random")) {
                                Movement.verifyTargetFind_Random(cdrone);
                            }
                        }

                        if (cdrone.getResetCounter() >= 2) {
                            System.out.println("Trial Completed: " + Startup_Multi.numTrialsRun + ", Found in: " + simCounter + " cycles.");
                            Startup_Multi.timeToFind.set(Startup_Multi.numTrialsRun - 1, simCounter);
                            
                            //Collects info for the dataGathering
                            didFindID[cdrone.droneID] = true;
                            dronesFoundTarget++;
                            if (dronesFoundTarget >= REQUIRED_DRONES_TO_FIND_TARGET) {
                                foundTheTarget = true;
                                break;
                            }
                        }
                    } else if ((distCheckLog > randomNum) && (cdrone.isFaulted())) {
                        numFalseNegatives++;
                        Startup_Multi.numFalseNegatives.set(Startup_Multi.numTrialsRun - 1, Startup_Multi.numFalseNegatives.get(Startup_Multi.numTrialsRun - 1) + 1);
                    }
                }
            }
        }

        if (foundTheTarget) {
            Startup_Multi.excelOutput.gatherData(drones, targets, canFindID, didFindID, simCounter, Startup_Multi.numTrialsRun);
            // Reset for next simulation
            dronesFoundTarget = 0;
            Constructor();
            // End simulation
            return 0; 
        } else {
        	Startup_Multi.excelOutput.gatherData(drones, targets, canFindID, didFindID, simCounter, Startup_Multi.numTrialsRun);
            return 1; // Continue simulation
        }
    }

    // Function for multiple targets (currently unused)
    static int checkForFindFunction_MultipleTargets(ArrayList<Drone> drones, ArrayList<Target> targets, int droneSearchRadius, int simCounter, boolean probabilisticRadius) {
        // Implementation for multiple targets (similar logic can be applied)
        // ...

        return 1; // Placeholder return value
    }

    public static boolean CanFindTarget(Drone drone, Target target, int droneSearchRadius) {
        int xdistance = Math.abs(drone.getX() - target.getX());
        int ydistance = Math.abs(drone.getY() - target.getY());
        double actualdistance = Math.sqrt((Math.pow(xdistance, 2)) + (Math.pow(ydistance, 2)));

        return actualdistance < droneSearchRadius;
        
    }
}
