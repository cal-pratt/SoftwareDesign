package creaturepkg;

import silvertiger.tutorial.lwjgl.math.Vector2f;


public interface IMapElement {
    public void updateActions(float timepassed);
    public void updateModel();
	public Vector2f getPosition();
	public void setPosition(Vector2f position);
	public void delete();
	public void attachMap(IGameMap owner);
    public void detachMap();
	public boolean isAlive();
}
