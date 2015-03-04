package objectpkg;

public class Object2DFactory {
    public static ATexObject2D getContinue(){
        return new Continue();
    } 
    public static ATexObject2D getBanner(){
        return new Banner();
    } 
    public static ATexObject2D getNewgame(){
        return new Newgame();
    }
}
