import java.util.concurrent.CountDownLatch;

public class Startup_Multi {
	
	public static int startingx = DroneFleet.screenX/2; //starting x position
	public static int startingy = DroneFleet.screenY/2; //starting y position
	public static String droneMovementSelectedOption = "Grid"; //"Grid" or "Random"
	public static int numTrials = 2; //number of trials
	public static int numTrialsRun = 0;
	public static boolean startNextTrialFlag = true;
	
	public static void main(String[] args) {
		
		System.out.println("multi mode");
		
		//TODO: run dronefleet multiple times
//		for(int i = 0; i<2; i++) {
//		DroneFleet.main(1, false, startingx, startingy, droneMovementSelectedOption, false);
//		}
		
//		for (int i = 0; i < numTrials; i++) {
//            Thread droneFleetThread = new Thread(() -> DroneFleet.main(1, false, startingx, startingy, droneMovementSelectedOption, false));
//            droneFleetThread.start(); // Start the thread
//            try {
//            	droneFleetThread.join(); // Wait for the thread to finish
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
		
//		// Create an array to hold references to simulation threads
//        Thread[] simulationThreads = new Thread[numberOfSimulations];
//
//        
//            final int simulationNumber = i;
//            Thread simulationThread = new Thread(() -> {
//                // You can set different parameters for each simulation
//                int runType = 0; // 0 for single run
//                boolean fullMarkerLinesIn = true; // Set to true or false
//                int startingxIn = 750; // Change the starting x-coordinate
//                int startingyIn = 400; // Change the starting y-coordinate
//                String droneMovementSelectedOption = "Random"; // "Random" or "Grid"
//                boolean debugMode = false; // Set to true or false
//
//                // Call DroneFleet.main with the parameters
//                DroneFleet.main(1, false, startingx, startingy, droneMovementSelectedOption, false);
//
//                // Handle any post-simulation tasks here
//
//            });
//            simulationThreads[i] = simulationThread;
//            simulationThread.start();
//        }
//
//        // Wait for all simulation threads to complete
//        for (Thread thread : simulationThreads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
		while(numTrialsRun<numTrials) {
			while(startNextTrialFlag == true) {
				
				startNextTrialFlag = false;
				DroneFleet.run(1, false, startingx, startingy, droneMovementSelectedOption, false);
				numTrialsRun += 1;
				
			}
		}
		
//		for (int i = 0; i < numTrials; i++) {
//			Thread thread = new Thread(() -> {
//				DroneFleet.run(1, false, startingx, startingy, droneMovementSelectedOption, false);
//			});
//			thread.start(); // Start the thread
//			try {
//			    thread.join(); // Wait for the thread to finish
//			} catch (InterruptedException e) {
//			    // Handle the exception
//			}
//		}
		
//		for (int i = 0; i < numTrials; i++) {
//		    DroneFleet.run(1, false, startingx, startingy, droneMovementSelectedOption, false);
//		}
//		
//		CountDownLatch latch = new CountDownLatch(numTrials);
//
//		for (int i = 0; i < numTrials; i++) {
//		    Thread thread = new Thread(() -> {
//		        DroneFleet.run(1, false, startingx, startingy, droneMovementSelectedOption, false);
//		        latch.countDown(); // Signal that this thread has finished
//		    });
//		    thread.start();
//		}
//
//		try {
//		    latch.await(); // Wait for all threads to finish
//		} catch (InterruptedException e) {
//		    // Handle the exception
//		}
//
//        System.out.println("All simulations have finished.");
		
//		System.exit(0);//terminate console (0 indicates successful termination)
		
	}//end main
	
}//end Startup_Multi