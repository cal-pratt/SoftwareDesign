package menupkg;

import objectpkg.ATexObject2D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

public interface IMenuItem {
		
	public void setPosition(float posX, float posY);
	
	public void setSize(float width, float height );
	
	public void update(Matrix4f m);
	
	public void delete();
	
	public ATexObject2D getSprite();
}
