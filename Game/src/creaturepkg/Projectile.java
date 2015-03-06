package creaturepkg;

import graphicspkg.GraphicsManager;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class Projectile extends AMapElement{
	int power = 0;
	float dx;
	float dy;
	Matrix4f rot;
	
	public Projectile(GraphicsManager gm, float x, float y, float dx, float dy) {
		super(gm, Object3DFactory.getLaser(), x, y);
		this.dx = dx;
		this.dy = dy;
		
        if(dx == 0){
            if(dy > 0){
                rot = Matrix4f.rotate(0, 0, 0, 1);
            }
            else{
                rot = Matrix4f.rotate(180, 0, 0, 1);
            }
        }
        else{
            float angle = 180f*(float)(Math.atan(dy/dx))/3.14f;
            if(dx > 0) angle += 180;
            rot = Matrix4f.rotate(angle + 90, 0f, 0f, 1f);
        }
        
	}

    @Override
	public void updateActions(float timepassed){
		x += dx;
		y += dy;
	}
	
	@Override
	public void updateModel(Matrix4f model){
        super.updateModel(Matrix4f.scale(.5f, .5f, .5f).multiply(rot).multiply(Matrix4f.rotate(90, 0, 0, 1)));
	}
	
	//If a projectile hits a creature, there is a collision
	public void creatureCollision(ACreature target, Projectile attack) {
		target.defend(target, attack);
	}
}
