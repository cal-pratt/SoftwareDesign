package menupkg;

import java.util.ArrayList;
import java.util.List;

import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class AMenu implements IMenuItem{
	float actualWidth;
	float actualHeight;
	float actualPosX;
	float actualPosY;
	
	float repWidth;
	float repHeight;
	float repPosX;
	float repPosY;

    List<IMenuItem> items;
    
    public AMenu(float actualPosX, float actualPosY, float actualWidth, float actualHeight,
    		float repPosX, float repPosY, float repWidth, float repHeight ){
    	
    	items = new ArrayList<IMenuItem>();
    	
    	this.actualWidth = actualWidth;
    	this.actualHeight = actualHeight;
    	this.actualPosX = actualPosX;
    	this.actualPosY = actualPosY;
    	
    	this.repWidth = repWidth;
    	this.repHeight = repHeight;
    	this.repPosX = repPosX;
    	this.repPosY = repPosY;
    }
    
    public void add(IMenuItem item){
    	items.add(item);
    }
    
    public void remove(IMenuItem item){
    	items.remove(item);
    }
    
    public void delete(){
    	for(IMenuItem item : items){
    		item.delete();
    	}
    	items.clear();
    }
	
	@Override
	public void updateOrthographic(Matrix4f m) {
    	m = m.multiply(Matrix4f.translate(
    			actualPosX/actualWidth, 
    			actualPosY/actualHeight, 
    			0));
        m = m.multiply(Matrix4f.orthographic(
        		0, repWidth*repWidth/actualWidth, 
        		0, actualHeight*(actualHeight/repHeight), 
        		-1f, 10f));
		m = m.multiply(Matrix4f.translate(
				repPosX, 
				repPosY,
				0));
    	for(IMenuItem item : items){
    		item.updateOrthographic(m);
    	}
    }
    
    public float getWidth(){
    	return actualWidth;
    }
    
    public float getHeight(){
    	return actualHeight;
    }
    
    public float getX(){
    	return actualPosX;
    }
    
    public float getY(){
    	return actualPosY;
    }

	@Override
	public void setPosition(float posX, float posY) {
		actualPosX = posX;
		actualPosY = posY;
	}

	@Override
	public void setSize(float width, float height) {
		actualWidth = width;
		actualHeight = height;
	}

	@Override
	public ATexObject2D getSprite() {
		return null;
	}
    
}
