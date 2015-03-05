package mainpkg;

import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;
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
