package creaturepkg;

import java.util.List;

import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;

public abstract class ACreature extends AMapElement {
	//Creature variables
	protected int maxHealth;
	protected int currHealth;
	private int attackLvl;
	private int defenseLvl;
	private int attackType = 0;
	
	protected Vector2f aim;
	
	protected ACreature(APcObject3D creatureMesh, int maxHealth, int attackLvl, int defenseLvl, int attackType) {
		super(creatureMesh, new Vector2f());
		this.aim = new Vector2f();
		this.maxHealth = maxHealth;
		this.attackLvl = attackLvl;
		this.defenseLvl = defenseLvl;
		this.attackType = attackType;
		this.currHealth = maxHealth;
		
	}
	
	protected ACreature(List<APcObject3D> creatureMesh, int maxHealth, int attackLvl, int defenseLvl, int attackType) {
        super(creatureMesh, new Vector2f());
		this.aim = new Vector2f();
        this.maxHealth = maxHealth;
        this.attackLvl = attackLvl;
        this.defenseLvl = defenseLvl;
        this.attackType = attackType;
        this.currHealth = maxHealth;
    }
	
	public void applyDamage(ACreature attacker){
		int attack = attacker.attackLvl - this.defenseLvl + 1;
		if(attack > 0){
			currHealth -= attack;
		}
		if(currHealth <= 0){
			currHealth = 0;
			setDead();
		}
	}

    @Override
	public void setPosition(Vector2f position){
    	Vector2f minb = containingMap.getMinBoundary();
    	Vector2f maxb = containingMap.getMaxBoundary();
    	super.setPosition(new Vector2f(
    			Math.min(maxb.x, Math.max(minb.x, position.x)),
    			Math.min(maxb.y, Math.max(minb.y, position.y))
    		));
    }
	
    @Override
	public void updateModel(Matrix4f model){
        super.updateModel(model);
	}
    
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
