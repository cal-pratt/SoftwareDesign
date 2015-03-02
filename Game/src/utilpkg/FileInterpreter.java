package utilpkg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import silvertiger.tutorial.lwjgl.math.*;
//
//import com.joml.vector.Vector4f;

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
    
    public static void readObjFile(String filename, List<Vector4f> verticies, List<Integer> indicies) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ")){
                    String[] vertexComponents = line.split(" ");
                    verticies.add(new Vector4f(
                            Float.parseFloat(vertexComponents[1]),
                            Float.parseFloat(vertexComponents[2]),
                            Float.parseFloat(vertexComponents[3]),
                            1.0f
                    ));
                }
                if (line.startsWith("f ")){
                    String[] triangleIndicies = line.split(" ");
                    indicies.add(Integer.parseInt(triangleIndicies[1]) - 1);
                    indicies.add(Integer.parseInt(triangleIndicies[2]) - 1);
                    indicies.add(Integer.parseInt(triangleIndicies[3]) - 1);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file: " + filename);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void readPlyFile(String filename, 
            ArrayList<Vector4f> verticies,
            ArrayList<Vector4f> colors,
            ArrayList<Integer> indicies) {
        
        verticies.clear();
        colors.clear();
        indicies.clear();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int vertexCount = 0;
            int faceCount = 0;
            while(true){
                if((line = reader.readLine()) == null){
                    reader.close(); return;
                }
                else if(line.contains("element vertex")){
                    String[] vertexInfo = line.split(" ");
                    vertexCount = Integer.parseInt(vertexInfo[2]);
                    verticies.ensureCapacity(vertexCount);
                    colors.ensureCapacity(vertexCount);
                }
                else if(line.contains("element face")){
                    String[] faceInfo = line.split(" ");
                    faceCount = Integer.parseInt(faceInfo[2]);
                }
                else if(line.contains("end_header")){
                    break;
                }
            }
            if (verticies == null || verticies == null 
                    || vertexCount == 0 || faceCount == 0){
                reader.close(); return;
            }
            while (vertexCount-- != 0) {
                if((line = reader.readLine()) == null){
                    reader.close(); return;
                }
                String[] vertexComponents = line.split(" ");
                verticies.add(new Vector4f(
                        Float.parseFloat(vertexComponents[0]),
                        Float.parseFloat(vertexComponents[1]),
                        Float.parseFloat(vertexComponents[2]),
                        1
                ));
                colors.add(new Vector4f(
                        Float.parseFloat(vertexComponents[3])/255f,
                        Float.parseFloat(vertexComponents[4])/255f,
                        Float.parseFloat(vertexComponents[5])/255f,
                        1
                ));
            }
            while (faceCount-- != 0) {
                if((line = reader.readLine()) == null){
                    reader.close(); return;
                }
                String[] faceComponents = line.split(" "); 
                if(Integer.parseInt(faceComponents[0]) == 3){
                    indicies.add(Integer.parseInt(faceComponents[1]));
                    indicies.add(Integer.parseInt(faceComponents[2]));
                    indicies.add(Integer.parseInt(faceComponents[3]));
                }
                else if(Integer.parseInt(faceComponents[0]) == 4){
                    indicies.add(Integer.parseInt(faceComponents[1]));
                    indicies.add(Integer.parseInt(faceComponents[2]));
                    indicies.add(Integer.parseInt(faceComponents[3]));
                    indicies.add(Integer.parseInt(faceComponents[1]));
                    indicies.add(Integer.parseInt(faceComponents[3]));
                    indicies.add(Integer.parseInt(faceComponents[4]));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Could not read file: " + filename);
            e.printStackTrace();
        }
    }
}
