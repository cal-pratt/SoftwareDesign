package creaturepkg;

import graphicspkg.GraphicsManager;

public interface IGameMap {

    public void addMapElement(IMapElement ele);
    public void addMapElement(ACreature ele);
    public void addMapElement(Projectile ele);
    public void updateActions(float timepassed);
    public void updateModel();
    public GraphicsManager getGraphicsManager();
    public float getBoundaryX();
    public float getBoundaryY();
}
