package creaturepkg;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;

import eventpkg.GameEvents.*;
import inputpkg.UserInput;
import inputpkg.Key;
import inputpkg.Joystick;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class Player extends ACreature {
	private PlayerEventPublisher eventPublisher;
	
	private UserInput input;
	private IKeyEventListener velocityKeyCallback;
	private IKeyEventListener aimKeyCallback;
    private IJoystickEventListener joystickCallback;
	
	private float rotationAngle = 0;
	
	private float flightSpeed = 100f;
    private float laserSpeed = 200f;
	private Vector2f velocity;
	private float lastFire = 0;
	private float fireIncrement = 40;
	
	public Player(UserInput input) {
		super(Arrays.asList(Object3DFactory.getSpaceShipTop(), Object3DFactory.getSpaceShipBottom()), 10, 2, 2, 0);
		eventPublisher = new PlayerEventPublisher();
		this.velocity = new Vector2f();
		this.input = input;
		
		velocityKeyCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Key action) 
            { 
                updateKeyVelocity();
            }
	    };
	    aimKeyCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Key action) 
            { 
                updateKeyAim();
            }
	    };

	    joystickCallback = new IJoystickEventListener(){
            @Override 
            public void actionPerformed(JoystickEventPublisher event, Joystick action) 
            {
                updateJoystickDir();
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
            dx -= flightSpeed;
        }
        if(input.getAction(GLFW_KEY_D) == GLFW_PRESS || input.getAction(GLFW_KEY_D) == GLFW_REPEAT){
            dx += flightSpeed;
        }
        if(input.getAction(GLFW_KEY_S) == GLFW_PRESS || input.getAction(GLFW_KEY_S) == GLFW_REPEAT){
            dy -= flightSpeed;
        }
        if(input.getAction(GLFW_KEY_W) == GLFW_PRESS || input.getAction(GLFW_KEY_W) == GLFW_REPEAT){
            dy += flightSpeed;
        }
        velocity = new Vector2f(dx, dy);
	}
	
	private void updateKeyAim(){
        float dx = 0;
        float dy = 0;
        if(input.getAction(GLFW_KEY_LEFT) == GLFW_PRESS || input.getAction(GLFW_KEY_LEFT) == GLFW_REPEAT){
            dx -= laserSpeed;
        }
        if(input.getAction(GLFW_KEY_RIGHT) == GLFW_PRESS || input.getAction(GLFW_KEY_RIGHT) == GLFW_REPEAT){
            dx += laserSpeed;
        }
        if(input.getAction(GLFW_KEY_UP) == GLFW_PRESS || input.getAction(GLFW_KEY_UP) == GLFW_REPEAT){
            dy += laserSpeed;
        }
        if(input.getAction(GLFW_KEY_DOWN) == GLFW_PRESS || input.getAction(GLFW_KEY_DOWN) == GLFW_REPEAT){
            dy -= laserSpeed;
        }
        aim = new Vector2f(dx, dy);
	}
	
	public void updateJoystickDir(){
	    float dx = input.getLeftStickerHor(GLFW_JOYSTICK_1);
	    float dy = -input.getLeftStickerVert(GLFW_JOYSTICK_1);
	    
	    if(dx == 0 && dy == 0){
	    	velocity = new Vector2f(0, 0);
	    }
	    else{
	    	velocity = new Vector2f(dx, dy).normalize().scale(flightSpeed);
	    }
	    
	    dx = input.getRightStickerHor(GLFW_JOYSTICK_1);
	    dy = -input.getRightStickerVert(GLFW_JOYSTICK_1);
	    
	    if(dx == 0 && dy == 0){
	    	aim = new Vector2f(0, 0);
	    }
	    else{
	    	aim = new Vector2f(dx, dy).normalize().scale(laserSpeed);
	    }
	}
	
	public void updateActions(float timepassed){
	    
        lastFire += timepassed;
		
		if(velocity.x != 0 || velocity.y != 0 ){
		    if(velocity.x == 0){
		        if(velocity.y > 0){
		            rotationAngle = 0;
		        }
		        else{
		            rotationAngle = 180;
		        }
		    }
		    else{
		        rotationAngle = -180f*(float)(Math.atan(velocity.x/velocity.y))/3.14f;
		        if(velocity.y < 0) rotationAngle += 180;
		    }

	    	setPosition(getPosition().add(velocity.scale(timepassed/1000f)));

            System.out.println(timepassed);
	        
		}
		
		if(aim.x != 0 || aim.y != 0 ){
			if(lastFire > fireIncrement ){
				Vector2f shipTip = getPosition().add(new Vector2f(
						3*(float)Math.sin(Math.toRadians(-rotationAngle + 10)),
						3*(float)Math.cos(Math.toRadians(rotationAngle - 10))
					));
                containingMap.addMapElement(new Projectile(this, shipTip, aim));
                shipTip = getPosition().add(new Vector2f(
						3*(float)Math.sin(Math.toRadians(-rotationAngle - 10)),
						3*(float)Math.cos(Math.toRadians(rotationAngle + 10))
					));
                containingMap.addMapElement(new Projectile(this, shipTip, aim));
				lastFire = 0;
			}
		}
		
        eventPublisher.publish(this);
	}
	
	public void updateModel(){
        super.updateModel(Matrix4f.rotate(rotationAngle, 0, 0, 1).multiply(Matrix4f.rotate(90, 1, 0, 0)));
	}
    
    public void doDamage(float dmg){
        currHealth -= dmg;
        if (currHealth < 0){
            currHealth = 0;
        }

        if (currHealth > maxHealth){
            currHealth = maxHealth;
        }
        eventPublisher.publish(this);
    }
    
    @Override
    public void setDead(){
        eventPublisher.publish(this);
        super.setDead();
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
