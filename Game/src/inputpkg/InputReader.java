package inputpkg;

// Class definition ---------------------------------------------------------------------------- //
public class InputReader {
    
    // Private copy of input state ------------------------------------------------------------- //
    private UserInput userInput;
    
    // Initialize default input ---------------------------------------------------------------- //
    public InputReader(){
        userInput = new UserInput();
    }
    
    // Accessors and Mutators ------------------------------------------------------------------ //
    synchronized public void keyInvoke(int key, int scancode, int action, int mods){
        userInput.keyInvoke(key, scancode, action, mods);
    }
    
    public void mouseButtonInvoke(int button, int action, int mods) {
    	 userInput.keyInvoke(button, 0, action, mods);
	}
    
    public void cursorPosInvoke(double x, double y) {
    	userInput.cursorPosInvoke((float)x, (float)y);
	}
    
    synchronized public UserInput getKeyBoardInput(){
        return userInput.clone();
    }
}
// --------------------------------------------------------------------------------------------- //
