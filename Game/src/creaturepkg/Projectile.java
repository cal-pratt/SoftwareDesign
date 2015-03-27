/** Projectile
 * An attack fired by creatures to attack hostile creatures
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



public class Projectile extends AMapElement{
	private Vector2f velocity;
	private Matrix4f rot;
	private ACreature owner;
	
	public Projectile(List<APcObject3D> mesh, ACreature owner, Vector2f position, Vector2f velocity) {
		super(mesh, position);
		this.owner = owner;
		
		this.velocity = new Vector2f(velocity.x, velocity.y);
		
        if(velocity.x == 0){
            if(velocity.y > 0){
                rot = Matrix4f.rotate(0, 0, 0, 1);
            }
            else{
                rot = Matrix4f.rotate(180, 0, 0, 1);
            }
        }
        else{
            float angle = 180f*(float)(Math.atan(velocity.y/velocity.x))/3.14f;
            if(velocity.x > 0) angle += 180;
            rot = Matrix4f.rotate(angle + 90, 0f, 0f, 1f);
        }
	}

    @Override
	public void updateActions(IGameMap map, float timepassed){
    	setPosition(getPosition().add(velocity.scale(timepassed/1000f)));

        if(owner.getPosition().subtract(getPosition()).length() > 100){
            setDead();
        }
	}
	
	@Override
	public void updateModel(){
        super.updateModel(Matrix4f.scale(.5f, .5f, .5f).multiply(
        		rot).multiply(Matrix4f.rotate(90, 0, 0, 1).multiply(
        				Matrix4f.rotate(90, 1, 0, 0))));
	}
	
	public void updateCollision(IGameMap map, ACreature creature){
		if(this.owner == creature) return;
		if(creature.getPosition().subtract(getPosition()).length() < 2){
			creature.applyDamage(map, owner);
			setDead();
		}
	}
	
}
