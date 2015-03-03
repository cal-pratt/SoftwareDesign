package creaturepkg;

public abstract class ACreature {
	//Creature variables
	private int maxHealth, currHealth, attackLvl, defenseLvl, attackType;
	
	//behaviour
	public void die(){}
	
	public void attack(int attackType, int attackLvl, ACreature attacker, ACreature target ) {
		int attackerAttack = attacker.getAttackLvl();
		int tgtDefense = target.getDefenseLvl();
		int damage = attackerAttack - tgtDefense;
		if (damage >0 ) {
			target.setCurrentHealth(target.getCurrentHealth() - damage);
		}
	}
	
	//When a player is attacked they should automatically defend and lose 
	//appropriate health
	public void defend(int defenseLvl){}

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
