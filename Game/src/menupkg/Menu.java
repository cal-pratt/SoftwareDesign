package menupkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

import objectpkg.ATexObject2D;

public class Menu {
	GraphicsManager gm;
	float width;
	float height;
	float posX;
	float posY;

    List<MenuItem> items;
    
    public Menu(GraphicsManager gm, float posX, float posY, float width, float height ){
    	items = new ArrayList<MenuItem>();
    	this.gm = gm;
    	this.width = width;
    	this.height = height;
    	this.posX = posX;
    	this.posY = posY;
    }
    
    public void addMenuItem(ATexObject2D sprite,
    		float posX, float posY,
    		float width, float height){
    	items.add(new MenuItem(this, sprite, posX, posY, width, height));
    	gm.add(sprite);
    }
    
    public void clear(){
    	for(MenuItem item : items){
    		item.getSprite();
    	}
    }
    
    public void update(){
    	for(MenuItem item : items){
    		item.updateProjection();
    	}
    }
    
    public float getWidth(){
    	return width;
    }
    public float getHeight(){
    	return height;
    }
    public float getX(){
    	return posX;
    }
    public float getY(){
    	return posY;
    }
    
}
