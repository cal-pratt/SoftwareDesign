package mainpkg;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import shaderpkg.PcMesh;
import shaderpkg.PcProgram;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import inputpkg.IKeyboardInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class GameCore extends ACore {
    
    private PcProgram pcprogram;
    private PcMesh pcmesh;

    private float previousAngle = 0f;
    private float angle = 0f;
    private final float angelPerSecond = 50f;
    
    
    // Game state ------------------------------------------------------------------------------ //
    private IKeyboardInput prevInput;
    
    // Customize core setup -------------------------------------------------------------------- //
    public GameCore(){
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
    protected void resizeViewport() {
        
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
        pcprogram.setModel( model);

        pcmesh.draw();
    }
}
//---------------------------------------------------------------------------------------------- //
