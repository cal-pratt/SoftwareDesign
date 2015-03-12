package mainpkg;



// Class definition ---------------------------------------------------------------------------- //
public class Launcher {
    // Main program method --------------------------------------------------------------------- //
    public static void main(String[] args) {
        mainCore().run();
    }
    
    // Core abstraction ------------------------------------------------------------------------ //
    private static ACore mainCore() {
        return new MainCore();
    }
    
    private static ACore skillMenu() {
        return new SkillMenuCore();
    }
}
// --------------------------------------------------------------------------------------------- //
