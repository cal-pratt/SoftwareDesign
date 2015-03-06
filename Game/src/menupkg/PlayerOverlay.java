package menupkg;

import objectpkg.Object2DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import creaturepkg.Player;
import eventpkg.IPlayerEventListener;
import eventpkg.PlayerEventPublisher;
import graphicspkg.GraphicsManager;

public class PlayerOverlay extends AMenu {
	IPlayerEventListener callBack;
	
	GraphicsManager gm;
	Player player;
	MenuSprite health;
	MenuSprite nohealth;
	MenuSprite banner;
	
	float fullwidth; 
		
	public PlayerOverlay(GraphicsManager gm, Player player, float actualPosX, float actualPosY, float actualWidth,
			float actualHeight, float repPosX, float repPosY, float repWidth,
			float repHeight) {
		super(actualPosX, actualPosY, actualWidth, actualHeight, repPosX, repPosY,
				repWidth, repHeight);
		this.gm = gm;
		this.player = player;
		
		fullwidth = repWidth/2;
		
		callBack = new IPlayerEventListener(){
			@Override
			public void actionPerformed(PlayerEventPublisher sender, Object e) {
				updateSize();
			}
		};
		
		health = new MenuSprite(gm, Object2DFactory.getHealth(), 
				(repWidth - fullwidth)/2, 0f, fullwidth, 50);
		nohealth = new MenuSprite(gm, Object2DFactory.getNoHealth(), 
				(repWidth - fullwidth)/2, 0f, fullwidth, 50);
		
		add(nohealth);
		add(health);

		add(banner = new MenuSprite(
				gm, Object2DFactory.getBanner(), 
				0,0, repWidth, repHeight));
	}
	
	private void updateSize(){
		health.setSize(fullwidth * player.getCurrentHealth()/ (float) player.getMaxHealth(), health.getHeight());
	}
	
	public void update(){
		updateOrthographic(new Matrix4f());
	}
	
	@Override
	public void reset() {
		
	}

	@Override
	public void hide() {
		player.getEventPublisher().unsubscribe(callBack);
		health.hide();
		nohealth.hide();
		banner.hide();
	}

	@Override
	public void show() {
		updateSize();
		player.getEventPublisher().subscribe(callBack);
		health.show();
		nohealth.show();
		banner.show();
	}

}
