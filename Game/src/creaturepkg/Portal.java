/** Portal
 * A portal to transport the Player between maps
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import java.util.Arrays;
import java.util.List;

import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class Portal extends AMapElement {

	private IGameMap holdingMap;
	private Portal exit;
	private boolean playerCollision;
	private boolean canWorp;
	private float lastWorp = 0;
	private float worpInterval = 50;
	
	private float spin = 0;
	
	public Portal(IGameMap holdingMap) {
		super(Arrays.asList(Object3DFactory.getPortal()), new Vector2f());
		this.holdingMap = holdingMap;
	}
	
	public void updateCollision(Player player){
		if (this.exit != null &&
				player.getPosition().subtract(getPosition()).length() < 2){
			this.playerCollision = true;
		}
		else {
			playerCollision = false;
		}
	}
    
	public IGameMap getMap(){
		return holdingMap;
	}
	
	public void setExit(Portal exit){
		this.exit = exit;
	}

	public Portal getExit(){
		return exit;
	}
	
	public boolean isWorping(){
		return canWorp;
	}
	
	public void setWorpingDone(){
		canWorp = false;
		lastWorp = 0;
	}
	
	@Override
	public void updateActions(IGameMap map, float timepassed) {
		lastWorp += timepassed;
		spin += timepassed*4;
		if(lastWorp > worpInterval){
			lastWorp = 0;
			canWorp = playerCollision;
		}
	}

	@Override
	public void updateModel() {
		super.updateModel(Matrix4f.rotate(-spin,0,1,0).multiply(
				Matrix4f.rotate(90,1,0,0).multiply(
						Matrix4f.scale(.5f, .5f, .5f))));

	}

}
