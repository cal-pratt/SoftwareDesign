package creaturepkg;

import java.util.Arrays;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class FireProjectile extends Projectile {

	public FireProjectile(ACreature owner, Vector2f position, Vector2f velocity) {
		super(Arrays.asList(Object3DFactory.getFireLaser()), owner, position, velocity);
		
	}

}
