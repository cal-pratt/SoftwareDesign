package creaturepkg;

import objectpkg.Object3DFactory;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class Projectile extends AMapElement{
	int power = 0;
	float dx;
	float dy;
	Matrix4f rot;
	IMapElement owner;
	
	public Projectile(IMapElement owner, float x, float y, float dx, float dy) {
		super(Object3DFactory.getLaser(), x, y);
		this.owner = owner;
		
		this.dx = dx;
		this.dy = dy;
		
        if(dx == 0){
            if(dy > 0){
                rot = Matrix4f.rotate(0, 0, 0, 1);
            }
            else{
                rot = Matrix4f.rotate(180, 0, 0, 1);
            }
        }
        else{
            float angle = 180f*(float)(Math.atan(dy/dx))/3.14f;
            if(dx > 0) angle += 180;
            rot = Matrix4f.rotate(angle + 90, 0f, 0f, 1f);
        }
	}

    @Override
	public void updateActions(float timepassed){
        setPosX(getPosX() + dx*timepassed/1000f);
        setPosY(getPosY() + dy*timepassed/1000f);

        if(Math.pow(Math.abs(owner.getPosX() - getPosX()),2) + 
                Math.pow(Math.abs(owner.getPosY() - getPosY()),2) > 2000){
            setDead();
        }
	}
	
	@Override
	public void updateModel(){
        super.updateModel(Matrix4f.scale(.5f, .5f, .5f).multiply(rot).multiply(Matrix4f.rotate(90, 0, 0, 1)));
	}
	
	
}
