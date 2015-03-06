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
    public static ATexObject2D getSpace(){
        return new Space();
    }
    public static ATexObject2D getNewgameClicked(){
    	return new NewgameClicked();
    }
    public static ATexObject2D getContinueClicked(){
    	return new ContinueClicked();
    }

    public static ATexObject2D getHealth(){
    	return new HealthBar();
    }

    public static ATexObject2D getNoHealth(){
    	return new NoHealthBar();
    }
}
