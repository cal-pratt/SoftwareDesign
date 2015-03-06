package creaturepkg;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import eventpkg.IKeyEventListener;
import eventpkg.KeyEventPublisher;
import eventpkg.PlayerEventPublisher;
import inputpkg.UserInput;
import graphicspkg.GraphicsManager;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class Player extends ACreature {
	private PlayerEventPublisher eventPublisher;
	
	private List<Projectile> projectiles = new LinkedList<Projectile>();
	
	private UserInput input;
	private IKeyEventListener velocityCallback;
	private IKeyEventListener fireCallback;
	private IKeyEventListener aimCallback;
	
	private Matrix4f rot = new Matrix4f();
	
	private float velocityX = 0, velocityY = 0;
	private float aimX = 0, aimY = 0;
	
	private float fire = 0;
	private float lastFire = 0;
	private float fireIncrement = 40;
	
	public Player(GraphicsManager gm, UserInput input) {
		super(gm, Arrays.asList(Object3DFactory.getSpaceShipTop(), Object3DFactory.getSpaceShipBottom()), 10, 2, 2, 0);
		eventPublisher = new PlayerEventPublisher();
		
		this.input = input;
		
		fireCallback = new IKeyEventListener() {
			
			@Override
			public void actionPerformed(KeyEventPublisher sender, Object e) {
				readyProjectile();
			}
		};
		
		
		velocityCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                updateVelocity();
            }
	    };
	    aimCallback = new IKeyEventListener(){
            @Override 
            public void actionPerformed(KeyEventPublisher event, Object action) 
            { 
                updateAim();
            }
	    };
        
        input.getInputEvent(GLFW_KEY_A).subscribe(velocityCallback);
        input.getInputEvent(GLFW_KEY_D).subscribe(velocityCallback);
        input.getInputEvent(GLFW_KEY_S).subscribe(velocityCallback);
        input.getInputEvent(GLFW_KEY_W).subscribe(velocityCallback);
        
        input.getInputEvent(GLFW_KEY_UP).subscribe(aimCallback);
        input.getInputEvent(GLFW_KEY_DOWN).subscribe(aimCallback);
        input.getInputEvent(GLFW_KEY_LEFT).subscribe(aimCallback);
        input.getInputEvent(GLFW_KEY_RIGHT).subscribe(aimCallback);
		input.getInputEvent(GLFW_KEY_SPACE).subscribe(fireCallback);
	}
	
	@Override 
	public void delete(){
		super.delete();
		for(Projectile p : projectiles){
			p.delete();
		}
		projectiles.clear();
        input.getInputEvent(GLFW_KEY_A).unsubscribe(velocityCallback);
        input.getInputEvent(GLFW_KEY_D).unsubscribe(velocityCallback);
        input.getInputEvent(GLFW_KEY_S).unsubscribe(velocityCallback);
        input.getInputEvent(GLFW_KEY_W).unsubscribe(velocityCallback);
        
        input.getInputEvent(GLFW_KEY_UP).unsubscribe(aimCallback);
        input.getInputEvent(GLFW_KEY_DOWN).unsubscribe(aimCallback);
        input.getInputEvent(GLFW_KEY_LEFT).unsubscribe(aimCallback);
        input.getInputEvent(GLFW_KEY_RIGHT).unsubscribe(aimCallback);
		input.getInputEvent(GLFW_KEY_SPACE).unsubscribe(fireCallback);
	}
	
	public PlayerEventPublisher getEventPublisher(){
		return eventPublisher;
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
	
	private void updateVelocity(){
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
	
	private void updateAim(){
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
	
	private void readyProjectile(){
		//fire = input.getAction(GLFW_KEY_SPACE) == GLFW_PRESS 
		//		|| input.getAction(GLFW_KEY_SPACE) == GLFW_REPEAT;
	}
	
	public void update(float timepassed, Matrix4f projection, Matrix4f model){
		this.x += velocityX;
		this.y += velocityY;
		
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
		}
		
		if(aimX != 0 || aimY != 0 ){
			if(lastFire > fireIncrement ){
				createProjectile();
				lastFire = 0;
			}
		}
		updateModel(model.multiply(Matrix4f.translate(0,2.5f, 1).multiply(rot)).multiply(Matrix4f.rotate(90, 1, 0, 0)));
		updateProjection(projection);
		for(Projectile p : new LinkedList<>(projectiles)){
			if(Math.pow(Math.abs(p.x - x),2) + Math.pow(Math.abs(p.y - y),2) > 2000){
				p.delete();
				projectiles.remove(p);
			}
		}
		for(Projectile p : projectiles){
			p.update(projection);
		}
	}
	
	private void createProjectile() {
	    
		projectiles.add(new Projectile(gm, x-.4f, y+.4f, aimX, aimY));
        projectiles.add(new Projectile(gm, x+.4f, y-.4f, aimX, aimY));
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
