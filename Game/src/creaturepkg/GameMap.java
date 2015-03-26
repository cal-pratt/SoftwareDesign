package creaturepkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import silvertiger.tutorial.lwjgl.math.Vector2f;

public class GameMap implements IGameMap {

    private List<IMapElement> mapElements = new ArrayList<IMapElement>();
    private List<ACreature> mapCreatures = new ArrayList<ACreature>();
    private List<Projectile> mapProjectiles = new ArrayList<Projectile>();
    private List<IMapElement> mapQueue = new LinkedList<IMapElement>();
    private GraphicsManager gm;
    
    private Vector2f maxBound;
    private Vector2f minBound;
    
    public GameMap(GraphicsManager gm, Vector2f minBound, Vector2f maxBound){
        this.gm = gm;
        this.maxBound = maxBound;
        this.minBound = minBound;
    }

    private void addMapElement(IMapElement ele){
        mapQueue.add(ele);
	}

    @Override
    public void addMapElement(ACreature ele){
		addMapElement((IMapElement)ele);
		mapCreatures.add(ele);
	}

    @Override
	public void addMapElement(Projectile ele){
		addMapElement((IMapElement)ele);
		mapProjectiles.add(ele);
	}
    
	private void removeMapElement(IMapElement ele){
    	mapElements.remove(ele);
	}

    @Override
    public void removeMapElement(ACreature ele){
    	removeMapElement((IMapElement)ele);
		mapCreatures.remove(ele);
	}

    @Override
	public void removeMapElement(Projectile ele){
    	removeMapElement((IMapElement)ele);
		mapProjectiles.remove(ele);
	}
	
	@Override
    public void updateActions(float timepassed){
		// Update all elements in the map
        for (IMapElement ele : mapElements){
            ele.updateActions(this, timepassed);
        }
        // Add all new elements to the map
        for(IMapElement ele : mapQueue){
            mapElements.add(ele);
            ele.attachMap(this);
        }
        mapQueue.clear();
        
        // update projectile collisions
        for(ACreature creature : mapCreatures){
        	if(creature.getState() != MapElementState.DEAD)
        	for(Projectile proj : mapProjectiles){
            	if(proj.getState() != MapElementState.DEAD){
            		proj.updateCollision(creature);
            	}
            }
        }
        // Remove dead elements
        for (ACreature creature : new ArrayList<>(mapCreatures)){
            if(creature.getState() == MapElementState.DEAD){
            	mapCreatures.remove(creature);
                mapElements.remove(creature);
                creature.removeMap(this);
            }
        }
        for (Projectile proj : new ArrayList<>(mapProjectiles)){
            if(proj.getState() == MapElementState.DEAD){
            	mapProjectiles.remove(proj);
                mapElements.remove(proj);
                proj.removeMap(this);
            }
        }
    }
	
	public void attachMapElements(){
		for (IMapElement ele : mapElements){
            ele.attachMap(this);
		}
	}
	
	public void detachMapElements(){
		for (IMapElement ele : mapElements){
            ele.removeMap(this);
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
    public Vector2f getMaxBoundary() {
        return new Vector2f(maxBound.x, maxBound.y);
    }

    @Override
    public Vector2f getMinBoundary() {
        return new Vector2f(minBound.x, minBound.y);
    }
    
    
}
