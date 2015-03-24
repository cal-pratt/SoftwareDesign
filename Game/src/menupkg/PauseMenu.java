package menupkg;

import eventpkg.GameEvents.*;
import objectpkg.Object2DFactory;
import graphicspkg.GraphicsManager;
import inputpkg.UserInput;

public class PauseMenu extends AMenu {
    private MenuButton continuegame;
    
    public PauseMenu(GraphicsManager gm, UserInput input) {
        super(gm);
        
        continuegame = new MenuButton(gm, input,
                Object2DFactory.getContinueReleased(), Object2DFactory.getContinueClicked(),
                width/2f - height/7f, height/3f, width/5f, height/5f);
        
        add(new MenuSprite(gm, Object2DFactory.getPause(), width*1f/3.8f, height*2f/3.8f, width/2f, height*1f/3f));
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
