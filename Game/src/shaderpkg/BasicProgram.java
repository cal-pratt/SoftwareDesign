package shaderpkg;

import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUseProgram;
import com.joml.matrix.Matrix4f;
import com.joml.vector.Vector4f;

import java.nio.ByteBuffer;

public class BasicProgram extends AProgram{

    private UniformMatrix4f modelMatrixUniform;
    private UniformMatrix4f cameraMatrixUniform;
    private UniformVector4f colorVectorUniform;
    
    public BasicProgram(){
        createProgram("shaders/basicprogram.vert", "shaders/basicprogram.frag");
    }
    
    @Override
    protected void bindAttribLocations() {
    	modelMatrixUniform = new UniformMatrix4f(
    			glGetUniformLocation(programIdentifier, "modelToCameraMatrix"));
        cameraMatrixUniform = new UniformMatrix4f(
        		glGetUniformLocation(programIdentifier, "cameraToClipMatrix"));
        colorVectorUniform = new UniformVector4f(
        		glGetUniformLocation(programIdentifier, "objectColor"));
    }
    
    @Override
    public void draw(int mode, int count, int type, ByteBuffer indicies) {
        glUseProgram(programIdentifier);
        
        modelMatrixUniform.updateUniform();
        cameraMatrixUniform.updateUniform();
        colorVectorUniform.updateUniform();
        
        glDrawElements(mode, count, type, indicies);
        
        glUseProgram(0);
    }
    
    public void setModelMatrix(Matrix4f modelMatrix){
    	modelMatrixUniform.setData(modelMatrix);
    }
    
    public void setCameraMatrix(Matrix4f cameraMatrix){
    	cameraMatrixUniform.setData(cameraMatrix);
    }
    
    public void setColorVector(Vector4f colorVector){
    	colorVectorUniform.setData(colorVector);
    }
}
