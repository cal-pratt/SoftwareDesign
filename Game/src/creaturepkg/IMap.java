package creaturepkg;

import graphicspkg.GraphicsManager;

public interface IMap {

    public void addMapElement(IMapElement ele);
    public void updateActions(float timepassed);
    public void updateModel();
    public GraphicsManager getGraphicsManager();
    public float getBoundaryX();
    public float getBoundaryY();
}
