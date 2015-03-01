package shaderpkg;

import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.nio.ByteBuffer;

import silvertiger.tutorial.lwjgl.math.*;


public class PcProgram extends AProgram{

    private UniformMatrix4f modelMatrixUniform;
    private UniformMatrix4f cameraMatrixUniform;
    
    public PcProgram(){
        createProgram("shaders/basicprogram.vert", "shaders/basicprogram.frag");
    }
    
    @Override
    protected void bindAttribLocations() {
    	modelMatrixUniform = new UniformMatrix4f(
    			glGetUniformLocation(programIdentifier, "modelToCameraMatrix"));
        cameraMatrixUniform = new UniformMatrix4f(
        		glGetUniformLocation(programIdentifier, "cameraToClipMatrix"));
    }
    
    public void updateUniforms(){
        modelMatrixUniform.updateUniform();
        cameraMatrixUniform.updateUniform();
    }
    
    public void setModelMatrix(Matrix4f modelMatrix){
    	modelMatrixUniform.setData(modelMatrix);
    }
    
    public void setCameraMatrix(Matrix4f cameraMatrix){
    	cameraMatrixUniform.setData(cameraMatrix);
    }
}
