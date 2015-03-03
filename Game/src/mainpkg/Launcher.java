package mainpkg;



// Class definition ---------------------------------------------------------------------------- //
public class Launcher {
    // Main program method --------------------------------------------------------------------- //
    public static void main(String[] args) {
    	multiProgCore().run();
    }
    
    // Core abstraction ------------------------------------------------------------------------ //
    private static ACore concreteCore() {
        return new GameCore();
    }
    
    private static ACore gmCore() {
        return new GmExampleCore();
    }

    private static ACore texCore() {
        return new TexExampleCore();
    }
    
    private static ACore multiProgCore() {
        return new MultiProgramExampleCore();
    }
}
// --------------------------------------------------------------------------------------------- //
