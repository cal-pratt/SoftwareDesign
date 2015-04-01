/** AlienEnemy
 * A simple enemy 
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;


import java.util.Arrays;

import eventpkg.GameEvents.*;
import graphicspkg.GraphicsManager;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class AlienEnemy extends ACreature {
	private Player player;

    private float laserSpeed = 100f;
	private float aggroRange = 30;
	private float lastFire = 0;
	private float fireIncrement = 30;
	
	private IPlayerEventListener callback = new IPlayerEventListener() {
		@Override
		public void actionPerformed(PlayerEventPublisher sender, Player e) {
		    updateAim();
		}
	};
	
	public AlienEnemy(Player player) {
		super(Arrays.asList(Object3DFactory.getAlien()), 10, 2, 2, 0);
		this.player = player;
		player.getEventPublisher().subscribe(callback);
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
	public void updateActions(IGameMap map, float timepassed) {
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
                Matrix4f.rotate(90, 0, 0, 1).multiply(Matrix4f.scale(1f, 1f, 1f)));
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
