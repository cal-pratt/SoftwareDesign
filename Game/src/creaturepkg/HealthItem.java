package creaturepkg;

import java.util.Arrays;
import java.util.List;

import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class HealthItem extends AMapElement {

	private float spin = 0;
	
	protected HealthItem() {
		super(Arrays.asList(Object3DFactory.getHeart()), new Vector2f());
	}

	public void updateCollision(Player player){
		if (player.getPosition().subtract(getPosition()).length() < 2){
			player.setCurrentHealth(player.getCurrentHealth() + 1);
			this.setDead();
		}
	}
	
	@Override
	public void updateActions(IGameMap map, float timepassed) {
		spin += timepassed;
	}

	@Override
	public void updateModel() {
        Matrix4f model = Matrix4f.translate( 0, 0, 1).multiply(
                Matrix4f.rotate(spin, 0, 0, 1).multiply(
                        Matrix4f.rotate(-90, 1, 0, 0).multiply(
                        		Matrix4f.scale(1.5f, 1.5f, 1.5f))));
	    super.updateModel(model);
	}

}
