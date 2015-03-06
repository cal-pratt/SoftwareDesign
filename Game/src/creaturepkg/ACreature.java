package creaturepkg;

import inputpkg.UserInput;

import java.util.LinkedList;
import java.util.List;

import menupkg.PlayerOverlay;
import menupkg.StartMenu;
import graphicspkg.GraphicsManager;
import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class ACreature extends MapElement {
	//Creature variables
	protected int maxHealth;
	protected int currHealth;
	private int attackLvl;
	private int defenseLvl;
	private int attackType;
	protected float aimX = 0;
	protected float aimY = 0;
	
	protected List<Projectile> projectiles = new LinkedList<Projectile>();
	
	protected ACreature(GraphicsManager gm, APcObject3D creatureMesh, int maxHealth, int attackLvl, int defenseLvl, int attackType) {
		super(gm, creatureMesh, 0, 0);
		this.maxHealth = maxHealth;
		this.attackLvl = attackLvl;
		this.defenseLvl = defenseLvl;
		this.attackType = attackType;
		
		this.currHealth = maxHealth;
		
	}
	protected ACreature(GraphicsManager gm,  List<APcObject3D> creatureMesh, int maxHealth, int attackLvl, int defenseLvl, int attackType) {
        super(gm, creatureMesh, 0, 0);
        this.maxHealth = maxHealth;
        this.attackLvl = attackLvl;
        this.defenseLvl = defenseLvl;
        this.attackType = attackType;
        
        this.currHealth = maxHealth;
    }
	
	//behaviour
	public void die(){
	}
	
	//Triggered as player begins to attack
	public int attack() {
		//When different attacks are made, insert different attack types here
		int atkDamage = this.getAttackLvl();
		//Fire projectile
		return atkDamage;
	}
	
	//When a player is attacked they should automatically defend and lose appropriate health
	public void defend(ACreature target, Projectile attack){
		int tgtDefense = target.getDefenseLvl();
		int atkDamage = attack.power;
		int damage = atkDamage - tgtDefense;
		if (damage >0 ) {
			target.setCurrentHealth(target.getCurrentHealth() - damage);
		}
		if (target.getCurrentHealth() <= 0) {
			target.die();
		}
	}
	
	protected void createProjectile() {
	    
		projectiles.add(new Projectile(gm, x-.4f, y+.4f, aimX, aimY));
        projectiles.add(new Projectile(gm, x+.4f, y-.4f, aimX, aimY));
	}
	
	protected void updateProjectiles(float timepassed) {
		for(Projectile p : new LinkedList<>(projectiles)){
			if(Math.pow(Math.abs(p.x - x),2) + Math.pow(Math.abs(p.y - y),2) > 2000){
				p.delete();
				projectiles.remove(p);
			}
		}
		for(Projectile p : projectiles){
			p.update(timepassed);
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
