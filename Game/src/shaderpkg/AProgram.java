package shaderpkg;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

abstract class AProgram {
    
    protected int programIdentifier;
    
    protected abstract void bindAttribLocations();
    
    
    
    protected void createProgram(String vShaderFilename, String fShaderFilename) {       
        // Load the vertex shader
        int vShaderIdentifier = AProgram.loadShader(
            FileInterpreter.fileToString(vShaderFilename),
            GL20.GL_VERTEX_SHADER );
        int fShaderIdentifier = AProgram.loadShader(
            FileInterpreter.fileToString(fShaderFilename),
            GL20.GL_VERTEX_SHADER );
        
        programIdentifier = GL20.glCreateProgram();
        GL20.glAttachShader(programIdentifier, vShaderIdentifier);
        GL20.glAttachShader(programIdentifier, fShaderIdentifier);
 
        bindAttribLocations();
         
        GL20.glLinkProgram(programIdentifier);
        GL20.glValidateProgram(programIdentifier);
         
        if (GL11.glGetError() != GL11.GL_NO_ERROR) {
            System.err.println("Could not compile program.");
            return;
        }
        return;
    }
    
    private static int loadShader(StringBuilder shaderSource, int type){
        int shaderIdentifier = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderIdentifier, shaderSource);
        GL20.glCompileShader(shaderIdentifier);
         
        if (GL20.glGetShaderi(shaderIdentifier, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE ||
                GL11.glGetError() != GL11.GL_NO_ERROR) {
            System.err.println("Could not compile shader.");
            return 0;
        }
         
        return shaderIdentifier;
    }
}
