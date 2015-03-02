package mainpkg;



// Class definition ---------------------------------------------------------------------------- //
public class Launcher {
    // Main program method --------------------------------------------------------------------- //
    public static void main(String[] args) {
    	gmCore().run();
    }
    
    // Core abstraction ------------------------------------------------------------------------ //
    private static ACore concreteCore() {
        return new GameCore();
    }
    
    private static ACore gmCore() {
        return new GmExampleCore();
    }
}
// --------------------------------------------------------------------------------------------- //
