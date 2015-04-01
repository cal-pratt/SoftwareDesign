/** FireProjectile
 * A red/orange projectile slightly stronger than the default - obtained by the player when the fire skill is clicked
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import java.util.Arrays;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class FireProjectile extends Projectile {

	public FireProjectile(ACreature owner, Vector2f position, Vector2f velocity) {
		super(Arrays.asList(Object3DFactory.getFireLaser()), owner, position, velocity);
		
	}

}
