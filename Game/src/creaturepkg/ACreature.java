package creaturepkg;

public abstract class ACreature {
	//Creature variables
	private int maxHealth, currHealth, attackLvl, defenseLvl, attackType;
	
	//behaviour
	public void die(){}
	
	public int attack(int attackType, ACreature attacker ) {
		//When different attacks are made, insert different attack types here
		int atkDamage = attacker.getAttackLvl();
		//Fire projectile
		return atkDamage;
	}
	
	//When a player is attacked they should automatically defend and lose 
	//appropriate health
	public void defend(ACreature target, Projectile attack){
		int tgtDefense = target.getDefenseLvl();
		int atkDamage = attack.power;
		int damage = atkDamage - tgtDefense;
		if (damage >0 ) {
			target.setCurrentHealth(target.getCurrentHealth() - damage);
		}
	}

	//Getters and Setters
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getCurrentHealth() {
		return currHealth;
	}
	
	public int getAttackLvl() {
		return attackLvl;
	}
	
	public int getDefenseLvl() {
		return defenseLvl;
	}
	
	public int getAttackType() {
		return attackType;
	}
	
	public void setMaxHealth(int maxHealth){
		this.maxHealth = maxHealth;
	}
	
	public void setCurrentHealth(int currHealth) {
		this.currHealth = currHealth;
	}
	
	public void setAttackLvl(int attackLvl) {
		this.attackLvl = attackLvl;
	}
	
	public void setDefenseLvl(int defenseLvl) {
		this.defenseLvl = defenseLvl;
	}
	
	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}
}
