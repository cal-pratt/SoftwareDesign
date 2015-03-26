package creaturepkg;

import silvertiger.tutorial.lwjgl.math.Vector2f;
import graphicspkg.GraphicsManager;

public interface IGameMap {

    public void addMapElement(ACreature creature);
    public void addMapElement(Projectile proj);
    public void removeMapElement(ACreature creature);
    public void removeMapElement(Projectile proj);
    public void updateActions(float timepassed);
    public void updateModel();
    public GraphicsManager getGraphicsManager();
    public Vector2f getMaxBoundary();
    public Vector2f getMinBoundary();
	public void attachMapElements();
	public void detachMapElements();
}
