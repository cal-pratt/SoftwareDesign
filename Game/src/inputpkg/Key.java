package inputpkg;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import eventpkg.KeyEventPublisher;

class Key{
    private int action;
    private KeyEventPublisher inputEvent;
    
    public Key(){
        this.action = GLFW_RELEASE;
        inputEvent = new KeyEventPublisher();
    }
    public void set(int action){
        this.action = action;
        inputEvent.publish(action);
    }
    public KeyEventPublisher getInputEvent(){
        return inputEvent;
    }
    public int getAction(){
        return this.action;
    }
}