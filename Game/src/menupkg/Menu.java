package menupkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

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
    	items.add(new MenuItem(sprite, posX, posY, width, height));
    	gm.add(sprite);
    }
    
    public void clear(){
    	for(MenuItem item : items){
    		item.getSprite();
    	}
    }
    
    public void update(){
        Matrix4f m = Matrix4f.orthographic(posX, width, posY, height, -1f, 10f);
    	for(MenuItem item : items){
    		item.updateProjection(m);
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
