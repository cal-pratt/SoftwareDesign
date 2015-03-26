package creaturepkg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;
import eventpkg.GameEvents.IPlayerEventListener;
import eventpkg.GameEvents.PlayerEventPublisher;

public class FirstBoss extends ACreature {
	private Player player;

    private float laserSpeed = 100f;
	private float minDist = 5;
	private float lastFire = 0;
	private float fireIncrement = 200;
	private Vector2f velocity = new Vector2f();
	private List<Vector2f> shots = new ArrayList<Vector2f>();

	private float flightSpeed = 60f;
	
	private IPlayerEventListener callback = new IPlayerEventListener() {
		@Override
		public void actionPerformed(PlayerEventPublisher sender, Player e) {
		    updateAim();
		}
	};
	
	public FirstBoss(Player player) {
		super(Arrays.asList(Object3DFactory.getCoolShip()), 50, 2, 2, 0);
		this.player = player;
		player.getEventPublisher().subscribe(callback);
	}

    @Override 
    public void delete(IGameMap map){
        player.getEventPublisher().unsubscribe(callback);
        super.delete(map);
    }
	
	private void updateAim(){
		
		Vector2f delta = player.getPosition().subtract(this.getPosition());
		if (currHealth < maxHealth){
			
			if (currHealth > maxHealth/2){
			
		        aim = delta.normalize().scale(laserSpeed);
			}
			
			else {
				for(int i = 0; i <6; i++){
					shots.set(i, new Vector2f((float)Math.cos(i*Math.PI/3), (float)Math.sin(i*Math.PI/3)));
				}
				aim = new Vector2f();
				
			}
	        
	        if (delta.length() > minDist){
	        	velocity = delta.normalize().scale(flightSpeed);
	        }
	        else {
	        	velocity = new Vector2f();
	        }
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
		if(lastFire > fireIncrement ){
			for(Vector2f shot: shots){
				if(shot.x != 0 || shot.y != 0 ){
						map.addMapElement(
					    		new Projectile(this, getPosition().add(new Vector2f(-.4f,+.4f)), shot)
					    	);
						lastFire = 0;
				}
			}
		}
		
		setPosition(getPosition().add(velocity.scale(timepassed/1000)));
	}

	public void updateModel() {
        Matrix4f model = Matrix4f.translate( 0, 0, 1).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f)));
	    super.updateModel(model);
	}

}
