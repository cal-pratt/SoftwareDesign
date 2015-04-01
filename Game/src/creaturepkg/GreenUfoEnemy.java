/** GreenUfoEnemy
 * An enemy with a green UFO mesh that moves quickly around the screen - it is the slightly weaker UFO
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import java.util.Arrays;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;
import eventpkg.GameEvents.IPlayerEventListener;
import eventpkg.GameEvents.PlayerEventPublisher;

public class GreenUfoEnemy extends ACreature{
	private Player player;
	private IPlayerEventListener callback= new IPlayerEventListener() {
		
		@Override
		public void actionPerformed(PlayerEventPublisher sender, Player e) {
		    updateAim();
		}
	};;
	private Vector2f velocity = new Vector2f();

    private float laserSpeed = 120f;
	private float aggroRange = 40;
	private float lastFire;
	public float fireIncrement = 200;
	
	private float flightSpeed = 100f;
	
	public GreenUfoEnemy(Player player) {
		super(Arrays.asList(Object3DFactory.getUfo()), 10, 2, 2, 0);
		this.player = player;
		player.getEventPublisher().subscribe(callback);
		velocity = new Vector2f(((float)Math.random()*2) - 1, ((float)Math.random()*2) - 1);
		velocity = velocity.normalize().scale(flightSpeed);
	}
	
	@Override 
    public void delete(){
        player.getEventPublisher().unsubscribe(callback);
        super.delete();
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
	public void updateActions(IGameMap map, float timepassed){
    	Vector2f minb = getMinBoundary();
    	Vector2f maxb = getMaxBoundary();
    	
    	Vector2f tempVelocity = velocity.scale(timepassed/1000);
    	if (getPosition().x + tempVelocity.x > maxb.x || getPosition().x + tempVelocity.x < minb.x){
    		velocity.x = -velocity.x;
    	}
    	if (getPosition().y + tempVelocity.y > maxb.y || getPosition().y + tempVelocity.y < minb.y){
    		velocity.y = -velocity.y;
    	}
    	setPosition(getPosition().add(velocity.scale(timepassed/1000)));
    	
    	
		lastFire += timepassed;
		if(aim.x != 0 || aim.y != 0 ){
			if(lastFire > fireIncrement ){
				map.addMapElement(
			    		new NormProjectile(this, getPosition().add(new Vector2f(-.4f,+.4f)), aim)
			    	);
				lastFire = 0;
			}
		}
	}

	public void updateModel() {
        Matrix4f model = Matrix4f.translate( 0, 0, 1).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1.5f, 1.5f, 1.5f)));
	    super.updateModel(model);
	}
	
    public float getAggroRange() {
        return aggroRange;
    }
    
    public void setAggroRange(int aggroRange) {
        this.aggroRange = aggroRange;
    }
}
