/** AEventPublisher
 * An abstract class that publishes events based on listeners
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package eventpkg;

import inputpkg.Joystick;
import inputpkg.Key;

import java.util.ArrayList;
import java.util.List;

import creaturepkg.Player;

import menupkg.MenuButton;

abstract class AEventPublisher<Model, T extends AEventPublisher<Model, ?>> {
    private List<IEventListener<Model, T>> listeners;
    
    public AEventPublisher(){
        listeners = new ArrayList<IEventListener<Model, T>>();
    }

    public void subscribe(IEventListener<Model, T> listener) {
        listeners.add(listener);
    }
    
    public void unsubscribe(IEventListener<Model, T> listener) {
        listeners.remove(listener);
    }

    protected void publish(T mediator, Model sender) {
        for(IEventListener<Model, T> listener : listeners){
            listener.actionPerformed(mediator, sender);
        }
    }
    
    public abstract void publish(Model sender);
}


/** IEventListener
 * Listeners to actions performed within the game
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

interface IEventListener<Model, EventPublisher extends AEventPublisher<Model, ?>> {
    public void actionPerformed(EventPublisher sender, Model e);
}


/** GameEvents
 * A class containing all of the listeners that extend IEventListener and AEventPublisher
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */
public class GameEvents{
    public static interface IButtonEventListener extends IEventListener<MenuButton, ButtonEventPublisher>{}
    
    public static class ButtonEventPublisher extends AEventPublisher<MenuButton, ButtonEventPublisher> {
        @Override
        public void publish(MenuButton sender) {
            publish(this, sender);
        }
    }

    public static interface IJoystickEventListener extends IEventListener<Joystick, JoystickEventPublisher> {}
    
    public static class JoystickEventPublisher extends AEventPublisher<Joystick, JoystickEventPublisher> {
        @Override
        public void publish(Joystick sender) {
            publish(this, sender);
        }
    }

    public static interface IKeyEventListener extends IEventListener<Key, KeyEventPublisher>{}
    
    public static class KeyEventPublisher extends AEventPublisher<Key, KeyEventPublisher>{
        @Override
        public void publish(Key sender) {
            publish(this, sender);
        }
    }
    
    public static interface IPlayerEventListener extends IEventListener<Player, PlayerEventPublisher> {}
    
    public static class PlayerEventPublisher extends AEventPublisher<Player, PlayerEventPublisher> {
        @Override
        public void publish(Player sender) {
            publish(this, sender);
        }
    }

}