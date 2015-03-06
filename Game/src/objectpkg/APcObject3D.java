package objectpkg;

import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class APcObject3D implements IObject3D {
	protected final String FILENAME;
	
	protected Matrix4f model;
	
	public APcObject3D(String filename){
		this.FILENAME = filename;
	}
	public void setModel(Matrix4f model){
		this.model = model;
	}
	public String getFilename(){
		return this.FILENAME;
	}
	public Matrix4f getModel(){
		return this.model;
	}
}
