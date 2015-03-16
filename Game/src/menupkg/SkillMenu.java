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
    
    //TODO add private MenuSprite's for menu;
    
    //TODO add IPlayerEventListener callback variables for updating this with player info
    private IPlayerEventListener callBack;
    
    //TODO add local variables for current player info to avoid constant gets
        
    public SkillMenu(GraphicsManager gm, UserInput input, Player player) {
        super(gm);
        this.player = player;
        this.input = input;
        
        callBack = new IPlayerEventListener(){
            @Override
            public void actionPerformed(PlayerEventPublisher sender, Player e) {
                updatePlayerInfo();
            }
        };
        
        
        add(new MenuSprite(gm, Object2DFactory.getSkillMenu(), width*1f/4f, height*2f/3f, width*2f/4f, height*1f/6f));
        
        returnToGame = new MenuButton(gm, input,
        		Object2DFactory.getSkillExit(), Object2DFactory.getSkillExit(),
                width/2 - height/7, height/2, width/5, height/5);
        
        add(new MenuSprite(gm, Object2DFactory.getSkillFire(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillIce(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillArrowRed(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillArrowBlue(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillDoubleRed(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillDoubleBlue(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillThreeRed(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillThreeBlue(), 0f, 0f, width, height));
        add(new MenuSprite(gm, Object2DFactory.getSkillStar(), 0f, 0f, width, height));
        
        // TODO add buttons to the menu and create callbacks for button click events.
        add(returnToGame);
    }
    
    //TODO add getter methods for button events (callbacks) like opening/ closing menu
    public ButtonEventPublisher getReturnToGameButtonEvent(){
        return returnToGame.getEventPublisher();
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
            player.getEventPublisher().unsubscribe(callBack);
            // TODO unsubscribe to all of the event publishers with the callbacks you made
        }
    }

    @Override
    public void show() {
        if (hidden){
            super.show();
            updatePlayerInfo();
            menuOpen = true;
            // TODO call any other update functions to refresh
            // This is because they have previously been unsubed to the normal events
            player.getEventPublisher().subscribe(callBack);
            // TODO subscribe to all of the event publishers with the callbacks you made
        }
    }
    
    public boolean isMenuOpen() {
    	return menuOpen;
    }

}
