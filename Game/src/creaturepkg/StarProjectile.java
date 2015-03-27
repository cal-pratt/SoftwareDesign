package creaturepkg;

import java.util.Arrays;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class StarProjectile extends Projectile {

	public StarProjectile(ACreature owner, Vector2f position, Vector2f velocity) {
		super(Arrays.asList(Object3DFactory.getStarLaser()), owner, position, velocity);
		// TODO Auto-generated constructor stub
	}

}
