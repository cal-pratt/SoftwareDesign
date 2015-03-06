package menupkg;

import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public interface IMenuItem {
		
	public void setPosition(float posX, float posY);
	
	public void setSize(float width, float height );
	
	public void updateView(Matrix4f m);
	
	public void delete();
	
	public void reset();
	
	public void hide();
	
	public void show();
	
	public ATexObject2D getSprite();
    
    public float getWidth();
    
    public float getHeight();
    
    public float getX();
    
    public float getY();
}
