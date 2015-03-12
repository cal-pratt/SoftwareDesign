package menupkg;

import objectpkg.Object2DFactory;
import creaturepkg.Player;
import eventpkg.GameEvents.*;
import graphicspkg.GraphicsManager;

public class PlayerOverlay extends AMenu {
    private IPlayerEventListener callBack;
	
    private Player player;
    private MenuSprite health;
    private MenuSprite nohealth;
	
    private float fullwidth; 
		
	public PlayerOverlay(GraphicsManager gm, Player player) {
		super(gm);
		this.player = player;
		
		fullwidth = width/2.15f;
		
		callBack = new IPlayerEventListener(){
			@Override
			public void actionPerformed(PlayerEventPublisher sender, Player e) {
				updateHealthBar();
			}
		};

        add(new MenuSprite(
                gm, Object2DFactory.getBanner(), 
                0,0, width, height));
        
		health = new MenuSprite(gm, Object2DFactory.getHealth(), 
		        width/100, height*9/10f, fullwidth, height/10f);
		
		nohealth = new MenuSprite(gm, Object2DFactory.getNoHealth(), 
		        width/100, height*9/10f, fullwidth, height/10f);

		add(nohealth);
		add(health);
	}
	
	private void updateHealthBar(){
		health.setSize(fullwidth * player.getCurrentHealth()/ (float) player.getMaxHealth(), health.getHeight());
	}
	
	@Override
	public void reset() {
		
	}

	@Override
	public void hide() {
        if (!hidden){
            super.hide();
    		player.getEventPublisher().unsubscribe(callBack);
        }
	}

	@Override
	public void show() {
        if (hidden){
            super.show();
    		updateHealthBar();
    		player.getEventPublisher().subscribe(callBack);
        }
	}

}
