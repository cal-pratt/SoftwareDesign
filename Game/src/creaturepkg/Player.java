package creaturepkg;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import eventpkg.IKeyEventListener;
import eventpkg.KeyEventPublisher;
import inputpkg.UserInput;
import graphicspkg.GraphicsManager;
import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;

public class Player extends ACreature {
	public Projectile projectile;
	public Player(GraphicsManager gman, UserInput input) {
		super(gman, Object3DFactory.getCube(), 10, 2, 2, 0);

		createProjectile();
		input.getInputEvent(GLFW_KEY_Z).subscribe(new IKeyEventListener() {
			
			@Override
			public void actionPerformed(KeyEventPublisher sender, Object e) {
				createProjectile();
			}
		});
	}
	
	private void createProjectile() {
		projectile = new Projectile(gm, this);
	}

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
