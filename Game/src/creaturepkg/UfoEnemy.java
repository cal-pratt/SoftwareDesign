package creaturepkg;

import java.util.Arrays;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;
import eventpkg.GameEvents.IPlayerEventListener;
import eventpkg.GameEvents.PlayerEventPublisher;

public class UfoEnemy extends ACreature{
	private Player player;
	private IPlayerEventListener callback;

    private float laserSpeed = 120f;
	private float aggroRange = 40;
	private float lastFire;
	private float fireIncrement = 200;
	
	public UfoEnemy(Player player) {
		super(Arrays.asList(Object3DFactory.getUfo()), 10, 2, 2, 0);
		this.player = player;
		callback = new IPlayerEventListener() {
			
			@Override
			public void actionPerformed(PlayerEventPublisher sender, Player e) {
			    updateAim();
			}
		};
		player.getEventPublisher().subscribe(callback);
	}
	
	@Override 
    public void delete(IGameMap map){
        player.getEventPublisher().unsubscribe(callback);
        super.delete(map);
    }
	
	private void updateAim(){
		if (player.getPosition().subtract(this.getPosition()).length() < aggroRange){
			Vector2f delta = player.getPosition().subtract(this.getPosition());
	        
	        aim = delta.scale(laserSpeed/delta.length());
		}
		else {
			aim = new Vector2f();
		}
	}

    @Override 
	public void updateActions(IGameMap map, float timepassed) {
		lastFire += timepassed;
		if(aim.x != 0 || aim.y != 0 ){
			if(lastFire > fireIncrement ){
				map.addMapElement(
			    		new Projectile(this, getPosition().add(new Vector2f(-.4f,+.4f)), aim)
			    	);
				lastFire = 0;
			}
		}
	}

	public void updateModel() {
        Matrix4f model = Matrix4f.translate( 0, 0, 1).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f)));
	    super.updateModel(model);
	}
    
    //Getters and Setters
    public float getAggroRange() {
        return aggroRange;
    }
    
    public void setAggroRange(int aggroRange) {
        this.aggroRange = aggroRange;
    }
}
