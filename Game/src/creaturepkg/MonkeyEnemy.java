package creaturepkg;

import graphicspkg.GraphicsManager;
import objectpkg.APcObject3D;
import objectpkg.Object3DFactory;

public class MonkeyEnemy extends ACreature {
	
	public MonkeyEnemy(GraphicsManager gm) {
		super(gm, Object3DFactory.getMonkey(), 10, 1, 1, 0);
	}

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
