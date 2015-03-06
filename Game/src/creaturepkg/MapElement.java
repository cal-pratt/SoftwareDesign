package creaturepkg;

import java.util.ArrayList;
import java.util.List;

import graphicspkg.GraphicsManager;
import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

class MapElement implements IMapElement{

	protected float x;
	protected float y;
	protected GraphicsManager gm;
	protected List<APcObject3D> meshList;
	
	public boolean deleted = false;

	protected MapElement(GraphicsManager gm, List<APcObject3D> mesh, float x, float y) {
		this.gm = gm;
		this.meshList = mesh;
		this.x = x;
		this.y = y;
		for(APcObject3D m : meshList){
	        gm.add(m);
		}
	}
	
	protected MapElement(GraphicsManager gm, APcObject3D mesh, float x, float y) {
        this.gm = gm;
        this.meshList = new ArrayList<APcObject3D>();
        this.meshList.add(mesh);
        this.x = x;
        this.y = y;
        
        gm.add(mesh);
    }
	
	@Override
	public void positionOnMap(float x, float y) {
		this.x = x;
		this.y = y;
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
	public void delete() {
        for(APcObject3D mesh : meshList){
            gm.remove(mesh);
        }
		deleted = true;
	}
	
	public void updateModel(Matrix4f m) {
		m = (Matrix4f.translate(x, y, 0)).multiply(m);

        for(APcObject3D mesh : meshList){
            mesh.setModel(m);
        }
	}

}
