package creaturepkg;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;

import eventpkg.GameEvents.*;
import inputpkg.UserInput;
import inputpkg.Key;
import inputpkg.Joystick;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class Player extends ACreature {
	private PlayerEventPublisher eventPublisher;
	
	private UserInput input;
	private IKeyEventListener velocityKeyCallback;
	private IKeyEventListener aimKeyCallback;
    private IJoystickEventListener joystickCallback;
	
	private float rotationAngle = 0;
	
	private float flightSpeed = 50f;
    private float laserSpeed = 100f;
	private float velocityX = 0, velocityY = 0;
	private float lastFire = 0;
	private float fireIncrement = 40;
	
	public Player(UserInput input) {
		super(Arrays.asList(Object3DFactory.getSpaceShipTop(), Object3DFactory.getSpaceShipBottom()), 10, 2, 2, 0);
		eventPublisher = new PlayerEventPublisher();
		
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
        velocityX = dx;
        velocityY = dy;
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
        aimX = dx;
        aimY = dy;
	}
	
	public void updateJoystickDir(){
	    float dx = input.getLeftStickerHor(GLFW_JOYSTICK_1);
	    float dy = -input.getLeftStickerVert(GLFW_JOYSTICK_1);
	    
	    if(dx == 0 && dy == 0){
	        velocityX = 0;
	        velocityY = 0;
	    }
	    else if(dx == 0){
	        velocityX = 0;
	        velocityY = dy > 0 ? flightSpeed : -flightSpeed;
	    }
	    else if(dy == 0){
            velocityX = dx > 0 ? flightSpeed : -flightSpeed;
            velocityY = 0;
        }
	    else{
	        velocityX = dx*flightSpeed/((float)Math.sqrt(dx*dx + dy*dy));
	        velocityY = dy*flightSpeed/((float)Math.sqrt(dx*dx + dy*dy));
	    }
	    
	    dx = input.getRightStickerHor(GLFW_JOYSTICK_1);
	    dy = -input.getRightStickerVert(GLFW_JOYSTICK_1);
	    
	    if(dx == 0 && dy == 0){
	        aimX = 0;
	        aimY = 0;
        }
        else if(dx == 0){
            aimX = 0;
            aimY = dy > 0 ? laserSpeed : -laserSpeed;
        }
        else if(dy == 0){
            aimX = dx > 0 ? laserSpeed : -laserSpeed;
            aimY = 0;
        }
        else{
            aimX = dx*laserSpeed/((float)Math.sqrt(dx*dx + dy*dy));
            aimY = dy*laserSpeed/((float)Math.sqrt(dx*dx + dy*dy));
        }
	}
	
	public void updateActions(float timepassed){
	    
        lastFire += timepassed;
		
		if(velocityX != 0 || velocityY != 0 ){
		    if(velocityX == 0){
		        if(velocityY > 0){
		            rotationAngle = 0;
		        }
		        else{
		            rotationAngle = 180;
		        }
		    }
		    else{
		        rotationAngle = -180f*(float)(Math.atan(velocityX/velocityY))/3.14f;
		        if(velocityY < 0) rotationAngle += 180;
		    }

		    setPosX(getPosX() + velocityX*timepassed/1000f);
            setPosY(getPosY() + velocityY*timepassed/1000f);

            System.out.println(timepassed);
	        
            eventPublisher.publish(this);
		}
		
		if(aimX != 0 || aimY != 0 ){
			if(lastFire > fireIncrement ){
			    float shipTipX = getPosX() + 3*(float)Math.sin(Math.toRadians(-rotationAngle + 10));
                float shipTipY = getPosY() + 3*(float)Math.cos(Math.toRadians(rotationAngle - 10));
                containingMap.addMapElement(new Projectile(this, shipTipX, shipTipY, aimX, aimY));
                shipTipX = getPosX() + 3*(float)Math.sin(Math.toRadians(-rotationAngle - 10));
                shipTipY = getPosY() + 3*(float)Math.cos(Math.toRadians(rotationAngle + 10));
                containingMap.addMapElement(new Projectile(this, shipTipX, shipTipY, aimX, aimY));
				lastFire = 0;
			}
		}
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
