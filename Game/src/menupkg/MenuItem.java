package menupkg;

import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class MenuItem {
	public Menu parent;
	ATexObject2D sprite;
	float posX, posY;
	float width, height;
	
	public MenuItem(Menu parent,
		ATexObject2D sprite,
		float posX, float posY,
		float width, float height){
		
		this.parent = parent;
		this.sprite = sprite;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
		sprite.setView(new Matrix4f());
		sprite.setModel(new Matrix4f());
		
		updateProjection();
	}
	
	public void setPosition(float posX, float posY){
		this.posX = posX;
		this.posY = posY;
	}
	public void setSize(float width, float height ){
		this.width = width;
		this.height = height;
	}
	
	void updateProjection(){
		Matrix4f m = Matrix4f.orthographic(parent.getX(), parent.getWidth(), parent.getY(), parent.getHeight(), -1f, 10f);
		m = m.multiply(Matrix4f.translate(posX, posY,0));
		m = m.multiply(Matrix4f.scale(width/sprite.getWidth(), height/sprite.getHeight(), 1));
		sprite.setProjection(m);
	}
	public ATexObject2D getSprite(){
		return sprite;
	}
	
}
