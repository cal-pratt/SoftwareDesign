package creaturepkg;

public class Projectile {
	int power = 0;
	public Projectile(ACreature attacker) {
		power = attacker.getAttackLvl();
	}
	
	/* public int getPower(ACreature attacker) {
		//add functionality here for different attack types
		int power = attacker.getAttackLvl();
		return power;
	} */
	
	public void fireProjectile() {
		//projectile should move forward in direction shot
		//if a creature is hit, call creatureCollision
		
	}
	
	//If a projectile hits a creature, there is a collision
	public void creatureCollision(ACreature target, Projectile attack) {
		target.defend(target, attack);
	}
}
