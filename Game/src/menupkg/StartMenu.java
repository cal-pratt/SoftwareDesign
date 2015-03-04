package menupkg;

import eventpkg.ButtonEventPublisher;
import eventpkg.IButtonEventListener;
import objectpkg.Object2DFactory;
import graphicspkg.GraphicsManager;
import inputpkg.UserInput;

public class StartMenu extends Menu {
	private GraphicsManager gm;
	private UserInput input;

	boolean continuePressed = false;
	boolean newPressed = false;
	
	public StartMenu(GraphicsManager gm, UserInput input,
			float actualPosX, float actualPosY, float actualWidth, float actualHeight,
			float repPosX, float repPosY, float repWidth, float repHeight) {
		super(actualPosX, actualPosY, actualWidth, actualHeight, repPosX, repPosY, repWidth, repHeight);
		
		this.gm = gm;
		this.input = input;
		
		MenuButton newgame = new MenuButton(gm, input,
        		Object2DFactory.getNewgame(), Object2DFactory.getContinue(),
        		repWidth/2 - repHeight/7, repHeight/2, repWidth/5, repHeight/5);
		
		newgame.getEventPublisher().subscribe(new IButtonEventListener(){
			@Override
			public void actionPerformed(ButtonEventPublisher sender, Object e) {
				newPressed = true;
			}
		});
		
		MenuButton continuegame = new MenuButton(gm, input,
        		Object2DFactory.getContinue(), Object2DFactory.getNewgame(),
        		repWidth/2 - repHeight/7, repHeight/2 - repHeight/5, repWidth/5, repHeight/5);
		
		continuegame.getEventPublisher().subscribe(new IButtonEventListener(){
			@Override
			public void actionPerformed(ButtonEventPublisher sender, Object e) {
				continuePressed = true;
			}
		});
		
		add(new MenuSprite(gm, Object2DFactory.getSpace(), 0f, 0f, repWidth, repHeight));
		add(newgame);
		add(continuegame);
	}
	public boolean isContinuePressed(){
		return continuePressed;
	}
	
	public boolean isNewPressed(){
		return newPressed;
	}
	public void clearContinuePressed(){
		continuePressed = false;
	}
	
	public void clearNewPressed(){
		newPressed = false;
	}
}
