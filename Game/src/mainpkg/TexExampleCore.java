package mainpkg;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import objectpkg.APcObject3D;
import objectpkg.ATexObject2D;
import objectpkg.Object2DFactory;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;
import inputpkg.IUserInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class TexExampleCore extends ACore {
  
    ATexObject2D continuetex;
    GraphicsManager gm;
    
    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 50f;
    
    
    // Game state ------------------------------------------------------------------------------ //
    private IUserInput prevInput;
    
    // Customize core setup -------------------------------------------------------------------- //
    public TexExampleCore(){
        windowWidth = 300;
        windowHeight = 300;
        windowFullscreen = GL_FALSE;
        windowTitle = "Game Core";
        exitKey = GLFW_KEY_ESCAPE;
        threadSleepDuration = 10l;
    }
    
    // Core implementation --------------------------------------------------------------------- //
    @Override
    protected void startup() {
        prevInput = inputreader.getKeyBoardInput();
        
        gm = new GraphicsManager();
        
        gm.add(continuetex = Object2DFactory.getContinue());
        
        long window = GLFW.glfwGetCurrentContext();
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
        int width = widthBuffer.get();
        int height = heightBuffer.get();
        
        continuetex.setView(new Matrix4f());
        continuetex.setProjection(Matrix4f.orthographic(0f, width, 0f, height, -1f, 1f));
        
    }

    @Override
    protected void teardown() {
    }

    @Override
    protected void resizeViewport(int width, int height) {
        int dx, dy, size;
        if(width > height){
            dx = (width - height) / 2;
            dy = 0;
            size = height;
        }
        else{
            dx = 0;
            dy = (height - width) / 2;
            size = width;
        }
        
        glViewport(dx, dy, size, size);
    }

    @Override
    protected void updateLogic(long timePassed) {
        IUserInput currInput = inputreader.getKeyBoardInput();
        previousAngle = angle;
        angle += timePassed * angelPerSecond/1000.0;
        
        prevInput = currInput;
    }

    @Override
    protected void draw(long timePassed) {
        glClearColor(0.7f, 0.7f, 0.7f, 1.0f); 
        glClear(GL_COLOR_BUFFER_BIT);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        float lerpAngle = (1f - timePassed) * previousAngle + timePassed * angle;
        Matrix4f model = Matrix4f.rotate(lerpAngle, 0f, 0f, 1f);
        model = model.multiply(Matrix4f.rotate(previousAngle + timePassed * angle, .6f, 1f, 1f));
        
        //cube.setModel( Matrix4f.translate(.4f, 0f, 0f).multiply(Matrix4f.scale(.2f, .2f, .2f).multiply(model)));
        //tri.setModel( Matrix4f.translate(-.4f, 0f, 0f).multiply(Matrix4f.scale(.2f, .2f, .2f).multiply(model)));
        continuetex.setModel(new Matrix4f());

        gm.draw();
    }
}
//---------------------------------------------------------------------------------------------- //
