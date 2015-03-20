package creaturepkg;

import java.util.ArrayList;
import java.util.List;

import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;
import silvertiger.tutorial.lwjgl.math.Vector2f;

abstract class AMapElement implements IMapElement{
    
	private Vector2f position;
	private List<APcObject3D> meshList;
	protected IGameMap containingMap;
	
	private boolean alive = true;
	
	public boolean attached = false;

	protected AMapElement(List<APcObject3D> mesh, Vector2f position) {
		this.meshList = mesh;
		this.position = new Vector2f(position.x, position.y);
	}
	
	protected AMapElement(APcObject3D mesh, Vector2f position) {
        this.meshList = new ArrayList<APcObject3D>();
        this.meshList.add(mesh);
		this.position = new Vector2f(position.x, position.y);
    }
	
	public void attachMap(IGameMap owner){
    	if(!attached){
    	    this.containingMap = owner;
            for(APcObject3D m : meshList){
                owner.getGraphicsManager().add(m);
            }
            attached = true;
    	}
	}

    public void detachMap(){
        if(attached){
            for(APcObject3D m : meshList){
                containingMap.getGraphicsManager().remove(m);
            }
            attached = false;
        }
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
	public void delete() {}
	
	@Override
    public boolean isAlive() {
        return alive;
    }

    protected void setDead() {
        alive = false;
    }
    
	public void updateModel(Matrix4f m) {
		m = (Matrix4f.translate(position.x, position.y, 0)).multiply(m);

        for(APcObject3D mesh : meshList){
            mesh.updateModel(m);
        }
	}

}
