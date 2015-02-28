package utilpkg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileInterpreter {
    public static StringBuilder fileToString(String filename) {
        StringBuilder stringBuilder = new StringBuilder();
         
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file: " + filename);
            e.printStackTrace();
            return null;
        }
        
        return stringBuilder;
    }
}
