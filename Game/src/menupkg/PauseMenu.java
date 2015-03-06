package menupkg;

import eventpkg.ButtonEventPublisher;
import objectpkg.Object2DFactory;
import graphicspkg.GraphicsManager;
import inputpkg.UserInput;

public class PauseMenu extends AMenu {
    private MenuButton continuegame;
    
    public PauseMenu(GraphicsManager gm, UserInput input) {
        super(gm);
        
        continuegame = new MenuButton(gm, input,
                Object2DFactory.getContinueReleased(), Object2DFactory.getContinueClicked(),
                width/2 - height/7, height/2, width/5, height/5);
        
        add(new MenuSprite(gm, Object2DFactory.getPause(), width*1f/4f, height*2f/3f, width*2f/4f, height*1f/6f));
        add(continuegame);
    }
    
    public ButtonEventPublisher getContinueButtonEvent(){
        return continuegame.getEventPublisher();
    }
    
    public void reset(){
    }
    
    public void hide(){
        if (!hidden){
            super.hide();
        }
    }
    
    public void show(){
        if (hidden){
            super.show();
        }
    }
}
