package creaturepkg;

import graphicspkg.GraphicsManager;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class Projectile extends MapElement{
	int power = 0;
	float dx;
	float dy;
	
	public Projectile(GraphicsManager gm, float x, float y, float dx, float dy) {
		super(gm, Object3DFactory.getMonkey(), x,y);
		this.dx = dx;
		this.dy = dy;
	}
	
	//projectile should move forward in direction shot
	public void update(Matrix4f projection){
		x += dx;
		y += dy;
		updateProjection(projection);
		updateModel(Matrix4f.scale(.5f, .5f, .5f));
	}
	
	//If a projectile hits a creature, there is a collision
	public void creatureCollision(ACreature target, Projectile attack) {
		target.defend(target, attack);
	}
}
