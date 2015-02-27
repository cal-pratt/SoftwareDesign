package inputpkg;

// Interface definition ------------------------------------------------------------------------ //
public interface IKeyboardInput {
    
    // Accessors and Mutators ------------------------------------------------------------------ //
    public void keyInvoke(int key, int scancode, int action, int mods);
    
    public int getAction(int key);
}
// --------------------------------------------------------------------------------------------- //
