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
    //private IPlayerEventListener callBack;
    
    //TODO add local variables for current player info to avoid constant gets
        
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
        
        add(new MenuSprite(gm, Object2DFactory.getSkillFire(), width/1550f, height - height/6.86f, width/15f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillIce(), 0, height - height/3.095f, width/15f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillArrowRed(), width/5.2f, height - height/6.86f, width/18f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillArrowBlue(), width/5.2f, height - height/3.06f, width/18f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillDoubleRed(), width/2.68f, height - height/6.95f, width/6.9f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillDoubleBlue(), width/2.62f, height - height/3.09f, width/7.1f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillThreeRed(), width/1.55f, height - height/6.86f, width/5f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillThreeBlue(), width/1.55f, height - height/3.099f, width/5f, height/7f));
        add(new MenuSprite(gm, Object2DFactory.getSkillStar(), width/1.13f, height - height/3.99f, width/9f, height/5.75f));
        
        add(new MenuSprite(gm, Object2DFactory.getSkillMenu(), 0, height - height/3f, width, height/3f));
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
