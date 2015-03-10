package mainpkg;



// Class definition ---------------------------------------------------------------------------- //
public class Launcher {
    // Main program method --------------------------------------------------------------------- //
    public static void main(String[] args) {
        skillMenu().run();
    }
    
    // Core abstraction ------------------------------------------------------------------------ //
    @SuppressWarnings("unused")
    private static ACore multiProgCore() {
        return new MainCore();
    }
    
    private static ACore skillMenu() {
        return new SkillMenuCore();
    }
}
// --------------------------------------------------------------------------------------------- //
