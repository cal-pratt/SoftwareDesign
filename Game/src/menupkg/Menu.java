package menupkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public class Menu {
	float actualWidth;
	float actualHeight;
	float actualPosX;
	float actualPosY;
	
	float repWidth;
	float repHeight;
	float repPosX;
	float repPosY;

    List<IMenuItem> items;
    
    public Menu(float actualPosX, float actualPosY, float actualWidth, float actualHeight,
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
    
    public void update(){
    	Matrix4f m = Matrix4f.translate(
    			actualPosX/actualWidth, 
    			actualPosY/actualHeight, 
    			0);
        m = m.multiply(Matrix4f.orthographic(
        		0, repWidth*repWidth/actualWidth, 
        		0, actualHeight*(actualHeight/repHeight), 
        		-1f, 10f));
		m = m.multiply(Matrix4f.translate(
				repPosX, 
				repPosY,
				0));
    	for(IMenuItem item : items){
    		item.update(m);
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
    
}
