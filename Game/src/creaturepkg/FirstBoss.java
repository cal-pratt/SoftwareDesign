/** FirstBoss
 * The first boss the player encounters
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

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

    private float laserSpeed = 50f;
	private float minDist = 35;
	private float lastFire = 0;
	private float fireIncrement = 75;
	private Vector2f velocity = new Vector2f();
	
	private float flightSpeed = 75f;
	
	private IPlayerEventListener callback = new IPlayerEventListener() {
		@Override
		public void actionPerformed(PlayerEventPublisher sender, Player e) {
		    updateAim();
		}
	};
	
	public FirstBoss(Player player) {
		super(Arrays.asList(Object3DFactory.getAlien()), 50, 2, 2, 0);
		this.player = player;
		player.getEventPublisher().subscribe(callback);
	}

    @Override 
    public void delete(){
        player.getEventPublisher().unsubscribe(callback);
        super.delete();
    }
	
	private void updateAim(){
		
		if (currHealth < maxHealth){
			Vector2f delta = player.getPosition().subtract(this.getPosition());
			if (delta.length() > minDist){
		        	velocity = delta.normalize().scale(flightSpeed);
		    }
			else {
		        	velocity = new Vector2f();
		    }
			
			if (currHealth > maxHealth/2){
		        aim = delta.normalize().scale(laserSpeed);
			}
			else {
				aim = delta.normalize().scale(2 * laserSpeed);
			}
		}
		else {
			aim = new Vector2f();
        	velocity = new Vector2f();
		}
	}

    @Override 
	public void updateActions(IGameMap map, float timepassed) {
		
		lastFire += timepassed;
		if(aim.x != 0 || aim.y != 0 ){
			if(lastFire > fireIncrement ){
				map.addMapElement(
			    		new NormProjectile(this, getPosition(), aim)
			    	);
				lastFire = 0;
			}
		}
		setPosition(getPosition().add(velocity.scale(timepassed/1000)));
	}

	public void updateModel() {
        Matrix4f model = Matrix4f.translate( 0, 0, 1).multiply(
        		Matrix4f.rotate(0,0,0,1).multiply(
                Matrix4f.rotate(180, 1, 0, 0).multiply(Matrix4f.scale(.8f, .8f, .8f))));
	    super.updateModel(model);
	}

}
