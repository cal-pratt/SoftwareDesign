package creaturepkg;

import eventpkg.IPlayerEventListener;
import eventpkg.PlayerEventPublisher;
import graphicspkg.GraphicsManager;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class MonkeyEnemy extends ACreature {
	private Player player;
	private IPlayerEventListener callback;
	
	private float aggroRange = 30;
	private float lastFire;
	private float fireIncrement = 200;
	
	public MonkeyEnemy(GraphicsManager gm, Player player) {
		super(gm, Object3DFactory.getMonkey(), 10, 1, 1, 0);
		this.player = player;
		callback = new IPlayerEventListener() {
			
			@Override
			public void actionPerformed(PlayerEventPublisher sender, Object e) {
			    updateAim();
			}
		};
		player.getEventPublisher().subscribe(callback);
	}

    @Override 
    public void delete(){
        player.getEventPublisher().unsubscribe(callback);
        super.delete();
    }
	
	private void updateAim(){
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
	}

    @Override 
	public void updateActions(float timepassed) {
		lastFire += timepassed;
		if(aimX != 0 || aimY != 0 ){
			if(lastFire > fireIncrement ){
		        projectiles.add(new Projectile(gm, x-.4f, y+.4f, aimX, aimY));
		        projectiles.add(new Projectile(gm, x+.4f, y-.4f, aimX, aimY));
				lastFire = 0;
			}
		}
        super.updateActions(timepassed);
	}

    @Override 
	public void updateModel(Matrix4f model) {
	    super.updateModel(model);
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
    
    //Getters and Setters
    public float getAggroRange() {
        return aggroRange;
    }
    
    public void setAggroRange(int aggroRange) {
        this.aggroRange = aggroRange;
    }

}
