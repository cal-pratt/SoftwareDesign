package objectpkg;

import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class APcObject3D implements IGraphicsObject {
	protected Matrix4f model = new Matrix4f();

    public abstract String getFilename();
	
	public void updateModel(Matrix4f model){
		this.model = model;
	}
	
	public Matrix4f getModel(){
		return this.model;
	}
}
