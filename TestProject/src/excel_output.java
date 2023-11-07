import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class excel_output {
    public static void main(String[] args) {
        try {
        	
        	String file_specs = String.valueOf(Startup_Multi.numTrials) + "Trials_" + String.valueOf(DroneFleet.droneFaultRatio) + "%Faulted_" + String.valueOf(DroneFleet.droneFalsePosChance) + "%FalsePosChance";
        	
            // Define the file name
            String fileName = "C:\\Users\\Jack Capuano\\Desktop\\SimRuns\\"+file_specs+".csv";

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