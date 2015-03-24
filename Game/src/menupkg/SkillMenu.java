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
    
    
    //TODO add IPlayerEventListener callback variables for updating this with player info
    //private IPlayerEventListener callBack;
    
    //TODO add local variables for current player info to avoid constant gets
    int skillPoints = player.getSkillPoints();
        
    public SkillMenu(GraphicsManager gm, UserInput input, Player player) {
        super(gm);
        this.player = player;
        this.input = input;
        
//        callBack = new IPlayerEventListener(){
//            @Override
//            public void actionPerformed(PlayerEventPublisher sender, Player e) {
//                updatePlayerInfo();
//            }
//        };
        
        returnToGame = new MenuButton(gm, input, 
        		Object2DFactory.getSkillExit(), Object2DFactory.getSkillExit(),
                width/1.13f, height/1.75f, width/10f, height/10f);
        
        fireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillFire(), Object2DFactory.getSkillFire(), 
        		width/1550f, height - height/6.86f, width/15f, height/7f);
        iceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillIce(), Object2DFactory.getSkillIce(), 
        		0, height - height/3.095f, width/15f, height/7f);
        strongerFireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillArrowRed(), Object2DFactory.getSkillArrowRed(), 
        		width/5.2f, height - height/6.86f, width/18f, height/7f);
        strongerIceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillArrowBlue(), Object2DFactory.getSkillArrowBlue(), 
        		width/5.2f, height - height/3.06f, width/18f, height/7f);
        doubleFireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillDoubleRed(), Object2DFactory.getSkillDoubleRed(), 
        		width/2.68f, height - height/6.95f, width/6.9f, height/7f);
        doubleIceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillDoubleBlue(), Object2DFactory.getSkillDoubleBlue(),
        		width/2.62f, height - height/3.09f, width/7.1f, height/7f);
        tripleFireSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillThreeRed(), Object2DFactory.getSkillThreeRed(), 
        		width/1.55f, height - height/6.86f, width/5f, height/7f);
        tripleIceSkill = new MenuButton(gm, input, 
        		Object2DFactory.getSkillThreeBlue(), Object2DFactory.getSkillThreeBlue(), 
        		width/1.55f, height - height/3.099f, width/5f, height/7f);
        starSkill = new MenuButton(gm, input,
        		Object2DFactory.getSkillStar(), Object2DFactory.getSkillStar(), 
        		width/1.13f, height - height/3.99f, width/9f, height/5.75f);
        
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
