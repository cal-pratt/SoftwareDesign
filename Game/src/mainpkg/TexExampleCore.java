package mainpkg;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import objectpkg.ATexObject2D;
import objectpkg.Object2DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;

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
        
        gm = new GraphicsManager();
        
        gm.add(continuetex = Object2DFactory.getContinue());
        
        long window = GLFW.glfwGetCurrentContext();
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
        int width = widthBuffer.get();
        int height = heightBuffer.get();
        
        continuetex.setProjection(Matrix4f.orthographic(0f, width, 0f, height, -1f, 1f));
        
    }

    @Override
    protected void teardown() {
    }

    @Override
    protected void resizeViewport() {
        float dx, dy, size;
        if(windowWidth > windowHeight){
            dx = (windowWidth - windowHeight) / 2;
            dy = 0;
            size = windowHeight;
        }
        else{
            dx = 0;
            dy = (windowHeight - windowWidth) / 2;
            size = windowWidth;
        }
        
        glViewport((int)dx, (int)dy, (int)size, (int)size);
    }

    @Override
    protected void updateLogic(long timePassed) {
        previousAngle = angle;
        angle += timePassed * angelPerSecond/1000.0;
        
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

        gm.draw();
    }
}
//---------------------------------------------------------------------------------------------- //
