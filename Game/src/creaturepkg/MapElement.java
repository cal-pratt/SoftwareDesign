package creaturepkg;

import graphicspkg.GraphicsManager;
import objectpkg.APcObject3D;
import silvertiger.tutorial.lwjgl.math.Matrix4f;

class MapElement implements IMapElement{

	protected float x;
	protected float y;
	protected GraphicsManager gm;
	protected APcObject3D mesh;
	
	public boolean deleted = false;

	protected MapElement(GraphicsManager gm, APcObject3D mesh, float x, float y) {
		this.gm = gm;
		this.mesh = mesh;
		this.x = x;
		this.y = y;
		
		gm.add(mesh);
		mesh.setView(new Matrix4f());
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
		gm.remove(mesh);
		deleted = true;
	}
	
	public void updateProjection(Matrix4f m) {
		mesh.setProjection(m);
	}
	
	public void updateModel(Matrix4f m) {
		m = (Matrix4f.translate(x, y, 0)).multiply(m);
		mesh.setModel(m);
	}

}
