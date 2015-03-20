package creaturepkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import silvertiger.tutorial.lwjgl.math.Vector2f;

public class GameMap implements IGameMap {

    private List<IMapElement> mapElements;
    private List<ACreature> mapCreatures;
    private List<Projectile> mapProjectiles;
    private List<IMapElement> mapQueue;
    private GraphicsManager gm;
    
    private Vector2f maxBound;
    private Vector2f minBound;
    
    public GameMap(GraphicsManager gm, Vector2f minBound, Vector2f maxBound){
        this.gm = gm;
        this.maxBound = maxBound;
        this.minBound = minBound;
        mapElements = new ArrayList<IMapElement>();
        mapCreatures = new ArrayList<ACreature>();
        mapProjectiles = new ArrayList<Projectile>();
        mapQueue = new LinkedList<IMapElement>();
    }

    @Override
	public void addMapElement(IMapElement ele){
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
        for(ACreature creature : mapCreatures){
        	if(creature.isAlive())
        	for(Projectile proj : mapProjectiles){
            	if(proj.isAlive()){
            		proj.updateCollision(creature);
            	}
            }
        }
        for (IMapElement ele : new ArrayList<>(mapElements)){
            if(!ele.isAlive()){
                mapElements.remove(ele);
                ele.detachMap();
            }
        }
        for (ACreature creature : new ArrayList<>(mapCreatures)){
            if(!creature.isAlive()){
            	mapCreatures.remove(creature);
            }
        }
        for (Projectile proj : new ArrayList<>(mapProjectiles)){
            if(!proj.isAlive()){
            	mapProjectiles.remove(proj);
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
    public Vector2f getMaxBoundary() {
        return new Vector2f(maxBound.x, maxBound.y);
    }

    @Override
    public Vector2f getMinBoundary() {
        return new Vector2f(minBound.x, minBound.y);
    }
    
    
}
