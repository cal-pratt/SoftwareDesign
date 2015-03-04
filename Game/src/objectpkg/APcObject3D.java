package objectpkg;

import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class APcObject3D implements IObject3D {
	protected final String FILENAME;
	
	protected Matrix4f model;
	protected Matrix4f view;
	protected Matrix4f projection;
	
	public APcObject3D(String filename){
		this.FILENAME = filename;
	}
	public void setModel(Matrix4f model){
		this.model = model;
	}
	public void setView(Matrix4f view){
		this.view = view;
	}
	public void setProjection(Matrix4f projection){
		this.projection = projection;
	}
	public String getFilename(){
		return this.FILENAME;
	}
	public Matrix4f getModel(){
		return this.model;
	}
	public Matrix4f getView(){
		return this.view;
	}
	public Matrix4f getProjection(){
		return this.projection;
	}
}
