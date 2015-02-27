package inputpkg;

// Class definition ---------------------------------------------------------------------------- //
public class InputReader {
    
    // Private copy of input state ------------------------------------------------------------- //
    private KeyboardInput keyboardInput;
    
    // Initialize default input ---------------------------------------------------------------- //
    public InputReader(){
        keyboardInput = new KeyboardInput();
    }
    
    // Accessors and Mutators ------------------------------------------------------------------ //
    synchronized public void keyInvoke(int key, int scancode, int action, int mods){
        keyboardInput.keyInvoke(key, scancode, action, mods);
    }
    
    synchronized public KeyboardInput getKeyBoardInput(){
        return keyboardInput.clone();
    }
}
// --------------------------------------------------------------------------------------------- //
