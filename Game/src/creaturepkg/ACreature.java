package creaturepkg;

import java.util.List;

import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class ACreature extends AMapElement {
	//Creature variables
	protected int maxHealth;
	protected int currHealth;
	private int attackLvl;
	private int defenseLvl;
	private int attackType;
	protected float aimX = 0;
	protected float aimY = 0;
	
	protected ACreature(APcObject3D creatureMesh, int maxHealth, int attackLvl, int defenseLvl, int attackType) {
		super(creatureMesh, 0, 0);
		this.maxHealth = maxHealth;
		this.attackLvl = attackLvl;
		this.defenseLvl = defenseLvl;
		this.attackType = attackType;
		this.currHealth = maxHealth;
		
	}
	
	protected ACreature(List<APcObject3D> creatureMesh, int maxHealth, int attackLvl, int defenseLvl, int attackType) {
        super(creatureMesh, 0, 0);
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
	public void setPosX(float x){
        if(x > containingMap.getBoundaryX()){
            super.setPosX(containingMap.getBoundaryX());
        }
        else if(x < -containingMap.getBoundaryX()){
            super.setPosX(-containingMap.getBoundaryX());
        }
        else{
            super.setPosX(x);
        }
    }

    @Override
    public void setPosY(float y){
        if(y > containingMap.getBoundaryY()){
            super.setPosY(containingMap.getBoundaryY());
        }
        else if(y < -containingMap.getBoundaryY()){
            super.setPosY(-containingMap.getBoundaryY());
        }
        else{
            super.setPosY(y);
        }
    }
	
    @Override
	public void updateModel(Matrix4f model){
        super.updateModel(model);
	}
	
    @Override 
    public void delete(){
        super.delete();
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
