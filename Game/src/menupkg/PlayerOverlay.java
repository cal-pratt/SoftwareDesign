package menupkg;

import objectpkg.Object2DFactory;
import creaturepkg.Player;
import eventpkg.GameEvents.*;
import graphicspkg.GraphicsManager;

public class PlayerOverlay extends AMenu {
    private IPlayerEventListener callBack = new IPlayerEventListener(){
		@Override
		public void actionPerformed(PlayerEventPublisher sender, Player e) {
			updateHealthBar();
			updateExpBar();
		}
	};
	
    private Player player;
    private MenuSprite health;
    private MenuSprite nohealth;
    private MenuSprite exp;
    private MenuSprite noexp;
		
	public PlayerOverlay(GraphicsManager gm, Player player) {
		super(gm);
		this.player = player;

		exp = new MenuSprite(gm, Object2DFactory.getHealth(), 
				width*57/100, height*93/100f, 0, height*7/100f);
		
		noexp = new MenuSprite(gm, Object2DFactory.getNoHealth(), 
		        width*57/100, height*93/100f, expWidth(), height*7/100f);
		
		add(exp);
		add(noexp);
		
        add(new MenuSprite(
                gm, Object2DFactory.getBanner(), 
                0,0, width, height));
        
		health = new MenuSprite(gm, Object2DFactory.getHealth(), 
		        width/100, height*9/10f, healthWidth(), height/10f);
		
		nohealth = new MenuSprite(gm, Object2DFactory.getNoHealth(), 
		        width/100, height*9/10f, healthWidth(), height/10f);
		

		add(nohealth);
		add(health);
	}
	
	private float healthWidth(){
		return width/2.15f;
	}
	private float expWidth(){
		return width/3.5f;
	}
	
	private void updateHealthBar(){
		health.setSize(healthWidth() * player.getCurrentHealth()/ (float) player.getMaxHealth(), health.getHeight());
	}
	
	private void updateExpBar(){
		exp.setSize(expWidth() * player.getExperience()/ (float) player.getExperienceToLevel(), exp.getHeight());
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
