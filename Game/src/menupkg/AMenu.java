package menupkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class AMenu implements IMenuItem{
	float width;
	float height;
	GraphicsManager gm;
    List<IMenuItem> items;
    
    protected boolean hidden = true;
    
    public AMenu(GraphicsManager gm){
    	
    	items = new ArrayList<IMenuItem>();
    	
    	this.gm = gm;
    	
    	this.width = gm.getWidth();
    	this.height = gm.getHeight();
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
	public void updateView(Matrix4f m) {
        this.width = gm.getWidth();
        this.height = gm.getHeight();
        m = m.multiply(Matrix4f.orthographic(0, width, 0, height, -1f, 10f));
    	for(IMenuItem item : items){
    		item.updateView(m);
    	}
    }
    
   public void hide(){
        if (!hidden){
            hidden = true;
            for(IMenuItem item : items){
                item.hide();
            }
        }
    }

    public void show(){
        if (hidden){
            hidden = false;
            for(IMenuItem item : items){
                item.show();
            }
        }
    }
	
    public float getWidth(){
    	return width;
    }
    
    public float getHeight(){
    	return height;
    }
    
    public float getX(){
    	return 0;
    }
    
    public float getY(){
    	return 0;
    }

	@Override
	public void setPosition(float posX, float posY) {}

	@Override
	public void setSize(float width, float height) {}

	@Override
	public ATexObject2D getSprite() {
		return null;
	}
	
    
}
