package mainpkg;



// Class definition ---------------------------------------------------------------------------- //
public class Launcher {
    // Main program method --------------------------------------------------------------------- //
    public static void main(String[] args) {
        concreteCore().run();
    }
    
    // Core abstraction ------------------------------------------------------------------------ //
    private static ACore concreteCore() {
        return new GameCore();
    }
}
// --------------------------------------------------------------------------------------------- //
