import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class ExcelOutput {
	
	private ArrayList<Drone> droneCollection = new ArrayList<Drone>();
	private ArrayList<Target> targetCollection = new ArrayList<Target>();
	private ArrayList<Integer> cycles = new ArrayList<Integer>();
	private ArrayList<Boolean> canFinds = new ArrayList<Boolean>();
	private ArrayList<Boolean> didFinds = new ArrayList<Boolean>();
	private ArrayList<Integer> trials = new ArrayList<Integer>();
	
	private int perCycle = 10;
	
	public ExcelOutput() {
		checkForFind.Constructor();
		
		//Creates TrainingData.csv if it does not already exist
		File file = new File("TrainingData.csv");
        if (!file.exists()) {
	        try {
				createTrainingData();
			} catch (IOException e) {
				System.out.print("You messed up somewhere in your excelOutput Class");
				e.printStackTrace();
			}
        }
		
	}
	
	private void ClearCollections() {
		droneCollection.clear();
		targetCollection.clear();
		canFinds.clear();
		didFinds.clear();
		cycles.clear();
		trials.clear();
	}
	
	public void gatherData(boolean[] canFind, boolean[] didFind, int cycle,  int trialNumber) {	
		for(int i=0;i<DroneFleet.drones.size();i++) {
			droneCollection.add(DroneFleet.drones.get(i));
			targetCollection.add(DroneFleet.targets);
			canFinds.add(canFind[i]);
			didFinds.add(didFind[i]);
			cycles.add(cycle);
			trials.add(trialNumber);
		}

		//Adds data to TrainingData.csv
		try {
			appendTrainingData();
		} catch (IOException e) {
			System.out.print("You messed up somewhere in your excelOutput Class");
			e.printStackTrace();
		}
		
		ClearCollections();
	}
	
	
	public void appendTrainingData() throws IOException{
		String fileName = "TrainingData.csv";
		
		//Create FileWriter
		FileWriter writer = new FileWriter(fileName, true);
		PrintWriter appender = new PrintWriter(writer);
		
		int lastTrialNum=0;
		
		for(int i=0;i<droneCollection.size();i++) {
        	appender.println(droneCollection.get(i).toString()+","+targetCollection.get(i).toString()+","+canFinds.get(i)+","+didFinds.get(i)+","+cycles.get(i)+","+(trials.get(i)+lastTrialNum));
           }
		
		appender.close();
	}
	
	public void createTrainingData() throws IOException {
		
		String fileName = "TrainingData.csv";
		
		// Create a FileWriter to write to the CSV file
        FileWriter writer = new FileWriter(fileName);
        writer.append("DroneID, DroneX, DroneY, Faulty, TargetX, TargetY, CanFindTarget, FoundTarget, Cycle, TrialNumber\n");
        
        writer.close();
	}
	
	public void outputNotUseful() {
        try {
        	
        	DecimalFormat df = new DecimalFormat("#.00");

            // Use the format method to round the double to 2 decimal places
            String droneFaultRatio_Rounded = df.format(DroneFleet.droneFaultRatio*100);
            String droneFalsePOsChance_Rounded = df.format(DroneFleet.droneFalsePosChance*100);
        	
        	String file_specs = String.valueOf(Startup_Multi.numTrials) + "Trials_" + droneFaultRatio_Rounded + "%Faulted_" + droneFalsePOsChance_Rounded + "%FalsePosChance";
       
            // Define the file name
            String fileName = file_specs+".csv";
          

            // Create a FileWriter to write to the CSV file
            FileWriter writer = new FileWriter(fileName);

            // Write headers (variable names)
            writer.append("TTF,False Negatives,False Positives\n");

            // Create a two-dimensional array to store the data
            List<List<?>> allData = new ArrayList<>();
            allData.add(Startup_Multi.timeToFind);
            allData.add(Startup_Multi.numFalseNegatives);
            allData.add(Startup_Multi.numFalsePositives);
            

            // Write data from each ArrayList into separate columns
            writeData(writer, allData);

            // Close the FileWriter
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeData(FileWriter writer, List<List<?>> allData) throws IOException {
        int numRows = allData.stream().mapToInt(List::size).max().orElse(0);

        for (int row = 0; row < numRows; row++) {
            for (int i = 0; i < allData.size(); i++) {
                List<?> data = allData.get(i);
                if (row < data.size()) {
                    writeCellValue(writer, data.get(row).toString());
                }
                if (i < allData.size() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");
        }
    }

    private static void writeCellValue(FileWriter writer, String value) throws IOException {
        // Escape values with special characters or commas
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            writer.append("\"" + value.replace("\"", "\"\"") + "\"");
        } else {
            writer.append(value);
        }
    }
}