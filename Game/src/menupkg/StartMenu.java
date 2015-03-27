package menupkg;

import eventpkg.GameEvents.*;
import objectpkg.Object2DFactory;
import graphicspkg.GraphicsManager;
import inputpkg.UserInput;

public class StartMenu extends AMenu {
	private MenuButton continuegame;
	private MenuButton newgame;
	
	public StartMenu(GraphicsManager gm, UserInput input) {
		super(gm);
        
        newgame = new MenuButton(gm, input,
                Object2DFactory.getNewgameReleased(), Object2DFactory.getNewgameClicked(),
                width/3.9f, height/2.2f, width/5f, height/5f);
        
        continuegame = new MenuButton(gm, input,
                Object2DFactory.getContinueReleased(), Object2DFactory.getContinueClicked(),
                width/1.9f, height/2.2f, width/5f, height/5f);
		
		add(new MenuSprite(gm, Object2DFactory.getSpace(), 0f, 0f, width, height));
		add(newgame);
		add(continuegame);
	}
	
	public ButtonEventPublisher getContinueButtonEvent(){
	    return continuegame.getEventPublisher();
	}
    
    public ButtonEventPublisher getNewgameButtonEvent(){
        return newgame.getEventPublisher();
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
