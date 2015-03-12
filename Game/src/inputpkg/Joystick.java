package inputpkg;

import java.nio.FloatBuffer;

import eventpkg.GameEvents.JoystickEventPublisher;


import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;

public class Joystick{
    private final float epsilon = 0.001f;
    
    private int name;
    
    private float[] axes = new float[4];
    
    private float leftStickerHor = 0;
    private float leftStickerVert = 0;
    private float triggers = 0;
    private float rightStickerVert = 0;
    private float rightStickerHor = 0;
    
    private JoystickEventPublisher inputEvent;
    
    public Joystick(int name){
        this.name = name;
        inputEvent = new JoystickEventPublisher();
    }
    
    public void poll(){
        FloatBuffer fb = glfwGetJoystickAxes(name);
        leftStickerHor = (Math.abs(fb.get(0)) > epsilon)? fb.get(0) : 0;
        leftStickerVert = (Math.abs(fb.get(1)) > epsilon)? fb.get(1) : 0;
        rightStickerHor = (Math.abs(fb.get(2)) > epsilon)? fb.get(2) : 0;
        rightStickerVert = (Math.abs(fb.get(3)) > epsilon)? fb.get(3) : 0;
        inputEvent.publish(this);
    }
    
    public JoystickEventPublisher getInputEvent(){
        return inputEvent;
    }
    
    public float getLeftStickerHor(){
        return this.leftStickerHor;
    }
    public float getLeftStickerVert(){
        return this.leftStickerVert;
    }
    public float getTriggers(){
        return this.triggers;
    }
    public float getRightStickerVert(){
        return this.rightStickerVert;
    }
    public float getRightStickerHor(){
        return this.rightStickerHor;
    }
}