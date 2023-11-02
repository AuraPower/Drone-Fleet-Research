import java.util.concurrent.CountDownLatch;

public class Startup_Multi {

    public static int startingx = DroneFleet.screenX / 2; // Starting x position
    public static int startingy = DroneFleet.screenY / 2; // Starting y position
    public static String droneMovementSelectedOption = "Grid"; // "Grid" or "Random"
    public static int numTrials = 3; // Number of trials
    public static int numTrialsRun = 1;
    public static boolean startNextTrialFlag = true;

    public static void main(String[] args) {
        System.out.println("multi mode");
        
        while (numTrialsRun <= numTrials) {
            if (startNextTrialFlag) {
                System.out.println("Running Trial: " + numTrialsRun);
                DroneFleet.run(1, false, startingx, startingy, droneMovementSelectedOption, false);
                numTrialsRun++; // Increment the trial counter
                startNextTrialFlag = false; // Reset the flag here
            } else {
                try {
                    Thread.sleep(100); // Add a short delay to avoid busy-waiting
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.exit(0); // Terminate the console (0 indicates successful termination)
    }
}