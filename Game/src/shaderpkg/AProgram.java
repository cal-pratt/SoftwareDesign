package shaderpkg;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.util.vector;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

abstract class AProgram {
    
	private abstract class Uniformf{
	    private ByteBuffer buff;
	    private int unif;
	    private int size;
	    
	    public Uniformf(int size, int unif){
	    	buff = ByteBuffer.allocate(size * 4);
	    	this.unif = unif;
	    	this.size = size;
	    }
	    
	    protected void setBuffer(FloatBuffer floatBuffer){

			if (floatBuffer.capacity() == 4){
		    	buff.clear();
		    	buff.asFloatBuffer().put(floatBuffer);
		        buff.position(0);
			}
	    }
	    
	    public ByteBuffer getBuffer(){
	    	return buff;
	    }
	    
	    public int getUnif(){
	    	return unif;
	    }
	}
	protected class UniformVector4f extends Uniformf{
		public UniformVector4f(int unif){
			super(4, unif);
		}
		public void setBuffer(Vector4f floatBuffer){
			if (floatBuffer.capacity() == 4){
				
			}
		}
	}
	
	
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
