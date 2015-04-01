/** SkillMenu
 * The menu that contains all of the different skills the player can purchase with skill points
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package menupkg;

import inputpkg.UserInput;
import objectpkg.Object2DFactory;
import creaturepkg.Player;
import eventpkg.GameEvents.*;
import graphicspkg.GraphicsManager;



public class SkillMenu extends AMenu {
    
    private Player player;
    private UserInput input;
    private boolean menuOpen = false;
    
    private MenuButton returnToGame;
    private MenuButton fireSkill;
    private MenuButton iceSkill;
    private MenuButton strongerFireSkill;
    private MenuButton strongerIceSkill;
    private MenuButton doubleFireSkill;
    private MenuButton doubleIceSkill;
    private MenuButton tripleFireSkill;
    private MenuButton tripleIceSkill;
    private MenuButton starSkill;
    
        
    public SkillMenu(GraphicsManager gm, UserInput input, Player player) {
        super(gm);
        this.player = player;
        this.input = input;
        
        //All of the buttons contained within the skill menu
        
        returnToGame = new MenuButton(gm, input, 
        		Object2DFactory.getSkillExit(), Object2DFactory.getSkillExit(),
                width/1.13f, height/1.75f, width/10f, height/10f);
        
        fireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillFire(), Object2DFactory.getSkillFireClicked(), 
        		width/1550f, height - height/6.86f, width/15f, height/7f);
        iceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillIce(), Object2DFactory.getSkillIceClicked(), 
        		0, height - height/3.095f, width/15f, height/7f);
        strongerFireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillArrowRed(), Object2DFactory.getSkillArrowRedClicked(), 
        		width/5.22f, height - height/6.86f, width/18f, height/7f);
        strongerIceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillArrowBlue(), Object2DFactory.getSkillArrowBlueClicked(), 
        		width/5.22f, height - height/3.06f, width/18f, height/7f);
        doubleFireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillDoubleRed(), Object2DFactory.getSkillDoubleRedClicked(), 
        		width/2.67f, height - height/6.95f, width/6.9f, height/7f);
        doubleIceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillDoubleBlue(), Object2DFactory.getSkillDoubleBlueClicked(),
        		width/2.62f, height - height/3f, width/7.1f, height/7f);
        tripleFireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillThreeRed(), Object2DFactory.getSkillThreeRedClicked(), 
        		width/1.539f, height - height/7.4f, width/4.9f, height/7.5f);
        tripleIceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillThreeBlue(), Object2DFactory.getSkillThreeBlueClicked(), 
        		width/1.539f, height - height/3.07f, width/4.93f, height/7.5f);
        starSkill = new MenuButton(gm, input,
        		Object2DFactory.getSkillStar(), Object2DFactory.getSkillStarClicked(), 
        		width/1.123f, height - height/4.21f, width/9.43f, height/6.75f);
        
        add(new MenuSprite(gm, Object2DFactory.getSkillMenu(), 0, height - height/3f, width, height/3f));
        
        add(returnToGame);
        add(fireSkill);
        add(iceSkill);
        add(strongerFireSkill);
        add(strongerIceSkill);
        add(doubleFireSkill);
        add(doubleIceSkill);
        add(tripleFireSkill);
        add(tripleIceSkill);
        add(starSkill);
        
    }
    
    //TODO add getter methods for button events (callbacks) like opening/ closing menu
    public ButtonEventPublisher getReturnToGameButtonEvent(){
        return returnToGame.getEventPublisher();
    }
    public ButtonEventPublisher getFireSkillButtonEvent(){
        return fireSkill.getEventPublisher();
    }
    public ButtonEventPublisher getIceSkillButtonEvent(){
        return iceSkill.getEventPublisher();
    }
    public ButtonEventPublisher getStrongerFireSkillButtonEvent(){
        return strongerFireSkill.getEventPublisher();
    }
    public ButtonEventPublisher getStrongerIceSkillButtonEvent(){
        return strongerIceSkill.getEventPublisher();
    }
    public ButtonEventPublisher getDoubleFireSkillButtonEvent(){
        return doubleFireSkill.getEventPublisher();
    }
    public ButtonEventPublisher getDoubleIceSkillButtonEvent(){
        return doubleIceSkill.getEventPublisher();
    }
    public ButtonEventPublisher getTripleFireSkillButtonEvent(){
        return tripleFireSkill.getEventPublisher();
    }
    public ButtonEventPublisher getTripleIceSkillButtonEvent(){
        return tripleIceSkill.getEventPublisher();
    }
    public ButtonEventPublisher getStarSkillButtonEvent(){
        return starSkill.getEventPublisher();
    }
    
    private void updatePlayerInfo(){
        // TODO update menu locals with player info and other logic if required
    }

    //TODO create other functions for callbacks you make
    
    @Override
    public void reset() {
        // TODO reset all locals to default values (the way they were in the constructor) if changed. 
    }

    @Override
    public void hide() {
        if (!hidden){
            super.hide();
            menuOpen = false;
            //player.getEventPublisher().unsubscribe(callBack);
            // TODO unsubscribe to all of the event publishers with the callbacks you made
        }
    }

    @Override
    public void show() {
        if (hidden){
            super.show();
            //updatePlayerInfo();
            menuOpen = true;
            // TODO call any other update functions to refresh
            // This is because they have previously been unsubed to the normal events
            //player.getEventPublisher().subscribe(callBack);
            // TODO subscribe to all of the event publishers with the callbacks you made
        }
    }
    
    public boolean isMenuOpen() {
    	return menuOpen;
    }

}
