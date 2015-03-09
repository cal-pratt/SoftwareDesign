package creaturepkg;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;

import eventpkg.IJoystickEventListener;
import eventpkg.IKeyEventListener;
import eventpkg.JoystickEventPublisher;
import eventpkg.KeyEventPublisher;
import eventpkg.PlayerEventPublisher;
import inputpkg.UserInput;
import graphicspkg.GraphicsManager;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class Player extends ACreature {
	private PlayerEventPublisher eventPublisher;
	
	private UserInput input;
	private IKeyEventListener velocityKeyCallback;
	private IKeyEventListener aimKeyCallback;
    private IJoystickEventListener joystickCallback;
	
	private Matrix4f rot = new Matrix4f();
	
	private float velocityX = 0, velocityY = 0;
	private float lastFire = 0;
	private float fireIncrement = 40;
	
	public Player(GraphicsManager gm, UserInput input) {
		super(gm, Arrays.asList(Object3DFactory.getSpaceShipTop(), Object3DFactory.getSpaceShipBottom()), 10, 2, 2, 0);
		eventPublisher = new PlayerEventPublisher();
		
		this.input = input;
		
		
		velocityKeyCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                updateKeyVelocity();
            }
	    };
	    aimKeyCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                updateKeyAim();
            }
	    };

	    joystickCallback = new IJoystickEventListener(){
            @Override 
            public void actionPerformed(JoystickEventPublisher event, Object action) 
            {
                updateJoystickDir((float[])action);
            }
        };
        
        input.getKeyInputEvent(GLFW_KEY_A).subscribe(velocityKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_D).subscribe(velocityKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_S).subscribe(velocityKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_W).subscribe(velocityKeyCallback);
        
        input.getKeyInputEvent(GLFW_KEY_UP).subscribe(aimKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_DOWN).subscribe(aimKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_LEFT).subscribe(aimKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_RIGHT).subscribe(aimKeyCallback);
        

        if(input.JoystickFound(GLFW_JOYSTICK_1)){
            input.getJoystickInputEvent(GLFW_JOYSTICK_1).subscribe(joystickCallback);
        }
        
        
	}
	
	@Override 
	public void delete(){
		super.delete();
        input.getKeyInputEvent(GLFW_KEY_A).unsubscribe(velocityKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_D).unsubscribe(velocityKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_S).unsubscribe(velocityKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_W).unsubscribe(velocityKeyCallback);
        
        input.getKeyInputEvent(GLFW_KEY_UP).unsubscribe(aimKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_DOWN).unsubscribe(aimKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_LEFT).unsubscribe(aimKeyCallback);
        input.getKeyInputEvent(GLFW_KEY_RIGHT).unsubscribe(aimKeyCallback);
        
        if(input.JoystickFound(GLFW_JOYSTICK_1)){
            input.getJoystickInputEvent(GLFW_JOYSTICK_1).unsubscribe(joystickCallback);
        }
	}
	
	public PlayerEventPublisher getEventPublisher(){
		return eventPublisher;
	}
	
	private void updateKeyVelocity(){
        float dx = 0;
        float dy = 0;
        if(input.getAction(GLFW_KEY_A) == GLFW_PRESS || input.getAction(GLFW_KEY_A) == GLFW_REPEAT){
            dx -= 0.5f;
        }
        if(input.getAction(GLFW_KEY_D) == GLFW_PRESS || input.getAction(GLFW_KEY_D) == GLFW_REPEAT){
            dx += 0.5f;
        }
        if(input.getAction(GLFW_KEY_S) == GLFW_PRESS || input.getAction(GLFW_KEY_S) == GLFW_REPEAT){
            dy -= 0.5f;
        }
        if(input.getAction(GLFW_KEY_W) == GLFW_PRESS || input.getAction(GLFW_KEY_W) == GLFW_REPEAT){
            dy += 0.5f;
        }
        velocityX = dx;
        velocityY = dy;
	}
	
	private void updateKeyAim(){
        float dx = 0;
        float dy = 0;
        if(input.getAction(GLFW_KEY_LEFT) == GLFW_PRESS || input.getAction(GLFW_KEY_LEFT) == GLFW_REPEAT){
            dx -= 1f;
        }
        if(input.getAction(GLFW_KEY_RIGHT) == GLFW_PRESS || input.getAction(GLFW_KEY_RIGHT) == GLFW_REPEAT){
            dx += 1f;
        }
        if(input.getAction(GLFW_KEY_UP) == GLFW_PRESS || input.getAction(GLFW_KEY_UP) == GLFW_REPEAT){
            dy += 1f;
        }
        if(input.getAction(GLFW_KEY_DOWN) == GLFW_PRESS || input.getAction(GLFW_KEY_DOWN) == GLFW_REPEAT){
            dy -= 1f;
        }
        aimX = dx;
        aimY = dy;
	}
	
	public void updateJoystickDir(float[] axes){
	    float dx = input.getLeftStickerHor(GLFW_JOYSTICK_1);
	    float dy = -input.getLeftStickerVert(GLFW_JOYSTICK_1);
	    
	    if(dx == 0 && dy == 0){
	        velocityX = 0;
	        velocityY = 0;
	    }
	    else if(dx == 0){
	        velocityX = 0;
	        velocityY = dy > 0 ? .5f : -.5f;
	    }
	    else if(dy == 0){
            velocityX = dx > 0 ? .5f : -.5f;
            velocityY = 0;
        }
	    else{
	        velocityX = dx*.5f/((float)Math.sqrt(dx*dx + dy*dy));
	        velocityY = dy*.5f/((float)Math.sqrt(dx*dx + dy*dy));
	    }
	    
	    dx = input.getRightStickerHor(GLFW_JOYSTICK_1);
	    dy = -input.getRightStickerVert(GLFW_JOYSTICK_1);
	    
	    if(dx == 0 && dy == 0){
	        aimX = 0;
	        aimY = 0;
        }
        else if(dx == 0){
            aimX = 0;
            aimY = dy > 0 ? 1f : -1f;
        }
        else if(dy == 0){
            aimX = dx > 0 ? 1f : -1f;
            aimY = 0;
        }
        else{
            aimX = dx/((float)Math.sqrt(dx*dx + dy*dy));
            aimY = dy/((float)Math.sqrt(dx*dx + dy*dy));
        }
        
	}
	
	public void updateActions(float timepassed){
	    
        lastFire += timepassed;
		
		if(velocityX != 0 || velocityY != 0 ){
		    if(velocityX == 0){
		        if(velocityY > 0){
		            rot = Matrix4f.rotate(0, 0, 0, 1);
		        }
		        else{
                    rot = Matrix4f.rotate(180, 0, 0, 1);
		        }
		    }
		    else{
		        float angle = 180f*(float)(Math.atan(velocityY/velocityX))/3.14f;
		        if(velocityX > 0) angle += 180;
	            rot = Matrix4f.rotate(angle + 90, 0f, 0f, 1f);
		    }

	        this.x += velocityX;
	        this.y += velocityY;
	        
            eventPublisher.publish(true);
		}
		
		if(aimX != 0 || aimY != 0 ){
			if(lastFire > fireIncrement ){
			    float shipTipX = x + velocityX*7;
                float shipTipY = y + velocityY*7;
		        projectiles.add(new Projectile(gm, shipTipX-aimY/2, shipTipY+aimX/2, aimX, aimY));
		        projectiles.add(new Projectile(gm, shipTipX+aimY/2, shipTipY-aimX/2, aimX, aimY));
				lastFire = 0;
			}
		}
		
		super.updateActions(timepassed);
	}
	
	public void updateModel(Matrix4f model){
        super.updateModel(model.multiply(Matrix4f.translate(0,2.5f, 1).multiply(rot)).multiply(Matrix4f.rotate(90, 1, 0, 0)));
	}
    
    public void doDamage(float dmg){
        currHealth -= dmg;
        if (currHealth < 0){
            currHealth = 0;
        }

        if (currHealth > maxHealth){
            currHealth = maxHealth;
        }
        eventPublisher.publish(true);
    }

	//Player variables
	private int experience, skillPoints;
	
	
	//Getters and Setters
	public int getExperience() {
		return experience;
	}
	
	public int getSkillPoints() {
		return skillPoints;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}
}
