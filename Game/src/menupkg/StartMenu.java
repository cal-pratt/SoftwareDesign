package menupkg;

import eventpkg.ButtonEventPublisher;
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
                width/2 - height/7, height/2, width/5, height/5);
        
        continuegame = new MenuButton(gm, input,
                Object2DFactory.getContinueReleased(), Object2DFactory.getContinueClicked(),
                width/2 - height/7, height/2 - height/5, width/5, height/5);
		
		//add(new MenuSprite(gm, Object2DFactory.getSpace(), 0f, 0f, width, height));
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
