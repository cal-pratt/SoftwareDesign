package menupkg;

import inputpkg.UserInput;
import objectpkg.Object2DFactory;
import creaturepkg.Player;
import eventpkg.GameEvents.*;
import graphicspkg.GraphicsManager;



public class SkillMenu extends AMenu {
    
    private Player player;
    private UserInput input;
    
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
        
        
        add(new MenuSprite(gm, Object2DFactory.getSpace(), 0f, 0f, width, height));

        // TODO add other MenuSprite's to menu. Example in other menus like pause/ start
        // TODO Look at the Object2DFactory.java and copy the style to add new images
        
        // TODO add buttons to the menu and create callbacks for button click events.
    }
    
    //TODO add getter methods for button events (callbacks) like opening/ closing menu
    
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
            player.getEventPublisher().unsubscribe(callBack);
            // TODO unsubscribe to all of the event publishers with the callbacks you made
        }
    }

    @Override
    public void show() {
        if (hidden){
            super.show();
            updatePlayerInfo();
            // TODO call any other update functions to refresh
            // This is because they have previously been unsubed to the normal events
            player.getEventPublisher().subscribe(callBack);
            // TODO subscribe to all of the event publishers with the callbacks you made
        }
    }

}
