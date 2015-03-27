package utilpkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import silvertiger.tutorial.lwjgl.math.Vector4f;
import creaturepkg.Player;
import creaturepkg.Skills;

public class FileInterpreter {
	public static void loadPlayerData(Player player, Skills skills){
		String filename = "save/save.txt";
		try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            player.setSkillPoints(100000);
            if((line = reader.readLine()) == null) throw new Exception();
            player.setExperience(Integer.parseInt(line));
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setFirePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setIcePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setStrongFirePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setStrongIcePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setDoubleFirePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setDoubleIcePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setTripleFirePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setTripleIcePressed();
            if((line = reader.readLine()) == null) throw new Exception();
            if(Boolean.parseBoolean(line)) skills.setStarPressed();
            if((line = reader.readLine()) == null) throw new Exception();
            player.setCurrentHealth(Integer.parseInt(line));
            if((line = reader.readLine()) == null) throw new Exception();
            player.setSkillPoints(Integer.parseInt(line));
            
            reader.close();
        } catch (Exception e) {
            System.err.println("Could not read file: " + filename);
            e.printStackTrace();
        }
	}
	
	public static void savePlayerData(Player player, Skills skills){
        BufferedWriter writer = null;
		String filename = "save/save.txt";
        try {
            File file = new File(filename);
            
            System.out.println(file.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(player.getExperience()) + "\n");
            writer.write(String.valueOf(skills.getFirePressed()) + "\n");
            writer.write(String.valueOf(skills.getIcePressed()) + "\n");
            writer.write(String.valueOf(skills.getStrongFirePressed()) + "\n");
            writer.write(String.valueOf(skills.getStrongIcePressed()) + "\n");
            writer.write(String.valueOf(skills.getDoubleFirePressed()) + "\n");
            writer.write(String.valueOf(skills.getDoubleIcePressed()) + "\n");
            writer.write(String.valueOf(skills.getTripleFirePressed()) + "\n");
            writer.write(String.valueOf(skills.getTripleIcePressed()) + "\n");
            writer.write(String.valueOf(skills.getStarPressed()) + "\n");
            writer.write(String.valueOf(player.getCurrentHealth()) + "\n");
            writer.write(String.valueOf(player.getSkillPoints()) + "\n");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } 
            catch (Exception e) {
            	
            }
        }
    }
	
	
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
