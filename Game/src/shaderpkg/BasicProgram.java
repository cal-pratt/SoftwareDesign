package shaderpkg;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class BasicProgram extends AProgram implements IProgram{

    private float[] modelMatrix;
    private ByteBuffer modelMatrixBuff;
    private int modelMatrixUnif;

    private float[] colorVector;
    private ByteBuffer colorVectorBuff;
    private int colorVectorUnif;

    private float[] cameraMatrix;
    private ByteBuffer cameraMatrixBuff;
    private int cameraMatrixUnif;
    
    public BasicProgram(){
        createProgram(
                "shaders/basicprogram.vert",
                "shaders/basicprogram.frag");
    }
    
    @Override
    protected void bindAttribLocations() {
        modelMatrixUnif = glGetUniformLocation(programIdentifier, "modelToCameraMatrix");
        colorVectorUnif = glGetUniformLocation(programIdentifier, "objectColor");
        colorVectorUnif = glGetUniformLocation(programIdentifier, "cameraToClipMatrix");
    }

    public void useProgram(int mode, int count, int type, ByteBuffer indicies) {
        glUseProgram(programIdentifier);
        glUniformMatrix4f(modelMatrixUnif, 1, false, modelMatrixBuff);
        glUniformMatrix4f(cameraMatrixUnif, 1, false, cameraMatrixBuff);
        glUniformMatrix4f(colorVectorUnif, 1, false, colorVectorBuff);

        glDrawElements(mode, count, type, indicies);
        
        glUseProgram(0);
    }
    
    public void setModelMatrix(float[] modelMatrix){
        if(modelMatrix.length != 16) {
            return;
        }
        this.modelMatrix = modelMatrix;
        modelMatrixBuff = ByteBuffer.allocateDirect(this.modelMatrix.length * 4);
        modelMatrixBuff.order(ByteOrder.nativeOrder());
        for(float f : modelMatrix){
            modelMatrixBuff.put(ByteBuffer.allocate(4).putFloat(f).array());
        }
        modelMatrixBuff.position(0);
    }
    
    public void setColorVector(float[] colorVector){
        if(colorVector.length != 4) {
            return;
        }
        this.colorVector = colorVector;
        colorVectorBuff = ByteBuffer.allocateDirect(this.colorVector.length * 4);
        colorVectorBuff.order(ByteOrder.nativeOrder());
        for(float f : colorVector){
            colorVectorBuff.put(ByteBuffer.allocate(4).putFloat(f).array());
        }
        colorVectorBuff.position(0);
    }
    
    public void setCameraMatrix(float[] cameraMatrix){
        if(cameraMatrix.length != 16) {
            return;
        }
        this.cameraMatrix = cameraMatrix;
        cameraMatrixBuff = ByteBuffer.allocateDirect(this.cameraMatrix.length * 4);
        cameraMatrixBuff.order(ByteOrder.nativeOrder());
        for(float f : cameraMatrix){
            cameraMatrixBuff.put(ByteBuffer.allocate(4).putFloat(f).array());
        }
        cameraMatrixBuff.position(0);
    }
    
}
