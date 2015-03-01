package shaderpkg;

import utilpkg.Converter;
import utilpkg.FileInterpreter;
import silvertiger.tutorial.lwjgl.math.*;

import static org.lwjgl.opengl.GL20.glUniformMatrix3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;


import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glCreateShader;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

abstract class AProgram { 
    
	private abstract class Uniformf{
	    private ByteBuffer buf;
	    private int unif;
	    
	    public Uniformf(int unif, ByteBuffer dataBuffer){
	    	buf = dataBuffer;
	    	this.unif = unif;
	    }

	    abstract public void updateUniform();
	    
	    protected void setData(ByteBuffer dataBuffer){
	    	buf = dataBuffer;
	    }
	    
	    protected ByteBuffer getBuffer(){
	    	return buf;
	    }
	    
	    protected int getUnif(){
	    	return unif;
	    }
	}
	
	protected class UniformVector3f extends Uniformf{
		public UniformVector3f(int unif){
			super(unif, ByteBuffer.allocate(12));
		}
		public void setData(Vector3f data){
		    ByteBuffer buf = BufferUtils.createByteBuffer(36);
		    buf.asFloatBuffer().put(data.getBuffer());
            buf.flip();
            buf.rewind();
            setData(buf);
		}
		@Override
		public void updateUniform() {
			glUniform3f(getUnif(), 1, getBuffer());
		}
	}
	
	protected class UniformVector4f extends Uniformf{
		public UniformVector4f(int unif){
			super(unif, ByteBuffer.allocate(16));
		}
		public void setData(Vector4f data){
		    ByteBuffer buf = Converter.toByteBuffer(data);
            buf.flip();
            setData(buf);
		}
		@Override
		public void updateUniform() {
			glUniform4f(getUnif(), 1, getBuffer());
		}
	}
	
	protected class UniformMatrix3f extends Uniformf{
		public UniformMatrix3f(int unif){
			super(unif, ByteBuffer.allocate(36));
		}
		public void setData(Matrix3f data){
		    ByteBuffer buf = Converter.toByteBuffer(data);
            buf.flip();
            setData(buf);
		}
		@Override
		public void updateUniform() {
			glUniformMatrix3f(getUnif(), 1, false, getBuffer());
		}
	}
	
	protected class UniformMatrix4f extends Uniformf{
		public UniformMatrix4f(int unif){
			super(unif, ByteBuffer.allocate(64));
		}
		public void setData(Matrix4f data){
		    ByteBuffer buf = Converter.toByteBuffer(data);
		    buf.rewind();
            //buf.flip();
			setData(buf);
		}
		@Override
		public void updateUniform() {
		    int g = getUnif();
		    ByteBuffer b =  getBuffer();
			glUniformMatrix4f(g, 1, false, b);
		}
	}
	
    protected int programIdentifier;
    
    protected abstract void bindAttribLocations();
    
    public void use(){
        glUseProgram(programIdentifier);
    }
    
    protected void createProgram(String vShaderFilename, String fShaderFilename) {       
        // Load the vertex shader
        int vShaderIdentifier = loadShader(
            FileInterpreter.fileToString(vShaderFilename),
            GL_VERTEX_SHADER );
        int fShaderIdentifier = loadShader(
            FileInterpreter.fileToString(fShaderFilename),
            GL_FRAGMENT_SHADER );
        
        programIdentifier = glCreateProgram();
        glAttachShader(programIdentifier, vShaderIdentifier);
        glAttachShader(programIdentifier, fShaderIdentifier);
 
         
        glLinkProgram(programIdentifier);
        
        
        if (glGetProgrami(programIdentifier, GL_LINK_STATUS) != GL_TRUE) 
		{
			System.err.println(glGetProgramInfoLog(programIdentifier));
			throw new RuntimeException("could not link shader. Reason: "
					+ glGetProgramInfoLog(programIdentifier));
		}
 
		// perform general validation that the program is usable
		glValidateProgram(programIdentifier);
 
		if (glGetProgrami(programIdentifier,  GL_VALIDATE_STATUS) != GL_TRUE)
		{
			System.err.println(glGetProgramInfoLog(programIdentifier));
			throw new RuntimeException("could not validate shader. Reason: " 
					+ glGetProgramInfoLog(programIdentifier));            
		}
		
        bindAttribLocations();
		
    }
    
    private static int loadShader(StringBuilder shaderSource, int type){
        int shaderIdentifier = glCreateShader(type);
        glShaderSource(shaderIdentifier, shaderSource);
        glCompileShader(shaderIdentifier);
         
        if (glGetShaderi(shaderIdentifier, GL_COMPILE_STATUS) != GL_TRUE) {
            System.err.println(glGetShaderInfoLog(shaderIdentifier));
            
            return 0;
        }
         
        return shaderIdentifier;
    }
}
