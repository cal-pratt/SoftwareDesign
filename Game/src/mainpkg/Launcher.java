package mainpkg;



// Class definition ---------------------------------------------------------------------------- //
public class Launcher {
    // Main program method --------------------------------------------------------------------- //
    public static void main(String[] args) {
    	multiProgCore().run();
    }
    
    // Core abstraction ------------------------------------------------------------------------ //
    @SuppressWarnings("unused")
    private static ACore concreteCore() {
        return new GameCore();
    }
    
    @SuppressWarnings("unused")
    private static ACore gmCore() {
        return new GmExampleCore();
    }

    @SuppressWarnings("unused")
    private static ACore texCore() {
        return new TexExampleCore();
    }
    
    private static ACore multiProgCore() {
        return new MainCore();
    }
}
// --------------------------------------------------------------------------------------------- //
