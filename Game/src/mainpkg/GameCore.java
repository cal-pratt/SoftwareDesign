package mainpkg;

// Local Project Imports ----------------------------------------------------------------------- //
//import shaderpkg.PcProgram;
//import vaopkg.PcVao;
import shaderpkg.PcProgram;
import inputpkg.IKeyboardInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClear;


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
        new PcProgram();
        
        
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
        
        prevInput = currInput;
    }

    @Override
    protected void draw(long timePassed) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
//---------------------------------------------------------------------------------------------- //
