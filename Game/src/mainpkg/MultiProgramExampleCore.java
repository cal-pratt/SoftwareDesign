package mainpkg;

import java.nio.IntBuffer;

import menupkg.Menu;

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
public class MultiProgramExampleCore extends ACore {
  
    Menu menu;
    APcObject3D cube;
    GraphicsManager gm;
    
    float xplace = 0;
    float yplace = 0;
    
    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 50f;
    
    
    // Game state ------------------------------------------------------------------------------ //
    private IUserInput prevInput;
    
    // Customize core setup -------------------------------------------------------------------- //
    public MultiProgramExampleCore(){
        windowWidth = 1920/2;
        windowHeight = 1080/2;
        windowFullscreen = GL_FALSE;
        windowTitle = "Game Core";
        exitKey = GLFW_KEY_ESCAPE;
        threadSleepDuration = 10l;
    }
    
    // Core implementation --------------------------------------------------------------------- //
    @Override
    protected void startup() {
        prevInput = inputreader.getKeyBoardInput();
    	glEnable(GL_CULL_FACE);
    	glCullFace(GL_FRONT);
    	glFrontFace(GL_CCW);
    	
        gm = new GraphicsManager();
        
        gm.add(cube = Object3DFactory.getCube());
        
        long window = GLFW.glfwGetCurrentContext();
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
        int width = widthBuffer.get();
        int height = heightBuffer.get();
        

        menu = new Menu(gm, 0, 0, width, height);
        menu.addMenuItem(Object2DFactory.getContinue(), 0, 0, 10, 100);
        menu.addMenuItem(Object2DFactory.getContinue(), 100, 100, 100, 100);

        cube.setView(new Matrix4f());
        cube.setProjection(Matrix4f.orthographic(-windowRatio, windowRatio, -1f, 1f, -1f, 1f));
        
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
        long window = GLFW.glfwGetCurrentContext();
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
        int w = widthBuffer.get();
        int h = heightBuffer.get();
        
        //Matrix4f m = Matrix4f.orthographic(0, w, 0f, h, -1f, 10f);
        //(width/height)/(1920f/1080f)
        //continuetex.setProjection(m);
        //continuetex.setProjection(continuetex.getProjection().multiply());
        glViewport(0, 0, w, h);
    }

    @Override
    protected void updateLogic(long timePassed) {
        IUserInput currInput = inputreader.getKeyBoardInput();
        previousAngle = angle;
        angle += timePassed * angelPerSecond/1000.0;
        
        if(currInput.get(GLFW_KEY_A) == GLFW_PRESS || currInput.get(GLFW_KEY_A) == GLFW_REPEAT){
        	xplace -= .05f;
        	if (xplace < -windowRatio) xplace = -windowRatio;
        }
        if(currInput.get(GLFW_KEY_D) == GLFW_PRESS || currInput.get(GLFW_KEY_D) == GLFW_REPEAT){
        	xplace += .05f;
        	if (xplace > windowRatio) xplace = windowRatio;
        }
        if(currInput.get(GLFW_KEY_S) == GLFW_PRESS || currInput.get(GLFW_KEY_S) == GLFW_REPEAT){
        	yplace -= .05f;
        	if (yplace < -1) yplace = -1;
        }
        if(currInput.get(GLFW_KEY_W) == GLFW_PRESS || currInput.get(GLFW_KEY_W) == GLFW_REPEAT){
        	yplace += .05f;
        	if (yplace > 1) yplace = 1;
        }
        
        prevInput = currInput;
    }

    @Override
    protected void draw(long timePassed) {
        glClearColor(0.7f, 0.7f, 0.7f, 1.0f); 
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        float lerpAngle = (1f - timePassed) * previousAngle + timePassed * angle;
        Matrix4f model = Matrix4f.rotate(lerpAngle, 0f, 0f, 1f);
        model = model.multiply(Matrix4f.rotate(previousAngle + timePassed * angle, .6f, 1f, 1f));
        
        cube.setModel( Matrix4f.translate(xplace, yplace, 0f).multiply(Matrix4f.scale(.2f, .2f, .2f).multiply(model)));
        menu.update();
        
        gm.draw();
    }
}
//---------------------------------------------------------------------------------------------- //
