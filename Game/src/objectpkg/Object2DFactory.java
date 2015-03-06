package objectpkg;

public class Object2DFactory {
    public static ATexObject2D getContinueReleased(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/continue.png";
            }
        };
    } 
    public static ATexObject2D getBanner(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/banner.png";
            }
        };
    } 
    public static ATexObject2D getNewgameReleased(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/newgame.png";
            }
        };
    }
    public static ATexObject2D getSpace(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/space.png";
            }
        };
    }
    public static ATexObject2D getNewgameClicked(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/newgameClicked.png";
            }
        };
    }
    public static ATexObject2D getContinueClicked(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/continueClicked.png";
            }
        };
    }

    public static ATexObject2D getHealth(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/health.png";
            }
        };
    }

    public static ATexObject2D getNoHealth(){
    	return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/nohealth.png";
            }
        };
    }
    

    public static ATexObject2D getPause(){
        return new ATexObject2D(){
            @Override
            public String getFilename() {
                return "sprites/pause.png";
            }
        };
    }
}
