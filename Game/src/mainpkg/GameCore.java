package mainpkg;


// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class GameCore extends ACore {
    
    // Game state ------------------------------------------------------------------------------ //
    
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
    }

    @Override
    protected void draw(long timePassed) {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
//---------------------------------------------------------------------------------------------- //
