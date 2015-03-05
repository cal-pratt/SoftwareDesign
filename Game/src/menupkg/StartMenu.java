package menupkg;

import eventpkg.ButtonEventPublisher;
import eventpkg.IButtonEventListener;
import objectpkg.Object2DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import graphicspkg.GraphicsManager;
import inputpkg.UserInput;

public class StartMenu extends AMenu {
	private GraphicsManager gm;
	private UserInput input;

	boolean hidden = true;
	
	MenuButton continuegame;
	boolean continuePressed = false;
	IButtonEventListener continueCallback;
	
	MenuButton newgame;
	boolean newPressed = false;	
	IButtonEventListener newCallback;
	
	public StartMenu(GraphicsManager gm, UserInput input,
			float actualPosX, float actualPosY, float actualWidth, float actualHeight,
			float repPosX, float repPosY, float repWidth, float repHeight) {
		super(actualPosX, actualPosY, actualWidth, actualHeight, repPosX, repPosY, repWidth, repHeight);
		
		this.gm = gm;
		this.input = input;
		
		newgame = new MenuButton(gm, input,
        		Object2DFactory.getNewgame(), Object2DFactory.getContinue(),
        		repWidth/2 - repHeight/7, repHeight/2, repWidth/5, repHeight/5);
		
		newCallback = new IButtonEventListener(){
			@Override
			public void actionPerformed(ButtonEventPublisher sender, Object e) {
				newPressed = true;
			}
		};
		
		continuegame = new MenuButton(gm, input,
        		Object2DFactory.getContinue(), Object2DFactory.getNewgame(),
        		repWidth/2 - repHeight/7, repHeight/2 - repHeight/5, repWidth/5, repHeight/5);
		
		continueCallback = new IButtonEventListener(){
			@Override
			public void actionPerformed(ButtonEventPublisher sender, Object e) {
				continuePressed = true;
			}
		};
		
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
	
	public void update(){
		updateOrthographic(new Matrix4f());
	}
	
	public void reset(){
		newPressed = false;
		continuePressed = false;
	}
	
	public void hide(){
		if (!hidden){
			hidden = true;
			for(IMenuItem item : items){
				item.hide();
			}
			newgame.getEventPublisher().unsubscribe(newCallback);
			continuegame.getEventPublisher().unsubscribe(continueCallback);
		}
	}
	
	public void show(){
		if (hidden){
			hidden = false;
			for(IMenuItem item : items){
				item.show();
			}
			newgame.getEventPublisher().subscribe(newCallback);
			continuegame.getEventPublisher().subscribe(continueCallback);
		}
	}
}
