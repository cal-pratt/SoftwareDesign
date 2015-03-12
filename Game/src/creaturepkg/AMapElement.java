package creaturepkg;

import java.util.ArrayList;
import java.util.List;

import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

abstract class AMapElement implements IMapElement{
    
	private float x;
	private float y;
	private List<APcObject3D> meshList;
	protected IMap containingMap;
	
	private boolean alive = true;
	
	public boolean attached = false;

	protected AMapElement(List<APcObject3D> mesh, float x, float y) {
		this.meshList = mesh;
		this.x = x;
		this.y = y;
	}
	
	protected AMapElement(APcObject3D mesh, float x, float y) {
        this.meshList = new ArrayList<APcObject3D>();
        this.meshList.add(mesh);
        this.x = x;
        this.y = y;
    }
	
	public void attachMap(IMap owner){
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
    
	public float getPosX(){
		return x;
	}
	
	public float getPosY(){
		return y;
	}
	
	public void setPosX(float x){
        this.x = x;
	}
	
	public void setPosY(float y){
        this.y = y;
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
		m = (Matrix4f.translate(x, y, 0)).multiply(m);

        for(APcObject3D mesh : meshList){
            mesh.updateModel(m);
        }
	}

}
