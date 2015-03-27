package creaturepkg;

import java.util.Arrays;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class IceProjectile extends Projectile {

	public IceProjectile(ACreature owner, Vector2f position, Vector2f velocity) {
		super(Arrays.asList(Object3DFactory.getIceLaser()), owner, position, velocity);
	}
}
