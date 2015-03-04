package objectpkg;


import silvertiger.tutorial.lwjgl.math.Matrix4f;

public abstract class ATexObject2D implements IObject3D {
    protected final String FILENAME;
    
    protected Matrix4f model;
    protected Matrix4f view;
    protected Matrix4f projection;
    
    protected float width, height;
    
    public ATexObject2D(String filename){
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
    public void setRawSize(float width, float height){
    	this.width = width;
    	this.height = height;
    }

    public float getWidth(){
    	return width;
    }
    
    public float getHeight(){
    	return height;
    }
}
