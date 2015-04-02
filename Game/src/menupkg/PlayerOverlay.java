/** PlayerOverlay
 * The overlay on top of the screen while in game-play. Shows health, level, and experience. 
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package menupkg;

import inputpkg.UserInput;

import java.util.ArrayList;
import java.util.List;

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
			updateLevelNumber();
		}
	};
	
    private Player player;
    private UserInput input;
    private MenuSprite banner;
    private MenuSprite health;
    private MenuSprite nohealth;
    private MenuSprite exp;
    private MenuSprite noexp;
    private MenuButton skillsButton;
    private List<MenuSprite> tensDigit = new ArrayList<MenuSprite>();
    private List<MenuSprite> onesDigit = new ArrayList<MenuSprite>();
		
	public PlayerOverlay(GraphicsManager gm, UserInput input, Player player) {
		super(gm);
		this.player = player;
		this.input = input;
		
        banner = new MenuSprite(
                gm, Object2DFactory.getBanner(), 
                0,0, width, height);
        
        skillsButton = new MenuButton(gm, input, 
        		Object2DFactory.getSkillsButton(), Object2DFactory.getSkillsButton(),
                width/1.13f, height/1.067f, width/10f, height/16f);
        
		health = new MenuSprite(gm, Object2DFactory.getHealth(), 
		        width/100, height*9/10f, healthWidth(), height/10f);
		
		nohealth = new MenuSprite(gm, Object2DFactory.getNoHealth(), 
		        width/100, height*9/10f, healthWidth(), height/10f);

		exp = new MenuSprite(gm, Object2DFactory.getHealth(), 
				width*58.7f/100, height*93/100f, 0, height*1f/10f);
		
		noexp = new MenuSprite(gm, Object2DFactory.getNoHealth(), 
		        width*58.7f/100, height*93/100f, expWidth(), height*1f/10f);
        
		add(banner);
		add(nohealth);
		add(health);
		add(exp);
		add(noexp);
		add(skillsButton);
		
		for (int i = 0; i < 10; i++){
        	tensDigit.add(new MenuSprite(gm, Object2DFactory.getNumber(i), width/1.88f, height/1.07315f, width/35, height/13));
        	add(tensDigit.get(i));
        }
        for (int i = 0; i < 10; i++){
        	onesDigit.add(new MenuSprite(gm, Object2DFactory.getNumber(i), width/1.802f, height/1.07315f, width/35, height/13));
        	add(onesDigit.get(i));
        }
		
	}
	
	public ButtonEventPublisher getSkillsButtonEvent(){
        return skillsButton.getEventPublisher();
    }
	
	private float healthWidth(){
		return width/2.15f;
	}
	private float expWidth(){
		return width/3.2f;
	}
	
	private void updateHealthBar(){
		health.setSize(healthWidth() * player.getCurrentHealth()/ (float) player.getMaxHealth(), health.getHeight());
	}
	
	private void updateExpBar(){
		exp.setSize(expWidth() * player.getExperience()/ (float) player.getExperienceToLevel(), exp.getHeight());
	}
	
	private void updateLevelNumber(){
		for (int i = 0; i < 10; i++){
        	tensDigit.get(i).hide();
        }
        for (int i = 0; i < 10; i++){
        	onesDigit.get(i).hide();
        }

        tensDigit.get(player.getLevel()/10).show();
        onesDigit.get(player.getLevel()%10).show();
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
