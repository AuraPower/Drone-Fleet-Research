
public class Startup_Multi {
	
	public static int startingx = DroneFleet.screenX/2; //starting x position
	public static int startingy = DroneFleet.screenY/2; //starting y position
	public static String droneMovementSelectedOption = "Grid"; //"Grid" or "Random"
	public static int numTrials = 2; //number of trials
	
	public static void main(String[] args) {
		
		System.out.println("multi mode");
		
		//TODO: run dronefleet multiple times
//		for(int i = 0; i<2; i++) {
//		DroneFleet.main(1, false, startingx, startingy, droneMovementSelectedOption, false);
//		}
		
		for (int i = 0; i < numTrials; i++) {
            Thread droneFleetThread = new Thread(() -> DroneFleet.main(1, false, startingx, startingy, droneMovementSelectedOption, false));
            droneFleetThread.start(); // Start the thread
            try {
            	droneFleetThread.join(); // Wait for the thread to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
		System.out.println("multi mode complete");
		
//		System.exit(0);//terminate console (0 indicates successful termination)
		
	}//end main
	
}//end Startup_Multi