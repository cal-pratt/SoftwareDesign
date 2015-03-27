package mainpkg;

// Local Project Imports ----------------------------------------------------------------------- //
import inputpkg.UserInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

// Class definition ---------------------------------------------------------------------------- //
public abstract class ACore {

    // Customizable startup variables ---------------------------------------------------------- //
    protected float windowWidth = 300;
    protected float windowHeight = 300;
    protected int windowFullscreen = GL_FALSE;
    protected String windowTitle = "Default ACore";
    protected int exitKey = GLFW_KEY_ESCAPE;
    protected long threadSleepDuration = 20l;
    
    // Major components ------------------------------------------------------------------------- //
    protected UserInput input;
    
    protected float windowRatio;
    private GLFWErrorCallback errorCallback;
    
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseCallback;
    private GLFWCursorPosCallback cursorCallback;
    
    private long windowIdentifier; 

    // State ----------------------------------------------------------------------------------- //
    private boolean running = true;
    private boolean reset = false;
    
    // Abstract functions ---------------------------------------------------------------------- //
    protected abstract void startup();
    protected abstract void teardown();
    protected abstract void resizeViewport();
    protected abstract void updateActions(long timePassed);
    protected abstract void draw(long timePassed);
    
    // Main application execution -------------------------------------------------------------- //
    public void run() {
        try {
            System.out.println("Creating window: " + windowTitle);
            createWindow();
            createInput();
            GLContext.createFromCurrent();
            
            while(isRunning()){
            	reset = false;
            	mainLoop();
            }
            
            System.out.println("Exiting window: " + windowTitle);
            glfwDestroyWindow(windowIdentifier);
            keyCallback.release();
            mouseCallback.release();
            cursorCallback.release();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            glfwTerminate();
            errorCallback.release();
        }
        
    }

    // Load OpenGl and populate window --------------------------------------------------------- //
    private void createWindow() {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        if ( glfwInit() != GL_TRUE ){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, windowFullscreen == GL_TRUE ? GL_FALSE : GL_TRUE );
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        if (windowFullscreen == GL_TRUE){
            windowIdentifier = glfwCreateWindow((int)windowWidth, (int)windowHeight, windowTitle, 
                    glfwGetPrimaryMonitor(), NULL);
        }
        else{
            windowIdentifier = glfwCreateWindow((int)windowWidth, (int)windowHeight, windowTitle, 
                    NULL, NULL);
        }
        if ( windowIdentifier == NULL ){
            throw new RuntimeException("Failed to create the GLFW window");
        }
 
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        if(windowFullscreen == GL_FALSE){
            glfwSetWindowPos(
                windowIdentifier,
                (GLFWvidmode.width(vidmode) - (int)windowWidth) / 2,
                (GLFWvidmode.height(vidmode) - (int)windowHeight) / 2
            );
        }
        glfwMakeContextCurrent(windowIdentifier);
        glfwSwapInterval(1);
        glfwShowWindow(windowIdentifier);
        
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(windowIdentifier, width, height);
        windowWidth = width.get(); windowHeight = height.get();
        windowRatio = windowWidth / windowHeight;
        
    }
    
    // Link OpenGl input callback to input-reader ---------------------------------------------- //
    private void createInput() {
        input = new UserInput();
        glfwSetKeyCallback(windowIdentifier, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == exitKey && action == GLFW_RELEASE ){
                    glfwSetWindowShouldClose(window, GL_TRUE);
                    quit();
                }
                else if (window == windowIdentifier) {
                    input.keyInvoke(key, scancode, action, mods);
                }
            }
        });
        glfwSetMouseButtonCallback(windowIdentifier, mouseCallback = new GLFWMouseButtonCallback() {
			@Override
			public void invoke(long window, int button, int action, int mods) {
				if (window == windowIdentifier){
				    input.keyInvoke(button, 0, action, mods);
				}
			}
        });
        glfwSetCursorPosCallback(windowIdentifier, cursorCallback = new GLFWCursorPosCallback() {
			@Override
			public void invoke(long window, double x, double y) {
				if (window == windowIdentifier){
					input.cursorPosInvoke((float)x, (float)y);
				}
			}
        });
    }
    
    // Main game loop with fixed time-step ----------------------------------------------------- //
    private void mainLoop() {
        startup();
        
        double reference = glfwGetTime();
        
        while(isRunning() && glfwWindowShouldClose(windowIdentifier) == GL_FALSE){
            
        	IntBuffer width = BufferUtils.createIntBuffer(1);
        	IntBuffer height = BufferUtils.createIntBuffer(1);
        	glfwGetFramebufferSize(windowIdentifier, width, height);
        	windowWidth = width.get(); windowHeight = height.get();
            windowRatio = windowWidth / windowHeight;
            resizeViewport();

            
            updateActions(threadSleepDuration);
            if(reset){
            	break;
            }
            draw(threadSleepDuration);
            
            glfwSwapBuffers(windowIdentifier);
            glfwPollEvents();
            input.pollJoysticks();
            try { 
            	//System.out.println(threadSleepDuration - (long)(glfwGetTime() - reference)/1000l);
                Thread.sleep(threadSleepDuration - (long)(glfwGetTime() - reference)/1000L);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                running = false;
                
            }
            reference = glfwGetTime();
        }
        teardown();

    }

    // Status and control hooks ---------------------------------------------------------------- //
    public void quit() {
        running = false;
    }
    public void resetGame() {
        reset = true;
    }
    public boolean isRunning(){
        return running;
    }
}
// --------------------------------------------------------------------------------------------- //

