package mainpkg;

import shaderpkg.PcMesh;
import shaderpkg.PcProgram;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import inputpkg.IKeyboardInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class CubeExampleCore extends ACore {
    
    private PcProgram pcprogram;
    private PcMesh pcmesh;

    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 50f;
    
    
    // Game state ------------------------------------------------------------------------------ //
    private IKeyboardInput prevInput;
    
    // Customize core setup -------------------------------------------------------------------- //
    public CubeExampleCore(){
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

    	glEnable(GL_CULL_FACE);
    	glCullFace(GL_FRONT);
    	glFrontFace(GL_CCW);
    	
        pcprogram = new PcProgram();
        pcmesh = new PcMesh(pcprogram, "meshdata/cube.ply");
        
        Matrix4f view = new Matrix4f();
        pcprogram.setView(view);

        /* Set projection matrix to an orthographic projection */
        Matrix4f projection = Matrix4f.orthographic(-windowRatio, windowRatio, -1f, 1f, -1f, 1f);
        pcprogram.setProjection(projection);
        
    }

    @Override
    protected void teardown() {
        pcmesh.delete();
        pcprogram.delete();
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

        pcmesh.bindVertexArrayObject();
        pcprogram.use();

        float lerpAngle = (1f - timePassed) * previousAngle + timePassed * angle;
        Matrix4f model = Matrix4f.rotate(lerpAngle, 0f, 0f, 1f);
        model = model.multiply(Matrix4f.rotate(previousAngle + timePassed * angle/10, .6f, 1f, 1f));
        pcprogram.setModel( Matrix4f.scale(.5f, .5f, .5f).multiply(model));

        pcmesh.draw();
    }
}
//---------------------------------------------------------------------------------------------- //
