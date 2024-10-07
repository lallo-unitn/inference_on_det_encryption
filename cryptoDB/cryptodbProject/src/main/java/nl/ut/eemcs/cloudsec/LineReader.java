package nl.ut.eemcs.cloudsec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LineReader {


    // Method to read names from a file and count their occurrences
    public ArrayList<String> readLinesFromFile(String fileName, int max) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            ArrayList<String> linesOrderedFrequency = new ArrayList<String>();

            // Read first 100 lines (line) from the file
            for (int i = 0; i < max; i++) {
                line = reader.readLine();
                linesOrderedFrequency.add(line);
            }

            /*while ((line = reader.readLine()) != null) {
                linesOrderedFrequency.add(line);
            }*/
            return linesOrderedFrequency;
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return null;
    }

    // Method to display the contents of the map (names and their counts)
    public void displayLinesAndPos(ArrayList<String> linesOrderedFrequency) {
        for (String line : linesOrderedFrequency) {
            System.out.println("Name: " + line + ", Position: " + linesOrderedFrequency.indexOf(line));
        }
    }
}

