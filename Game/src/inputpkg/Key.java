/** Key
 * Model representing a Keyboard Key
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */
package inputpkg;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import eventpkg.GameEvents.KeyEventPublisher;

public class Key{
    private int action;
    private KeyEventPublisher inputEvent;
    
    public Key(){
        this.action = GLFW_RELEASE;
        inputEvent = new KeyEventPublisher();
    }
    public void set(int action){
        this.action = action;
        inputEvent.publish(this);
    }
    public KeyEventPublisher getInputEvent(){
        return inputEvent;
    }
    public int getAction(){
        return this.action;
    }
}