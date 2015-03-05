package creaturepkg;

import graphicspkg.GraphicsManager;
import objectpkg.Object3DFactory;

public class Projectile extends MapElement{
	int power = 0;
	float dx;
	float dy;
	
	public Projectile(GraphicsManager gm, ACreature attacker) {
		super(gm, Object3DFactory.getCube(), attacker.x, attacker.y);
		power = attacker.getAttackLvl();
		dx = 0.2f;
		dy = 0.1f;
	}
	
	/* public int getPower(ACreature attacker) {
		//add functionality here for different attack types
		int power = attacker.getAttackLvl();
		return power;
	} */
	

	//projectile should move forward in direction shot
	public void movingProjectile() {
		x += dx;
		y += dy;
	}
	
	//If a projectile hits a creature, there is a collision
	public void creatureCollision(ACreature target, Projectile attack) {
		target.defend(target, attack);
	}
}
