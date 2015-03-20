package creaturepkg;


import eventpkg.GameEvents.*;
import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class MonkeyEnemy extends ACreature {
	private Player player;
	private IPlayerEventListener callback;

    private float laserSpeed = 100f;
	private float aggroRange = 30;
	private float lastFire;
	private float fireIncrement = 200;
	
	public MonkeyEnemy(Player player) {
		super(Object3DFactory.getMonkey(), 10, 2, 2, 0);
		this.player = player;
		callback = new IPlayerEventListener() {
			
			@Override
			public void actionPerformed(PlayerEventPublisher sender, Player e) {
			    updateAim();
			}
		};
		player.getEventPublisher().subscribe(callback);
	}

    @Override 
    public void delete(){
        player.getEventPublisher().unsubscribe(callback);
        super.delete();
    }
	
	private void updateAim(){
		if (Math.pow(player.getPosX() - getPosX(),2) + Math.pow(player.getPosY() - getPosY(),2) < aggroRange*aggroRange){
			float dX = (player.getPosX() - this.getPosX());
	        float dY = (player.getPosY() - this.getPosY());
	        
	        aimX = dX*laserSpeed/((float)Math.sqrt(dX*dX + dY*dY));
	        aimY = dY*laserSpeed/((float)Math.sqrt(dX*dX + dY*dY));
		}
		else {
			aimX = 0;
			aimY = 0;
		}
	}

    @Override 
	public void updateActions(float timepassed) {
		lastFire += timepassed;
		if(aimX != 0 || aimY != 0 ){
			if(lastFire > fireIncrement ){
			    containingMap.addMapElement(new Projectile(this, getPosX()-.4f, getPosY()+.4f, aimX, aimY));
			   // containingMap.addMapElement(new Projectile(this, getPosX()+.4f, getPosY()-.4f, aimX, aimY));
				lastFire = 0;
			}
		}
	}

	public void updateModel() {
        Matrix4f model = Matrix4f.translate( 0, 0, 1).multiply(
                Matrix4f.rotate(0, 0, 1, 0).multiply(Matrix4f.scale(1f, 1f, 1f)));
	    super.updateModel(model);
	}
    
    //Getters and Setters
    public float getAggroRange() {
        return aggroRange;
    }
    
    public void setAggroRange(int aggroRange) {
        this.aggroRange = aggroRange;
    }

}
