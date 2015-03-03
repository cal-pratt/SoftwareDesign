package mainpkg;

import inputpkg.IUserInput;

// 3rd Part Imports ---------------------------------------------------------------------------- //
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;


// Class definition ---------------------------------------------------------------------------- //
public class GameCore extends ACore {
    
    // Game state ------------------------------------------------------------------------------ //
    private IUserInput prevInput;
    
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
        IUserInput currInput = inputreader.getKeyBoardInput();
        prevInput = currInput;
    }

    @Override
    protected void draw(long timePassed) {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
//---------------------------------------------------------------------------------------------- //
