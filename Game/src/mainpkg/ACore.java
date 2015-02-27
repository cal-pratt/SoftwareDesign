package mainpkg;

// Local Project Imports ----------------------------------------------------------------------- //
import inputpkg.InputReader;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import java.nio.ByteBuffer;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

// Class definition ---------------------------------------------------------------------------- //
public abstract class ACore {

    // Customizable startup variables ---------------------------------------------------------- //
    protected int windowWidth = 300;
    protected int windowHeight = 300;
    protected int windowFullscreen = GL_FALSE;
    protected String windowTitle = "Default ACore";
    protected int exitKey = GLFW_KEY_ESCAPE;
    protected long threadSleepDuration = 20l;
    
    // Major components ------------------------------------------------------------------------- //
    protected InputReader inputreader;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private long windowIdentifier; 

    // State ----------------------------------------------------------------------------------- //
    private boolean running = true;
    
    // Abstract functions ---------------------------------------------------------------------- //
    protected abstract void startup();
    protected abstract void teardown();
    protected abstract void resizeViewport();
    protected abstract void updateLogic(long timePassed);
    protected abstract void draw(long timePassed);
    
    // Main application execution -------------------------------------------------------------- //
    public void run() {
        try {
            System.out.println("Creating window: " + windowTitle);
            createWindow();
            createInput();
            GLContext.createFromCurrent();
            
            mainLoop();
            
            System.out.println("Exiting window: " + windowTitle);
            glfwDestroyWindow(windowIdentifier);
            keyCallback.release();
        } finally {
            glfwTerminate();
            errorCallback.release();
        }
    }

    // Load OpenGl and populate window --------------------------------------------------------- //
    private void createWindow() {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        if ( glfwInit() != GL11.GL_TRUE ){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, windowFullscreen == GL_TRUE ? GL_FALSE : GL_TRUE );
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        if (windowFullscreen == GL_TRUE){
            windowIdentifier = glfwCreateWindow(windowWidth, windowHeight, windowTitle, 
                    glfwGetPrimaryMonitor(), NULL);
        }
        else{
            windowIdentifier = glfwCreateWindow(windowWidth, windowHeight, windowTitle, 
                    NULL, NULL);
        }
        if ( windowIdentifier == NULL ){
            throw new RuntimeException("Failed to create the GLFW window");
        }
 
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        if(windowFullscreen == GL_FALSE){
            glfwSetWindowPos(
                windowIdentifier,
                (GLFWvidmode.width(vidmode) - windowWidth) / 2,
                (GLFWvidmode.height(vidmode) - windowHeight) / 2
            );
        }
        glfwMakeContextCurrent(windowIdentifier);
        glfwSwapInterval(1);
        glfwShowWindow(windowIdentifier);
    }
    
    // Link OpenGl input callback to input-reader ---------------------------------------------- //
    private void createInput() {
        inputreader = new InputReader();
        glfwSetKeyCallback(windowIdentifier, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == exitKey && action == GLFW_RELEASE ){
                    glfwSetWindowShouldClose(window, GL_TRUE);
                }
                else {
                    inputreader.keyInvoke(key, scancode, action, mods);
                }
            }
        });
    }
    
    // Main game loop with fixed time-step ----------------------------------------------------- //
    private void mainLoop() {
        startup();
        
        double reference = GLFW.glfwGetTime();
        
        while(isRunning() && glfwWindowShouldClose(windowIdentifier) == GL_FALSE){
            resizeViewport();
            
            updateLogic(threadSleepDuration);
            draw(threadSleepDuration);
            
            glfwSwapBuffers(windowIdentifier);
            glfwPollEvents();
            try { 
                Thread.sleep(threadSleepDuration - (long)(GLFW.glfwGetTime() - reference)/1000l);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            reference = GLFW.glfwGetTime();
        }
        teardown();

    }

    // Status and control hooks ---------------------------------------------------------------- //
    synchronized public void quit() {
        running = false;
    }
    synchronized public boolean isRunning(){
        return running;
    }
}
// --------------------------------------------------------------------------------------------- //
