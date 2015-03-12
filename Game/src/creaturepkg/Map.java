package creaturepkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map implements IMap {

    private List<IMapElement> mapElements;
    private List<IMapElement> mapQueue;
    private GraphicsManager gm;
    
    private float xBound;
    private float yBound;
    
    public Map(GraphicsManager gm, float xBound, float yBound){
        this.gm = gm;
        this.xBound = xBound;
        this.yBound = yBound;
        mapElements = new ArrayList<IMapElement>();
        mapQueue = new LinkedList<IMapElement>();
    }

    @Override
	public void addMapElement(IMapElement ele){
        mapQueue.add(ele);
	}
	
	@Override
    public void updateActions(float timepassed){
        for (IMapElement ele : mapElements){
            ele.updateActions(timepassed);
        }
        for(IMapElement ele : mapQueue){
            mapElements.add(ele);
            ele.attachMap(this);
        }
        mapQueue.clear();
        for (IMapElement ele : new ArrayList<>(mapElements)){
            if(!ele.isAlive()){
                mapElements.remove(ele);
                ele.detachMap();
            }
        }
    }
	
    @Override
    public void updateModel(){
        for (IMapElement ele : mapElements){
            ele.updateModel();
        }
    }

    @Override
    public GraphicsManager getGraphicsManager() {
        return this.gm;
    }

    @Override
    public float getBoundaryX() {
        return xBound;
    }

    @Override
    public float getBoundaryY() {
        return yBound;
    }
    
    
}
