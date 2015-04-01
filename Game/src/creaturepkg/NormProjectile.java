/** NormProjectile
 * The default projectile - yellow in colour and the weakest projectile
 * 
 * @author      Halie Murrin
 * @author      Calvert Pratt
 */

package creaturepkg;

import java.util.Arrays;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public class NormProjectile extends Projectile {

	public NormProjectile(ACreature owner, Vector2f position, Vector2f velocity) {
		super(Arrays.asList(Object3DFactory.getSimpleLaser()), owner, position, velocity);
		// TODO Auto-generated constructor stub
	}

}
