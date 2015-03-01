package creaturepkg;

public class Player extends ACreature {
	//Player variables
	private int experience, skillPoints;
	
	
	//Getters and Setters
	public int getExperience() {
		return experience;
	}
	
	public int getSkillPoints() {
		return skillPoints;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}
}
