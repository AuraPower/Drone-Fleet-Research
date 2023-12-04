import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Startup_Multi {
	
	static int numTrials = 10; // number of simulations to run
	static int numTrialsRun = 1; // do not change this from 1
	static ArrayList<Integer> numFalsePositives = new ArrayList<>();
	static ArrayList<Integer> numFalseNegatives = new ArrayList<>();
	static ArrayList<Integer> timeToFind = new ArrayList<>();
	static String droneMovementSelectedOption = "Random"; // "Random" or "Grid"
	static ExcelOutput excelOutput = new ExcelOutput();
	
    public static void main(String[] args) {
    	
    	
    	for(int j = 0; j < numTrials; j++) {
    		numFalseNegatives.add(0);
    		Startup_Multi.numFalsePositives.add(0);
    		Startup_Multi.timeToFind.add(0);
    	}
    	
        int numSimulations = numTrials;

        for (int i = 0; i < numSimulations; i++) {
            int runType = 1;
            boolean fullMarkerLinesIn = false;
            int startingxIn = DroneFleet.screenX/2; // starting x coord
            int startingyIn = DroneFleet.screenY/2; // starting y coord
            
            boolean debugMode = false;

            // Create a new instance of DroneFleet
            DroneFleet simFrame = new DroneFleet();

            // Call the reset method to reset the simulation state
            simFrame.reset();

            // Create a new JFrame and add the DroneFleet instance
            JFrame sim = new JFrame();
            sim.setSize(1514, 838);
            sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sim.add(simFrame);
            sim.setVisible(false);//Don't need to see empty frames

            // Use a CountDownLatch to ensure that the simulation is finished
            CountDownLatch latch = new CountDownLatch(1);

            // Start the simulation in a separate thread
            Thread simulationThread = new Thread(() -> {
                // Call the DroneFleet main method with the specified parameters
                DroneFleet.main(runType, fullMarkerLinesIn, startingxIn, startingyIn, droneMovementSelectedOption, debugMode);

                // Wait for the simulation to complete
                while (simFrame.isSimulationRunning()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Signal that the simulation is finished
                latch.countDown();
            });

            simulationThread.start();

            try {
                latch.await(); // Wait for the simulation to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Delay for simulation thread to finish
            try {
                Thread.sleep(100); // Delay in milliseconds (10 seconds in this example)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numTrialsRun += 1;
            
        }
        
        File file = new File("TrainingData.csv");
        if (file.exists()) {
	        try {
				excelOutput.appendTrainingData();
			} catch (IOException e) {
				System.out.print("You messed up somewhere in your excelOutput Class");
				e.printStackTrace();
			}
        }
        else {
        	try {
				excelOutput.createTrainingData();
			} catch (IOException e) {
				System.out.print("You messed up somewhere in your excelOutput Class");
				e.printStackTrace();
			}
        }
        System.exit(0);
    }
}