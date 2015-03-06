package menupkg;

import graphicspkg.GraphicsManager;
import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class MenuSprite implements IMenuItem {
	GraphicsManager gm;
	ATexObject2D sprite;
	float posX, posY;
	float width, height;
	
	boolean hidden = true;
	
	public MenuSprite(GraphicsManager gm, ATexObject2D sprite,
		float posX, float posY,
		float width, float height){
		
		this.gm = gm;
		
		this.sprite = sprite;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
		gm.add(sprite);
	}
	
	public void setPosition(float posX, float posY){
		this.posX = posX;
		this.posY = posY;
	}
	public void setSize(float width, float height ){
		this.width = width;
		this.height = height;
	}

	@Override
	public void updateView(Matrix4f m){
		m = m.multiply(Matrix4f.translate(posX, posY,0));
		if(hidden){
			sprite.setProjection(Matrix4f.scale(0, 0, 0));
		}
		else{
			m = m.multiply(Matrix4f.scale(width/sprite.getWidth(), height/sprite.getHeight(), 1));
			sprite.setProjection(m);
		}
	}
	public ATexObject2D getSprite(){
		return sprite;
	}

	@Override
	public void delete() {
		gm.remove(sprite);
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getX() {
		return posX;
	}

	@Override
	public float getY() {
		return posY;
	}
	

	@Override
	public void reset() {
	}

	@Override
	public void hide() {
		if(!hidden){
			hidden = true;
		}
	}

	@Override
	public void show() {
		if(hidden){
			hidden = false;
		}
	}
	
}
