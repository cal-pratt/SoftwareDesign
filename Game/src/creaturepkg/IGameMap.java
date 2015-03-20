package creaturepkg;

import silvertiger.tutorial.lwjgl.math.Vector2f;
import graphicspkg.GraphicsManager;

public interface IGameMap {

    public void addMapElement(IMapElement ele);
    public void addMapElement(ACreature ele);
    public void addMapElement(Projectile ele);
    public void updateActions(float timepassed);
    public void updateModel();
    public GraphicsManager getGraphicsManager();
    public Vector2f getMaxBoundary();
    public Vector2f getMinBoundary();
}
