package creaturepkg;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.LinkedList;
import java.util.List;

import eventpkg.IPlayerEventListener;
import eventpkg.PlayerEventPublisher;
import graphicspkg.GraphicsManager;
import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class MonkeyEnemy extends ACreature {
	
	private List<Projectile> projectiles = new LinkedList<Projectile>();
	private Player player;
	private IPlayerEventListener callback;
	
	//Enemy variables
	private float aggroRange = 30;
	private float lastFire;
	private float fireIncrement = 200;
	
	private boolean updateEnemyAim = false;
	
	public MonkeyEnemy(GraphicsManager gm, Player player) {
		super(gm, Object3DFactory.getMonkey(), 10, 1, 1, 0);
		this.player = player;
		callback = new IPlayerEventListener() {
			
			@Override
			public void actionPerformed(PlayerEventPublisher sender, Object e) {
				updateEnemyAim = true;
			}
		};
		player.getEventPublisher().subscribe(callback);
	}
	
	//Getters and Setters
	public float getAggroRange() {
		return aggroRange;
	}
	
	public void setAggroRange(int aggroRange) {
		this.aggroRange = aggroRange;
	}
	
	public void updateAim(){
		if (Math.pow(player.getPosX() - x,2) + Math.pow(player.getPosY() - y,2) < aggroRange*aggroRange){
			float dX = (player.getPosX() - this.x);
	        float dY = (player.getPosY() - this.y);
	        
	        aimX = dX/((float)Math.sqrt(dX*dX + dY*dY));
	        aimY = dY/((float)Math.sqrt(dX*dX + dY*dY));
		}
		else {
			aimX = 0;
			aimY = 0;
		}
		updateEnemyAim = false;
	}
	
	public void update(float timePassed, Matrix4f model) {
		if(updateEnemyAim) updateAim();
		lastFire += timePassed;
		if(aimX != 0 || aimY != 0 ){
			if(lastFire > fireIncrement ){
				createProjectile();
				lastFire = 0;
			}
		}
		
		updateModel(model);
		updateProjectiles(timePassed);
	}
	
	@Override 
	public void delete(){
		super.delete();
		for(Projectile p : projectiles){
			p.delete();
		}
		projectiles.clear();
        player.getEventPublisher().unsubscribe(callback);
	}
	
	@Override
	public int attack() {
		//When different attacks are made, insert different attack types here
		int atkDamage = this.getAttackLvl();

		//Listen for player's location
		//float direction = ((player.x)*(player.x))+((this.y)*(this.y))
		if (player.x - this.x < aggroRange ||player.y < aggroRange){
			projectiles.add(new Projectile(gm, x, y, (player.x)*(player.x), (this.y)*(this.y)));
		}
		//Fire projectile
		return atkDamage;
	}

}
