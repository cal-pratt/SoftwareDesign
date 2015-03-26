package creaturepkg;

import silvertiger.tutorial.lwjgl.math.Vector2f;


public interface IMapElement {
    public void updateActions(IGameMap map, float timepassed);
    public void updateModel();
    
	public Vector2f getPosition();
	public void setPosition(Vector2f position);

	public MapElementState getState();
	public void delete(IGameMap map);
	
	public void attachMap(IGameMap map);
    public void removeMap(IGameMap map);
    
}
