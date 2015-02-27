package mainpkg;

// Local Project Imports ----------------------------------------------------------------------- //
import inputpkg.IKeyboardInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

// Class definition ---------------------------------------------------------------------------- //
public class GameCore extends ACore {
    
    // Game state ------------------------------------------------------------------------------ //
    private IKeyboardInput prevInput;
    private float currentColor;
    
    // Customize core setup -------------------------------------------------------------------- //
    public GameCore(){
        windowWidth = 300;
        windowHeight = 300;
        windowFullscreen = GL_FALSE;
        windowTitle = "Game Core";
        exitKey = GLFW_KEY_ESCAPE;
        threadSleepDuration = 20l;
    }
    
    // Core implementation --------------------------------------------------------------------- //
    @Override
    protected void startup() {
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        prevInput = inputreader.getKeyBoardInput();
        currentColor = 0f;
    }

    @Override
    protected void teardown() {
        
    }

    @Override
    protected void resizeViewport() {
        
    }

    @Override
    protected void updateLogic(long timePassed) {
        IKeyboardInput currInput = inputreader.getKeyBoardInput();
        
        currentColor += timePassed;
        glClearColor(
                1 - .5f*(float)Math.sin(Math.toRadians(currentColor*1.1/10.0)),
                1 - .5f*(float)Math.sin(Math.toRadians(currentColor/10.0)), 
                1 - .5f*(float)Math.sin(1.23 + Math.toRadians(currentColor*1.2/10.0)),
                0.0f);
        
        if( prevInput.getAction(GLFW_KEY_A) == GLFW_REPEAT 
                && currInput.getAction(GLFW_KEY_A) == GLFW_RELEASE ){
            
            glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
        }
        if( prevInput.getAction(GLFW_KEY_A) == GLFW_PRESS
                && currInput.getAction(GLFW_KEY_A) == GLFW_RELEASE ){
            
            glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
        }
        
        prevInput = currInput;
    }

    @Override
    protected void draw(long timePassed) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
//---------------------------------------------------------------------------------------------- //
