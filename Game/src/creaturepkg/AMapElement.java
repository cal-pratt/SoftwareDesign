package creaturepkg;

import graphicspkg.GraphicsManager;

import java.util.ArrayList;
import java.util.List;

import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;

abstract class AMapElement implements IMapElement{
    
	private Vector2f position;
	private List<APcObject3D> meshList;
	private MapElementState state;
	
    private Vector2f maxBound;
    private Vector2f minBound;
	
	protected boolean attached = false;

	protected AMapElement(List<APcObject3D> mesh, Vector2f position) {
		this.state = MapElementState.IDLE;
        this.maxBound = new Vector2f(1000, 1000);
        this.minBound = new Vector2f(-1000, -1000);
		this.meshList = mesh;
		this.position = new Vector2f(position.x, position.y);
	}
	
	@Override
	public void delete(IGameMap map) {
		removeMap(map);
	}
	
	public void attachMap(IGameMap map){
    	if(!attached){
            for(APcObject3D m : meshList){
            	map.getGraphicsManager().add(m);
            }
            this.maxBound = map.getMaxBoundary();
            this.minBound = map.getMinBoundary();
            attached = true;
    	}
	}

    public void removeMap(IGameMap map){
        if(attached){
            for(APcObject3D m : meshList){
            	map.getGraphicsManager().remove(m);
            }
            attached = false;
        }
    }
    
    public Vector2f getMaxBoundary() {
        return new Vector2f(maxBound.x, maxBound.y);
    }

    public Vector2f getMinBoundary() {
        return new Vector2f(minBound.x, minBound.y);
    }

	@Override
	public Vector2f getPosition(){
		return new Vector2f(position.x, position.y);
	}

	@Override
	public void setPosition(Vector2f position){
        this.position.x = position.x;
        this.position.y = position.y;
	}

	
	@Override
	public MapElementState getState() {
        return state;
    }

    protected void setDead() {
    	state = MapElementState.DEAD;
    }
    
	public void updateModel(Matrix4f m) {
		m = (Matrix4f.translate(position.x, position.y, 0)).multiply(m);

        for(APcObject3D mesh : meshList){
            mesh.updateModel(m);
        }
	}
}
