package creaturepkg;

public class Enemy extends ACreature {
	//Player variables
	private int aggroRange;
	
	
	//Getters and Setters
	public int getAggroRange() {
		return aggroRange;
	}
	
	public void setAggroRange(int aggroRange) {
		this.aggroRange = aggroRange;
	}

}
