/** PauseMenu
 * A menu brought up when the player pauses the game - user can continue, save, or quit
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package menupkg;

import eventpkg.GameEvents.*;
import objectpkg.Object2DFactory;
import graphicspkg.GraphicsManager;
import inputpkg.UserInput;

public class EndOfGameMenu extends AMenu {
    private MenuButton newGame;
    
    public EndOfGameMenu(GraphicsManager gm, UserInput input) {
        super(gm);
        
        newGame = new MenuButton(gm, input,
                Object2DFactory.getNewgameReleased(), Object2DFactory.getNewgameClicked(),
                width/2f - height/7f, height/3f, width/5f, height/5f);
        
        add(new MenuSprite(gm, Object2DFactory.getPause(), width*1f/3.8f, height*2f/3.8f, width/2f, height*1f/3f));
        add(continuegame);
    }
    
    public ButtonEventPublisher getContinueButtonEvent(){
        return continuegame.getEventPublisher();
    }
    
    public void reset(){
    }
    
    public void hide(){
        if (!hidden){
            super.hide();
        }
    }
    
    public void show(){
        if (hidden){
            super.show();
        }
    }
}
