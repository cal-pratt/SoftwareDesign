package mainpkg;

import java.nio.IntBuffer;

import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;
import inputpkg.IKeyboardInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class GmExampleCore extends ACore {
  
    APcObject3D cube;
    APcObject3D tri;
    GraphicsManager gm;
    
    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 50f;
    
    
    // Game state ------------------------------------------------------------------------------ //
    private IKeyboardInput prevInput;
    
    // Customize core setup -------------------------------------------------------------------- //
    public GmExampleCore(){
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
        
    	glEnable(GL_CULL_FACE);
    	glCullFace(GL_FRONT);
    	glFrontFace(GL_CCW);
    	
        gm.add(cube = Object3DFactory.getCube());
        gm.add(tri = Object3DFactory.getTriangle());
        
        cube.setView(new Matrix4f());
        cube.setProjection(Matrix4f.orthographic(-windowRatio, windowRatio, -1f, 1f, -1f, 1f));
        tri.setView(new Matrix4f());
        tri.setProjection(Matrix4f.orthographic(-windowRatio, windowRatio, -1f, 1f, -1f, 1f));
        
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
        IKeyboardInput currInput = inputreader.getKeyBoardInput();
        previousAngle = angle;
        angle += timePassed * angelPerSecond/1000.0;
        
        prevInput = currInput;
    }

    @Override
    protected void draw(long timePassed) {
        glClear(GL_COLOR_BUFFER_BIT);


        float lerpAngle = (1f - timePassed) * previousAngle + timePassed * angle;
        Matrix4f model = Matrix4f.rotate(lerpAngle, 0f, 0f, 1f);
        model = model.multiply(Matrix4f.rotate(previousAngle + timePassed * angle, .6f, 1f, 1f));
        
        cube.setModel( Matrix4f.translate(.4f, 0f, 0f).multiply(Matrix4f.scale(.2f, .2f, .2f).multiply(model)));
        tri.setModel( Matrix4f.translate(-.4f, 0f, 0f).multiply(Matrix4f.scale(.2f, .2f, .2f).multiply(model)));
        

        gm.draw();
    }
}
//---------------------------------------------------------------------------------------------- //
